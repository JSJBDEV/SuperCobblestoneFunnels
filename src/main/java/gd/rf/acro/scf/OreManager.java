package gd.rf.acro.scf;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.WeightedList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreManager {
	private static final String[] ores_default = {
			//	ore/tag, funnel tier, weight
			"minecraft:cobblestone,0,100",
			"minecraft:stone,0,100",
			"minecraft:coal_ore,0,100",
			"c:tin_ore,0,100",
			"c:copper_ore,0,100",
			"minecraft:iron_ore,1,100",
			"c:zinc_ore,1,100",
			"c:aluminum_ore,1,100",
			"c:lead_ore,1,100",
			"c:silver_ore,2,100",
			"minecraft:redstone_ore,2,100",
			"minecraft:nether_quartz_ore,2,100",
			"minecraft:gold_ore,2,100",
			"c:uranium_ore,2,100",
			"minecraft:diamond_ore,3,20",
			"c:platinum_ore,3,20",
			"c:tungsten_ore,3,20",
			"c:osmium_ore,4,20",
			"c:palladium_ore,4,20",
			"c:amethyst_ore,4,20",
			"c:iridium_ore,4,20",
			"c:topaz_ore,4,20",
			"c:cobalt_ore,4,20",
			"c:peridot_ore,4,20",
			"c:sapphire_ore,4,20",
			"c:ruby_ore,4,20",
			"minecraft:ancient_debris,4,1"
	};

	public static final List<WeightedList<Identifier>> WEIGHTED_LIST_COLLECTION = new ArrayList<>();

	public static void makeOreTable() {
		File file = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/ores.acfg");

		try {
			if (!file.exists()) {
				FileUtils.writeLines(file, Arrays.asList(ores_default));
			}

			FileUtils.readLines(file, "utf-8").parallelStream()
					.map(e -> e.replace(" ", "")) // remove spaces
					.filter(e -> e.matches("([a-z0-9_.-]+:)?[a-z0-9_.-]+,\\d+,\\d+")) // is valid format
					.map(e -> e.split(",")) // split arguments
					.sorted((a, b) -> -a[1].compareTo(b[1])) // sort it so that highest tier is done first
					.forEachOrdered(OreManager::setWeights);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setWeights(String[] ctx) {
		try {
			Identifier id = new Identifier(ctx[0]);
			int tier = Integer.parseInt(ctx[1]);
			int weight = Integer.parseInt(ctx[2]);

			if (tier < 0) {
				System.err.println("invalid config tier");
				System.err.println(ctx[0] + ", [" + ctx[1] + "], " + ctx[2]);
				return;
			}

			for (int i = WEIGHTED_LIST_COLLECTION.size(); i <= tier; i++) {
				WEIGHTED_LIST_COLLECTION.add(new WeightedList<>());
			}

			for (int i = tier; i < WEIGHTED_LIST_COLLECTION.size(); i++) {
				WEIGHTED_LIST_COLLECTION.get(i).add(id, weight);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("invalid config identifier");
			System.err.println("[" + ctx[0] + "], " + ctx[1] + ", " + ctx[2]);
		}
	}
}
