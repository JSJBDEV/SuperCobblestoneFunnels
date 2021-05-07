package gd.rf.acro.scf;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;

public class CobblestoneFunnelBlock extends Block {
    private int tier;
    private int period;



    public CobblestoneFunnelBlock(Settings settings, int tlevel, int tperiod) {
        super(settings);
        tier=tlevel;
        period=tperiod;

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(2,1,2,14,16,14);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if(hasLavaAndWater(pos,world) && world.getBlockState(pos.down()).isAir())
        {
            PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
            packetByteBuf.writeInt(1);
            PlayerStream.around(world,pos,5).forEach(pp->ServerSidePacketRegistry.INSTANCE.sendToPlayer(pp,SCF.SEND_SOUND,packetByteBuf));
            world.setBlockState(pos.down(),OreManager.getRandomBlock(tier).getDefaultState());
        }

        world.getBlockTickScheduler().schedule(pos,this,period);
    }

    private boolean hasLavaAndWater(BlockPos pos, World world)
    {
        boolean hasWater = false;
        boolean hasLava = false;
        if(FluidTags.WATER.contains(world.getBlockState(pos.east()).getFluidState().getFluid()))
        {
            hasWater=true;
        }
        if(FluidTags.LAVA.contains(world.getBlockState(pos.east()).getFluidState().getFluid()))
        {
            hasLava=true;
        }

        if(FluidTags.WATER.contains(world.getBlockState(pos.west()).getFluidState().getFluid()))
        {
            hasWater=true;
        }
        if(FluidTags.LAVA.contains(world.getBlockState(pos.west()).getFluidState().getFluid()))
        {
            hasLava=true;
        }

        if(FluidTags.WATER.contains(world.getBlockState(pos.south()).getFluidState().getFluid()))
        {
            hasWater=true;
        }
        if(FluidTags.LAVA.contains(world.getBlockState(pos.south()).getFluidState().getFluid()))
        {
            hasLava=true;
        }

        if(FluidTags.WATER.contains(world.getBlockState(pos.north()).getFluidState().getFluid()))
        {
            hasWater=true;
        }
        if(FluidTags.LAVA.contains(world.getBlockState(pos.north()).getFluidState().getFluid()))
        {
            hasLava=true;
        }


        return hasLava && hasWater;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.getBlockTickScheduler().schedule(pos,this,period);
    }

    @Override
    public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);

    }



}
