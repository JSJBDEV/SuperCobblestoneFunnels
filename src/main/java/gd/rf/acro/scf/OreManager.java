package gd.rf.acro.scf;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.collection.WeightedList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreManager {
	public static final List<WeightedList<Identifier>> weightedListCollection = new ArrayList<>();
	private static final String[] DEFAULT_ORES = {
			//	ore, funnel tier, weight
			"minecraft:cobblestone,0,512",
			"minecraft:stone,0,512",
			"minecraft:coal_ore,0,256",
			"minecraft:copper_ore,0,128",
			"minecraft:iron_ore,0,128",
			"minecraft:redstone_ore,1,64",
			"minecraft:nether_quartz_ore,1,64",
			"minecraft:gold_ore,1,64",
			"minecraft:diamond_ore,2,16",
			"minecraft:ancient_debris,2,1",
	};

	public static void makeOreTable() {
		File file = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "SCF", "ores.acfg").toFile();

		// write default values if no file exists
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

			// load all data
			lines.parallelStream()
					.map(e -> e.replaceAll("\\s", "")) // remove spaces
					.filter(e -> e.matches("([a-z0-9_.-]+:)?[a-z0-9_.-]+,\\d+,\\d+")) // is valid format
					.map(e -> e.split(",")) // split arguments
					.sorted((a, b) -> -a[1].compareTo(b[1])) // sort it so that highest tier is done first
					.forEachOrdered(OreManager::setWeights);

			// let user know if the config format was wrong
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

	/**
	 * A helper to set all the weights
	 * Must be called with the highest tier first
	 *
	 * @param ctx The params for this value, in the order [Identifier, tier, weight]
	 */
	private static void setWeights(String[] ctx) {
		Identifier id;
		int tier;
		int weight;

		try {
			id = new Identifier(ctx[0]);
		} catch (InvalidIdentifierException e) {
			// shouldn't happen with regex check
			SCF.LOGGER.warning("invalid config identifier");
			SCF.LOGGER.warning("[" + ctx[0] + "], " + ctx[1] + ", " + ctx[2]);
			return;
		}

		try {
			tier = Integer.parseInt(ctx[1]);

			if (tier < 0) {
				throw new NumberFormatException(); // get into the catch
			}
		} catch (NumberFormatException e) {
			// shouldn't happen with regex check
			SCF.LOGGER.warning("invalid tier number");
			SCF.LOGGER.warning(ctx[0] + ", [" + ctx[1] + "], " + ctx[2]);
			return;
		}

		try {
			weight = Integer.parseInt(ctx[2]);
		} catch (NumberFormatException e) {
			// shouldn't happen with regex check
			SCF.LOGGER.warning("invalid weight number");
			SCF.LOGGER.warning(ctx[0] + ", " + ctx[1] + ", [" + ctx[2] + "]");
			return;
		}

		// add new lists if they don't exist yet
		for (int i = weightedListCollection.size(); i <= tier; i++) {
			weightedListCollection.add(new WeightedList<>());
		}

		// fill the list with their weights
		for (int i = tier; i < weightedListCollection.size(); i++) {
			weightedListCollection.get(i).add(id, weight);
		}
	}
}
