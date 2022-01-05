package gd.rf.acro.scf;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigUtils {
	public static Map<String, String> config = new HashMap<>();
	private static final File file = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/config.acfg");

	public static Map<String, String> loadConfigs() {
		try {
			List<String> lines = FileUtils.readLines(file, "utf-8");
			lines.forEach(line -> {
				if (line.charAt(0) != '#') {
					String noSpace = line.replace(" ", "");
					String[] entry = noSpace.split("=");
					config.put(entry[0], entry[1]);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	public static void generateConfigs(List<String> input) {
		try {
			FileUtils.writeLines(file, input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> checkConfigs() {
		if (!file.exists()) {
			generateConfigs(makeDefaults());
		}
		return loadConfigs();
	}

	private static List<String> makeDefaults() {
		List<String> defaults = new ArrayList<>();
		defaults.add("#cobblestone funnel ore level (default 0)");
		defaults.add("cobblelvl=0");
		defaults.add("#cobblestone funnel speed (default 60)");
		defaults.add("cobblespeed=60");

		defaults.add("#copper funnel ore level (default 1)");
		defaults.add("copperlvl=1");
		defaults.add("#copper funnel speed (default 60)");
		defaults.add("copperspeed=60");

		defaults.add("#bronze funnel ore level (default 1)");
		defaults.add("bronzelvl=1");
		defaults.add("#bronze funnel speed (default 30)");
		defaults.add("bronzespeed=30");

		defaults.add("#iron funnel ore level (default 2)");
		defaults.add("ironlvl=2");
		defaults.add("#iron funnel speed (default 50)");
		defaults.add("ironspeed=50");

		defaults.add("#gold funnel ore level (default 3)");
		defaults.add("goldlvl=3");
		defaults.add("#gold funnel speed (default 40)");
		defaults.add("goldspeed=40");

		defaults.add("#diamond funnel ore level (default 4)");
		defaults.add("diamondlvl=4");
		defaults.add("#diamond funnel speed (default 40)");
		defaults.add("diamondspeed=40");

		defaults.add("#netherite funnel ore level (default 5)");
		defaults.add("netheritelvl=5");
		defaults.add("#netherite funnel speed (default 20)");
		defaults.add("netheritespeed=20");
		return defaults;
	}
}
