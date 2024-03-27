package com.dolthhaven.dolt_mod_how.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class DoltModHowConfig {
    public static class Common {
        public final ConfigValue<Boolean> doMetalOresDropXP;
        public final ConfigValue<Boolean> doDistanceBasedWandererBootSpawning;
        public final ConfigValue<Boolean> doRichSoilGrowFungusColony;
        public final ConfigValue<Boolean> doHackyQuiverRecipe;
        public final ConfigValue<Boolean> doUnbloatKnifeEnchants;
        public final ConfigValue<Boolean> doDispenserCauldrons;
        public final ConfigValue<Boolean> doLightningEpilogueDisc;
        public final ConfigValue<Boolean> overhaulTipsyOverlay;


        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Vanilla");
            builder.push("Metal Ores");
            doMetalOresDropXP = builder.comment("If mining ores that normally drop raw ores should grant XP").define("Experienced metal", true);

            builder.pop();

            builder.push("Dispensers");
            doDispenserCauldrons = builder.comment("If dispensers should be able to dispense into cauldrons").define("Dispenser Cauldrons", true);

            builder.pop();
            builder.pop();

            builder.push("Farmer's Delight");
            builder.push("Knife Enchantments");
            doUnbloatKnifeEnchants = builder.comment("If knives should no longer receive the silk touch and efficiency from the enchanting table").define("Unbloated Knife Enchantments", true);

            builder.pop();
            builder.pop();

            builder.push("Brewin' and Chewin'");
            builder.push("Tipsy");
            overhaulTipsyOverlay = builder.comment("If the tipsy effect should, instead of doing whatever it does now, "+
                    "should act as a progressively worsening nausea effect. TO FULLY DISABLE THIS FEATURE, PLEASE " +
                    "ALSO TURN OFF \"WEIRD TIPSY\" IN THE COMMON CONFIG").define("Weird Tipsy", true);

            builder.pop();
            builder.pop();

            builder.push("Nether's Delight");
            builder.push("Fungus Colonies");

            doRichSoilGrowFungusColony = builder.comment("If fungus colonies should grow on normal rich soil instead of soul rich soil").define("Rich Fungus", true);

            builder.pop();
            builder.pop();

//            builder.push("Brewing and Chewing");
//            builder.push("Tipsy");
//            doBetterTipsy = builder.comment("If, instead of applying effects based on its level, the Tipsy effect should have apply a progressively "+
//                    "worsening nausea effect to the player.").define("Nauseaic Tipsy", true);
//
//            builder.pop();
//            builder.pop();

            builder.push("Environmental");
            builder.push("Wanderer's Boots");

            doDistanceBasedWandererBootSpawning = builder.comment("If mobs will spawn with wanderer's boots " +
                    "with some odds proportional to how far they are away from the 0, 0").define("Cool Boots", true);

            builder.pop();
            builder.pop();

            builder.push("Supplementaries");
            builder.push("Quivers");

            doHackyQuiverRecipe = builder.comment("If processing a quiver in a cutting board will eject all the arrows in that quiver")
                    .define("Realistic Quivers", true);

            builder.pop();
            builder.pop();

            builder.push("Anchor");
            builder.push("Epilogue Disc");

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
