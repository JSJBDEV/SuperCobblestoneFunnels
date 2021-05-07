package gd.rf.acro.scf;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SCF implements ModInitializer {
	public static final Identifier SEND_SOUND = new Identifier("scf","send_sound");
	public static final Tag<Block> COPPER_ORE = TagRegistry.block(new Identifier("c","copper_ore"));
	public static final Tag<Block> TIN_ORE = TagRegistry.block(new Identifier("c","tin_ore"));
	public static final Tag<Block> SILVER_ORE = TagRegistry.block(new Identifier("c","silver_ore"));
	public static final ItemGroup TAB = FabricItemGroupBuilder.build(
			new Identifier("scf", "tab"),
			() -> new ItemStack(SCF.COBBLESTONE_FUNNEL_BLOCK));
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ConfigUtils.checkConfigs();
		registerBlocks();
		registerItems();
		OreManager.makeOreTable();
		System.out.println("Hello Fabric world!");
	}
	public static CobblestoneFunnelBlock COBBLESTONE_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock COPPER_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock TIN_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock BRONZE_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock IRON_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock SILVER_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock GOLD_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock DIAMOND_FUNNEL_BLOCK;
	public static CobblestoneFunnelBlock NETHERITE_FUNNEL_BLOCK;
	private void registerBlocks()
	{
		Map<String,String> c = ConfigUtils.config;
		COBBLESTONE_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("cobble-tier")),Integer.parseInt(c.get("cobble-speed")));
		IRON_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("iron-tier")),Integer.parseInt(c.get("iron-speed")));
		COPPER_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("copper-tier")),Integer.parseInt(c.get("copper-speed")));
		TIN_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("tin-tier")),Integer.parseInt(c.get("tin-speed")));
		BRONZE_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("bronze-tier")),Integer.parseInt(c.get("bronze-speed")));
		SILVER_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("silver-tier")),Integer.parseInt(c.get("silver-speed")));
		GOLD_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("gold-tier")),Integer.parseInt(c.get("gold-speed")));
		DIAMOND_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("diamond-tier")),Integer.parseInt(c.get("diamond-speed")));
		NETHERITE_FUNNEL_BLOCK = new CobblestoneFunnelBlock(AbstractBlock.Settings.of(Material.STONE).ticksRandomly().strength(1.5f,0),Integer.parseInt(c.get("netherite-tier")),Integer.parseInt(c.get("netherite-speed")));



		Registry.register(Registry.BLOCK,new Identifier("scf","cobblestone_funnel"),COBBLESTONE_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","iron_funnel"),IRON_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","copper_funnel"),COPPER_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","tin_funnel"),TIN_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","bronze_funnel"),BRONZE_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","silver_funnel"),SILVER_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","gold_funnel"),GOLD_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","diamond_funnel"),DIAMOND_FUNNEL_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("scf","netherite_funnel"),NETHERITE_FUNNEL_BLOCK);
	}
	private void registerItems()
	{
		Registry.register(Registry.ITEM,new Identifier("scf","cobblestone_funnel"),new BlockItem(COBBLESTONE_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","iron_funnel"),new BlockItem(IRON_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","copper_funnel"),new BlockItem(COPPER_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","tin_funnel"),new BlockItem(TIN_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","bronze_funnel"),new BlockItem(BRONZE_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","silver_funnel"),new BlockItem(SILVER_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","gold_funnel"),new BlockItem(GOLD_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","diamond_funnel"),new BlockItem(DIAMOND_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
		Registry.register(Registry.ITEM,new Identifier("scf","netherite_funnel"),new BlockItem(NETHERITE_FUNNEL_BLOCK,new Item.Settings().group(TAB)));
	}



}
