package com.dolthhaven.dolt_mod_how.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class DoltModHowConfig {
    public static class Common {
        public final ConfigValue<Boolean> doMetalOresDropXP;
        public final ConfigValue<Boolean> doCropBlocksDropXP;
        public final ConfigValue<Integer> minCropXpDrops;
        public final ConfigValue<Integer> maxCropXpDrops;

        public final ConfigValue<Boolean> doRichSoilGrowFungusColony;
        public final ConfigValue<Boolean> doHackyQuiverRecipe;
        public final ConfigValue<Boolean> doUnbloatKnifeEnchants;
        public final ConfigValue<Boolean> doDispenserCauldrons;
        public final ConfigValue<Boolean> doLightningEpilogueDisc;
        public final ConfigValue<Boolean> doUntillableFarmland;
        public final ConfigValue<Boolean> removeOrangeVapor;
        public final ConfigValue<Boolean> overhaulTipsyOverlay;


        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Vanilla");
            builder.push("metal_ores");
            doMetalOresDropXP = builder.comment("If mining ores that normally drop raw ores should grant XP").define("Experienced metal", true);
            builder.pop();

            builder.push("farmland");
            doUntillableFarmland = builder.comment("Whether farmland can be untilled by sneak-right clicking them with a hoe").define("Untillable Farmland", true);
            builder.pop();

            builder.push("crops");
            doCropBlocksDropXP = builder.comment("If all blocks that are crops should drop experience.").define("Experienced Crops", true);
            minCropXpDrops = builder.comment("Minimum XP a crop block will drop when broken.").define("Minimum Crop XP", 0);
            maxCropXpDrops = builder.comment("Maximum XP a crop block will drop when broken.").define("Maximum Crop XP", 1);
            builder.pop();

            builder.push("dispensers");
            doDispenserCauldrons = builder.comment("If dispensers should be able to dispense into cauldrons").define("Dispenser Cauldrons", true);
            builder.pop();


            builder.pop();

            builder.push("atmospheric");
            builder.push("Oranges");
            removeOrangeVapor = builder.comment("If oranges can no longer be jumped on to be broken and to create a vapor cloud").define("Normal Oranges", true);

            builder.pop();
            builder.pop();

            builder.push("farmersdelight");
            builder.push("Knife Enchantments");
            doUnbloatKnifeEnchants = builder.comment("If knives should no longer receive the silk touch and efficiency from the enchanting table").define("Unbloated Knife Enchantments", true);

            builder.pop();
            builder.pop();

            builder.push("brewin_and_chewin");
            builder.push("Tipsy");
            overhaulTipsyOverlay = builder.comment("If the tipsy effect should, instead of doing whatever it does now, "+
                    "should act as a progressively worsening nausea effect. TO FULLY DISABLE THIS FEATURE, PLEASE " +
                    "ALSO TURN OFF \"WEIRD TIPSY\" IN THE COMMON CONFIG").define("Weird Tipsy", true);

            builder.pop();
            builder.pop();

            builder.push("nethersdelight");
            builder.push("fungus_colonies");

            doRichSoilGrowFungusColony = builder.comment("If fungus colonies should grow on normal rich soil instead of soul rich soil").define("Rich Fungus", true);

            builder.pop();
            builder.pop();

            builder.push("supplementaries");
            builder.push("quivers");

            doHackyQuiverRecipe = builder.comment("If processing a quiver in a cutting board will eject all the arrows in that quiver")
                    .define("Realistic Quivers", true);

            builder.pop();
            builder.pop();

            builder.push("anchor");
            builder.push("epilogue Disc");

            doLightningEpilogueDisc = builder.comment("If striking a jukebox with lightning with a filled disc inside will change that disc to Epilogue")
                    .define("AWESOME epilogue disc", true);

            builder.pop();
            builder.pop();
        }
    }

    public static class Client {
        public final ConfigValue<Boolean> removeSullyGrindstoneTooltip;
        public final ConfigValue<Boolean> overhaulTipsyOverlay;
        Client(ForgeConfigSpec.Builder builder) {
            builder.push("Sully's Mod");
            builder.push("Grindstone Tooltip");
            removeSullyGrindstoneTooltip = builder.comment("If the tooltips appended to grindable items in Sully's Mod should be removed").define("No Grindstone Tooltip", true);

            builder.pop();
            builder.pop();

            builder.push("Brewin' and Chewin'");
            builder.push("Tipsy");
            overhaulTipsyOverlay = builder.comment("If the tipsy effect should, instead of doing whatever it does now, "+
                    "should act as a progressively worsening nausea effect. TO FULLY DISABLE THIS FEATURE, PLEASE " +
                    "ALSO TURN OFF \"WEIRD TIPSY\" IN THE COMMON CONFIG").define("Weird Tipsy", true);

            builder.pop();
            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;


    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
