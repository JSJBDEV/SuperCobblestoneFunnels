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

    public static Map<String,String> config = new HashMap<>();


    public static Map<String,String> loadConfigs()
    {
        File file = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/config.acfg");
        try {
            List<String> lines = FileUtils.readLines(file,"utf-8");
            lines.forEach(line->
            {
                if(line.charAt(0)!='#')
                {
                    String noSpace = line.replace(" ","");
                    String[] entry = noSpace.split("=");
                    config.put(entry[0],entry[1]);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static void generateConfigs(List<String> input)
    {
        File file = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/config.acfg");

        try {
            FileUtils.writeLines(file,input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> checkConfigs()
    {
        if(new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/config.acfg").exists())
        {
            return loadConfigs();
        }
        generateConfigs(makeDefaults());
        return loadConfigs();
    }

    private static List<String> makeDefaults()
    {
        List<String> defaults = new ArrayList<>();
        defaults.add("#cobblestone funnel tier (default 1)");
        defaults.add("cobble-tier=1");
        defaults.add("#cobblestone funnel speed (default 60)");
        defaults.add("cobble-speed=60");

        defaults.add("#copper funnel tier (default 2)");
        defaults.add("copper-tier=2");
        defaults.add("#copper funnel speed (default 60)");
        defaults.add("copper-speed=60");

        defaults.add("#tin funnel tier (default 2)");
        defaults.add("tin-tier=2");
        defaults.add("#tin funnel speed (default 60)");
        defaults.add("tin-speed=60");

        defaults.add("#bronze funnel tier (default 3)");
        defaults.add("bronze-tier=3");
        defaults.add("#bronze funnel speed (default 50)");
        defaults.add("bronze-speed=50");

        defaults.add("#silver funnel tier (default 4)");
        defaults.add("silver-tier=4");
        defaults.add("#silver funnel speed (default 60)");
        defaults.add("silver-speed=60");

        defaults.add("#iron funnel ore tier (default 4)");
        defaults.add("iron-tier=4");
        defaults.add("#iron funnel speed (default 50)");
        defaults.add("iron-speed=50");

        defaults.add("#gold funnel ore tier (default 5)");
        defaults.add("gold-tier=5");
        defaults.add("#gold funnel speed (default 40)");
        defaults.add("gold-speed=40");

        defaults.add("#diamond funnel ore tier (default 6)");
        defaults.add("diamond-tier=6");
        defaults.add("#diamond funnel speed (default 40)");
        defaults.add("diamond-speed=40");

        defaults.add("#netherite funnel ore tier (default 7)");
        defaults.add("netherite-tier=7");
        defaults.add("#netherite funnel speed (default 20)");
        defaults.add("netherite-speed=20");
        return defaults;
    }

}
