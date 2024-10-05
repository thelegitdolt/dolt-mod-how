package com.dolthhaven.dolt_mod_how.core.util;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class Util {


    public static void printItem(Item item) {
        ResourceLocation res = ForgeRegistries.ITEMS.getKey(item);
        if (res == null) {
            DoltModHow.LOGGER.info("DoltModHow.Util.printItem used, but no Item was found!!");
        }
        else {
            DoltModHow.LOGGER.info(res.toString());
        }

    }

    public static @Nullable Item getPotentialItem(String path, String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(path, name));
    }


    public static @Nullable Block getPotentialBlock(String path, String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(path, name));
    }


    public static class Constants {
        public static final String MY_NETHERS_DELIGHT = "mynethersdelight";
        public static final String ENVIRONMENTAL = "environmental";
        public static final String QUARK = "quark";
        public static final String FARMERS_DELIGHT = "farmersdelight";


        public static final ResourceLocation STURDY_STONE = new ResourceLocation(QUARK, "sturdy_stone");

    }
}
