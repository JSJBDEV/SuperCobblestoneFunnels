package gd.rf.acro.scf;

import gd.rf.acro.scf.mixin.WeightedListAccessor;
import gd.rf.acro.scf.mixin.WeightedListEntryAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class FunnelBlock extends Block {
	private final static VoxelShape SHAPE = Block.createCuboidShape(2, 1, 2, 14, 16, 14);
	private final int period;
	private int level;

	public FunnelBlock(Settings settings, int level, int period) {
		super(settings);
		this.level = level;
		this.period = period;

		if (this.level < 0 || this.level >= OreManager.weightedListCollection.size()) {
			this.level = MathHelper.clamp(this.level, 0, OreManager.weightedListCollection.size() - 1);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.scheduledTick(state, world, pos, random);

		if (hasLavaAndWater(pos, world) && world.getBlockState(pos.down()).isAir()) {
			if (!world.isClient) {
				world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
			}

			Identifier randomBlockId = OreManager.weightedListCollection.get(this.level).pickRandom(random);
			world.setBlockState(pos.down(), Registry.BLOCK.get(randomBlockId).getDefaultState());
		}
		world.getBlockTickScheduler().schedule(pos, this, this.period);
	}

	private boolean hasLavaAndWater(BlockPos pos, World world) {
		Direction.Type offsets = Direction.Type.HORIZONTAL;

		boolean hasWater = offsets.stream().anyMatch(e -> FluidTags.WATER.contains(this.getFluidAtPos(pos.offset(e), world)));
		boolean hasLava = offsets.stream().anyMatch(e -> FluidTags.LAVA.contains(this.getFluidAtPos(pos.offset(e), world)));

		return hasLava && hasWater;
	}

	private Fluid getFluidAtPos(BlockPos pos, World world) {
		return world.getBlockState(pos).getFluidState().getFluid();
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		world.getBlockTickScheduler().schedule(pos, this, this.period);
	}

	@Override
	public void buildTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
		super.buildTooltip(stack, world, tooltip, options);

		if (world != null) {
			Identifier id = this.getGreatestWeight();
			GameOptions gameOptions = MinecraftClient.getInstance().options;

			tooltip.add(new TranslatableText("util.scf.blocks_per_second", this.period / 20f));
			tooltip.add(new TranslatableText("util.scf.best_block", Registry.BLOCK.get(id).getName()));
			tooltip.add(new TranslatableText("util.scf.extra_info",
					new TranslatableText(gameOptions.keySneak.getTranslationKey()),
					new TranslatableText(gameOptions.keyUse.getTranslationKey())
			));
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.isSneaking() && hand == Hand.MAIN_HAND && world.isClient) {
			MutableText text = new TranslatableText("util.scf.funnel_output");
			this.getList().stream().forEach(e -> text.append(new LiteralText("\n  "))
							.append(Registry.BLOCK.get(e).getName()));

			player.sendMessage(text, false);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	private WeightedList<Identifier> getList() {
		return OreManager.weightedListCollection.get(this.level);
	}

	private Identifier getGreatestWeight() {
		return ((WeightedListAccessor<Identifier>) this.getList()).getEntries().stream()
				.sorted(Comparator.comparing(WeightedList.Entry::getElement))
				.min(Comparator.comparingInt(e -> ((WeightedListEntryAccessor) e).getWeight()))
				.map(WeightedList.Entry::getElement)
				.orElse(null);
	}
}
