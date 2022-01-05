package gd.rf.acro.scf;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class SCF implements ModInitializer {
	public static final Identifier SEND_SOUND = new Identifier("scf", "send_sound");
	private static final String[] cRES = {
			"minecraft:cobblestone",
			"minecraft:stone",
			"minecraft:coal_ore",
			"c:tin_ore",
			"c:copper_ore",
			"minecraft:iron_ore",
			"c:zinc_ore",
			"c:aluminum_ore",
			"c:lead_ore",
			"c:silver_ore",
			"minecraft:redstone_ore",
			"minecraft:nether_quartz_ore",
			"minecraft:gold_ore",
			"c:uranium_ore",
			"minecraft:diamond_ore",
			"c:platinum_ore",
			"c:tungsten_ore",
			"c:osmium_ore",
			"c:palladium_ore",
			"c:amethyst_ore",
			"c:iridium_ore",
			"c:topaz_ore",
			"c:cobalt_ore",
			"c:peridot_ore",
			"c:sapphire_ore",
			"c:ruby_ore",
			"minecraft:ancient_debris",
	};

	private static final String[] cResRolls = {
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"100",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"20",
			"1"
	};

	public static String[] ORES = {};
	public static String[] WEIGHTS = {};

	public static final ItemGroup TAB = FabricItemGroupBuilder.build(
			new Identifier("scf", "tab"),
			() -> new ItemStack(SCF.COBBLESTONE_FUNNEL_BLOCK));

	public static Block COBBLESTONE_FUNNEL_BLOCK;
	public static Block COPPER_FUNNEL_BLOCK;
	public static Block BRONZE_FUNNEL_BLOCK;
	public static Block IRON_FUNNEL_BLOCK;
	public static Block GOLD_FUNNEL_BLOCK;
	public static Block DIAMOND_FUNNEL_BLOCK;
	public static Block NETHERITE_FUNNEL_BLOCK;

	@Override
	public void onInitialize() {
		ConfigUtils.checkConfigs();

		COBBLESTONE_FUNNEL_BLOCK = registerFunnel("cobblestone_funnel", "cobblelvl", "cobblespeed");
		COPPER_FUNNEL_BLOCK = registerFunnel("copper_funnel", "copperlvl", "copperspeed");
		BRONZE_FUNNEL_BLOCK = registerFunnel("bronze_funnel", "bronzelvl", "bronzespeed");
		IRON_FUNNEL_BLOCK = registerFunnel("iron_funnel", "ironlvl", "ironspeed");
		GOLD_FUNNEL_BLOCK = registerFunnel("gold_funnel", "goldlvl", "goldspeed");
		DIAMOND_FUNNEL_BLOCK = registerFunnel("diamond_funnel", "diamondlvl", "diamondspeed");
		NETHERITE_FUNNEL_BLOCK = registerFunnel("netherite_funnel", "netheritelvl", "netheritespeed");

		readOreTable();
	}

	private FunnelBlock registerFunnel(String id, String level, String speed) {
		FunnelBlock block = Registry.register(Registry.BLOCK,
						new Identifier("scf", id),
						new FunnelBlock(
								AbstractBlock.Settings
										.of(Material.STONE)
										.ticksRandomly()
										.strength(1.5f, 0),
								Integer.parseInt(ConfigUtils.config.get(level)),
								Integer.parseInt(ConfigUtils.config.get(speed))));

		Registry.register(Registry.ITEM,
				new Identifier("scf", id),
				new BlockItem(block, new Item.Settings().group(TAB)));

		return block;
	}

	private void readOreTable() {
		File block = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/funnel_blocks.acfg");
		File weight = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/funnel_blocks_weights.acfg");

		try {
			if (!block.exists()) {
				FileUtils.writeLines(block, Arrays.asList(cRES));
				ORES = Arrays.asList(cRES).toArray(ORES);
			} else {
				ORES = FileUtils.readLines(block, "utf-8").toArray(ORES);
			}
			if (!weight.exists()) {
				FileUtils.writeLines(weight, Arrays.asList(cResRolls));
				WEIGHTS = Arrays.asList(cResRolls).toArray(WEIGHTS);
			} else {
				WEIGHTS = FileUtils.readLines(weight, "utf-8").toArray(WEIGHTS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
