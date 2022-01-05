package gd.rf.acro.scf;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class ConfigUtils {
	public static Map<String, Integer> config = new HashMap<>();
	private static final File file = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "SCF", "config.acfg").toFile();

	public static Map<String, Integer> loadConfigs() {
		try {
			List<String> lines = FileUtils.readLines(file, "utf-8");
			lines.stream()
					.map(e -> e.replaceAll("\\s", "")) // remove spaces
					.filter(e -> e.matches("^[^#]\\w+(-\\w+)*=\\d+$")) // is valid format
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

	public static void checkConfigs() {
		if (!file.exists()) {
			generateConfigs(makeDefaults());
		}
		loadConfigs();
	}

	private static List<String> makeDefaults() {
		return Arrays.asList(
				"#cobblestone funnel ore level (default 0)",
				"cobble-tier=0",
				"#cobblestone funnel speed (default 60)",
				"cobble-speed=60",
				"",
				"#copper funnel ore level (default 1)",
				"copper-tier=1",
				"#copper funnel speed (default 60)",
				"copper-speed=60",
				"",
				"#tin funnel tier (default 1)",
				"tin-tier=1",
				"#tin funnel speed (default 60)",
				"tin-speed=60",
				"",
				"#bronze funnel ore level (default 2)",
				"bronze-tier=2",
				"#bronze funnel speed (default 50)",
				"bronze-speed=50",
				"",
				"#silver funnel tier (default 3)",
				"silver-tier=3",
				"#silver funnel speed (default 60)",
				"silver-speed=60",
				"",
				"#iron funnel ore level (default 3)",
				"iron-tier=3",
				"#iron funnel speed (default 50)",
				"iron-speed=50",
				"",
				"#gold funnel ore level (default 4)",
				"gold-tier=4",
				"#gold funnel speed (default 40)",
				"gold-speed=40",
				"",
				"#diamond funnel ore level (default 5)",
				"diamond-tier=5",
				"#diamond funnel speed (default 40)",
				"diamond-speed=40",
				"",
				"#netherite funnel ore level (default 6)",
				"netherite-tier=6",
				"#netherite funnel speed (default 20)",
				"netherite-speed=20");
	}
}
