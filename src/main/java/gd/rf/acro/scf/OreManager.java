package gd.rf.acro.scf;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.collection.WeightedList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreManager {
	public static final List<WeightedList<Identifier>> weightedListCollection = new ArrayList<>();
	private static final String[] DEFAULT_ORES = {
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

	public static void makeOreTable() {
		File file = Path.of(FabricLoader.getInstance().getConfigDirectory().getPath(), "SCF", "ores.acfg").toFile();

		if (!file.exists()) {
			try {
				FileUtils.writeLines(file, Arrays.asList(DEFAULT_ORES));
			} catch (IOException e) {
				// something went wrong with creating the files
				e.printStackTrace();
			}
		}

		try {
			List<String> lines = FileUtils.readLines(file, "utf-8");

			lines.parallelStream()
					.map(e -> e.replaceAll("\\s", "")) // remove spaces
					.filter(e -> e.matches("([a-z0-9_.-]+:)?[a-z0-9_.-]+,\\d+,\\d+")) // is valid format
					.map(e -> e.split(",")) // split arguments
					.sorted((a, b) -> -a[1].compareTo(b[1])) // sort it so that highest tier is done first
					.forEachOrdered(OreManager::setWeights);

			lines.parallelStream()
					.map(e -> e.replace("\\s", "")) // remove spaces
					.filter(e -> !e.matches("([a-z0-9_.-]+:)?[a-z0-9_.-]+,\\d+,\\d+")) // is not valid format
					.forEachOrdered(e -> {
						SCF.LOGGER.warning("invalid format");
						SCF.LOGGER.warning(e);
					});
		} catch (IOException e) {
			// something went wrong with loading the files
			e.printStackTrace();
		}
	}

	private static void setWeights(String[] ctx) {
		Identifier id;
		int tier;
		int weight;

		try {
			id = new Identifier(ctx[0]);
		} catch (InvalidIdentifierException e) {
			SCF.LOGGER.warning("invalid config identifier");
			SCF.LOGGER.warning("[" + ctx[0] + "], " + ctx[1] + ", " + ctx[2]);
			return;
		}

		try {
			tier = Integer.parseInt(ctx[1]);

			if (tier < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			SCF.LOGGER.warning("invalid tier number");
			SCF.LOGGER.warning(ctx[0] + ", [" + ctx[1] + "], " + ctx[2]);
			return;
		}

		try {
			weight = Integer.parseInt(ctx[2]);
		} catch (NumberFormatException e) {
			SCF.LOGGER.warning("invalid weight number");
			SCF.LOGGER.warning(ctx[0] + ", " + ctx[1] + ", [" + ctx[2] + "]");
			return;
		}

		for (int i = weightedListCollection.size(); i <= tier; i++) {
			weightedListCollection.add(new WeightedList<>());
		}

		for (int i = tier; i < weightedListCollection.size(); i++) {
			weightedListCollection.get(i).add(id, weight);
		}
	}
}
