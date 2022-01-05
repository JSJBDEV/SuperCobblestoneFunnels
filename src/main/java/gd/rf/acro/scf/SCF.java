package gd.rf.acro.scf;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
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

	public static CobblestoneFunnelBlock COBBLESTONE_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock COPPER_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock BRONZE_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock IRON_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock GOLD_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock DIAMOND_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock NETHERITE_FUNNEL_BLOCK;

	public static final ItemGroup TAB = FabricItemGroupBuilder.build(
			new Identifier("scf", "tab"),
			() -> new ItemStack(SCF.COBBLESTONE_FUNNEL_BLOCK));

	@Override
	public void onInitialize() {
		ConfigUtils.checkConfigs();

		registerBlocks();
		registerItems();

		readOreTable();
	}

	private void registerBlocks() {
		Map<String, String> c = ConfigUtils.config;

		COBBLESTONE_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("cobblelvl")), Integer.parseInt(c.get("cobblespeed")));
		COPPER_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("copperlvl")), Integer.parseInt(c.get("copperspeed")));
		BRONZE_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("bronzelvl")), Integer.parseInt(c.get("bronzespeed")));
		IRON_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("ironlvl")), Integer.parseInt(c.get("ironspeed")));
		GOLD_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("goldlvl")), Integer.parseInt(c.get("goldspeed")));
		DIAMOND_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("diamondlvl")), Integer.parseInt(c.get("diamondspeed")));
		NETHERITE_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f, 0), Integer.parseInt(c.get("netheritelvl")), Integer.parseInt(c.get("netheritespeed")));

		Registry.register(Registry.BLOCK, new Identifier("scf", "cobblestone_funnel"), COBBLESTONE_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("scf", "copper_funnel"), COPPER_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("scf", "bronze_funnel"), BRONZE_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("scf", "iron_funnel"), IRON_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("scf", "gold_funnel"), GOLD_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("scf", "diamond_funnel"), DIAMOND_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("scf", "netherite_funnel"), NETHERITE_FUNNEL_BLOCK);
	}

	private void registerItems() {
		Registry.register(Registry.ITEM, new Identifier("scf", "cobblestone_funnel"), new BlockItem(COBBLESTONE_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM, new Identifier("scf", "copper_funnel"), new BlockItem(COPPER_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM, new Identifier("scf", "bronze_funnel"), new BlockItem(BRONZE_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM, new Identifier("scf", "iron_funnel"), new BlockItem(IRON_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM, new Identifier("scf", "gold_funnel"), new BlockItem(GOLD_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM, new Identifier("scf", "diamond_funnel"), new BlockItem(DIAMOND_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM, new Identifier("scf", "netherite_funnel"), new BlockItem(NETHERITE_FUNNEL_BLOCK, new Item.Settings().group(TAB)));
	}

	private void readOreTable() {
		File block = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/funnel_blocks.acfg");
		File weight = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/funnel_blocks_weights.acfg");
		try {
			if (!block.exists()) {
				FileUtils.writeLines(block, Arrays.asList(cRES));
			}
			if (!weight.exists()) {
				FileUtils.writeLines(weight, Arrays.asList(cResRolls));
			}

			ORES = FileUtils.readLines(block, "utf-8").toArray(ORES);
			WEIGHTS = FileUtils.readLines(weight, "utf-8").toArray(WEIGHTS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
