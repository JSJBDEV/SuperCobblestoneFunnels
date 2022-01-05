package gd.rf.acro.scf;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ConfigUtils {
	public static Map<String, Integer> config = new HashMap<>();
	private static final File file = Path.of(FabricLoader.getInstance().getConfigDirectory().getPath(), "SCF", "config.acfg").toFile();

	public static Map<String, Integer> loadConfigs() {
		try {
			FileUtils.readLines(file, "utf-8").stream()
					.map(e -> e.replaceAll("\\s", "")) // remove spaces
					.filter(e -> e.matches("^[^#]\\w+=\\d+$")) // is valid format
					.map(e -> e.split("=")) // split arguments
					.forEach(entry -> config.put(entry[0], Integer.parseInt(entry[1])));
		} catch (IOException e) {
			// something went wrong with loading the files
			e.printStackTrace();
		}
		return config;
	}

	private static void generateConfigs(List<String> input) {
		try {
			FileUtils.writeLines(file, input);
		} catch (IOException e) {
			// something went wrong with creating the files
			e.printStackTrace();
		}
	}

	public static Map<String, Integer> checkConfigs() {
		if (!file.exists()) {
			generateConfigs(makeDefaults());
		}
		return loadConfigs();
	}

	private static List<String> makeDefaults() {
		return Arrays.asList(
				"#cobblestone funnel ore level (default 0)",
				"cobblelvl=0",
				"#cobblestone funnel speed (default 60)",
				"cobblespeed=60",
				"",
				"#copper funnel ore level (default 1)",
				"copperlvl=1",
				"#copper funnel speed (default 60)",
				"copperspeed=60",
				"",
				"#bronze funnel ore level (default 1)",
				"bronzelvl=1",
				"#bronze funnel speed (default 30)",
				"bronzespeed=30",
				"",
				"#iron funnel ore level (default 2)",
				"ironlvl=2",
				"#iron funnel speed (default 50)",
				"ironspeed=50",
				"",
				"#gold funnel ore level (default 3)",
				"goldlvl=3",
				"#gold funnel speed (default 40)",
				"goldspeed=40",
				"",
				"#diamond funnel ore level (default 4)",
				"diamondlvl=4",
				"#diamond funnel speed (default 40)",
				"diamondspeed=40",
				"",
				"#netherite funnel ore level (default 5)",
				"netheritelvl=5",
				"#netherite funnel speed (default 20)",
				"netheritespeed=20");
	}
}
