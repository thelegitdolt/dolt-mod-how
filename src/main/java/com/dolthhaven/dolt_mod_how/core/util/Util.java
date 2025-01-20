package com.dolthhaven.dolt_mod_how.core.util;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Util {
    public static void ifThen(boolean condition, Runnable then) {
        if (condition) then.run();
    }

    public static void printItem(Item item) {
        ResourceLocation res = ForgeRegistries.ITEMS.getKey(item);
        if (res == ForgeRegistries.ITEMS.getDefaultKey()) {
            DoltModHow.LOGGER.info("DoltModHow.Util.printItem used, but no Item was found!!");
        }
        else {
            DoltModHow.LOGGER.info(res.toString());
        }

    }

    public static @Nullable Item getPotentialItem(ResourceLocation loc) {
        return ForgeRegistries.ITEMS.getValue(loc);
    }

    public static @Nullable Item getPotentialItem(String path, String name) {
        return getPotentialItem(new ResourceLocation(path, name));
    }


    public static @Nullable Block getPotentialBlock(ResourceLocation loc) {
        Block block = ForgeRegistries.BLOCKS.getValue(loc);
        return block == Blocks.AIR ? null : block;
    }

    public static @Nullable Block getPotentialBlock(String path, String name) {
       return getPotentialBlock(new ResourceLocation(path, name));
    }

    public static @NotNull Fluid getFluidOrWater(String path, String name) {
        return getFluidOrWater(new ResourceLocation(path, name));
    }

    public static @NotNull Fluid getFluidOrWater(ResourceLocation location) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(location);
        return fluid != null ? fluid: Fluids.WATER;
    }

    public static class Constants {
        public static final String ALEXS_CAVES = "alexscaves";
        public static final String ATMOSPHERIC = "atmospheric";
        public static final String CAVERNS_AND_CHASMS = "caverns_and_chasms";
        public static final String CREATE = "create";
        public static final String ENVIRONMENTAL = "environmental";
        public static final String FARMERS_DELIGHT = "farmersdelight";
        public static final String MY_NETHERS_DELIGHT = "mynethersdelight";
        public static final String NEAPOLITAN = "neapolitan";
        public static final String JNE = "netherexp";
        public static final String OREGANIZED = "oreganized";
        public static final String QUARK = "quark";
        public static final String SULLYSMOD = "sullysmod";
        public static final String UPGRADE_AQUATIC = "upgrade_aquatic";
        public static final String SPECIES = "species";


        public static final ResourceLocation STURDY_STONE = new ResourceLocation(QUARK, "sturdy_stone");
        public static final ResourceLocation RED_MUSHROOM_COLONIES = new ResourceLocation(FARMERS_DELIGHT, "red_mushroom_colonies");
        public static final ResourceLocation BULLET_PEPPER = new ResourceLocation(MY_NETHERS_DELIGHT, "bullet_pepper");

        public static final ResourceLocation GOLDEN_LAVA_BUCKET = new ResourceLocation(CAVERNS_AND_CHASMS, "golden_lava_bucket");

        public static final ResourceLocation BANANA_PEEL = new ResourceLocation(NEAPOLITAN, "banana_peel");
        public static final ResourceLocation MYCELIUM_SPROUTS = new ResourceLocation(ENVIRONMENTAL, "mycelium_sprouts");
        public static final ResourceLocation ALPHACENE_GRASS_BLOCK = new ResourceLocation(SPECIES, "alphacene_grass_block");

        public static final ResourceLocation TALL_BEACHGRASS = new ResourceLocation(UPGRADE_AQUATIC, "tall_beachgrass");
        public static final ResourceLocation BEACHGRASS = new ResourceLocation(UPGRADE_AQUATIC, "beachgrass");

        public static final ResourceLocation BLACK_ROCK_CANDY = new ResourceLocation(ALEXS_CAVES, "rock_candy_black");
        public static final ResourceLocation GALENA = new ResourceLocation(ALEXS_CAVES, "galena");


        public static final ResourceLocation ACID = new ResourceLocation(ALEXS_CAVES, "acid");
        public static final ResourceLocation PURPLE_SODA = new ResourceLocation(ALEXS_CAVES, "purple_soda");
        public static final ResourceLocation MOLTEN_LEAD = new ResourceLocation(OREGANIZED, "molten_lead");
    }
}
