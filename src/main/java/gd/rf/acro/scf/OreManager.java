package gd.rf.acro.scf;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreManager {
    public static List<String> ORES;
    private static final String[] ores_default =
            {
                    //	ore/tag, funnel tier, weight
                    "minecraft:cobblestone,1,1",
                    "minecraft:stone,1,1",
                    "minecraft:coal_ore,1,1",
                    "minecraft:iron_ore,1,1",
                    "minecraft:redstone_ore,2,1",
                    "minecraft:nether_quartz_ore,2,1",
                    "minecraft:gold_ore,2,1",
                    "minecraft:diamond_ore,3,1",
                    "minecraft:ancient_debris,3,1",

            };

    public static void makeOreTable(){
        File file = new File(FabricLoader.getInstance().getConfigDirectory().getPath() + "/SCF/ores.acfg");

        try {
            if(!file.exists())
            {
                FileUtils.writeLines(file, Arrays.asList(ores_default));
            }
            ORES = FileUtils.readLines(file,"utf-8");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Block getRandomBlock(int tier)
    {

        List<String[]> ret = new ArrayList<>();
        for (String ore : ORES) {
            String[] ctx = ore.split(",");
            if (Integer.parseInt(ctx[1]) <= tier) {
                ret.add(ctx);
            }
        }
        Block block=null;
        while (block==null)
        {
            String[] test = ret.get(RandomUtils.nextInt(0,ret.size()));
            if(RandomUtils.nextInt(0,Integer.parseInt(test[2]))==0)
            {
                block= Registry.BLOCK.get(Identifier.tryParse(test[0]));
            }
        }
        return block;
    }
}
