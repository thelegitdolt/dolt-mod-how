package com.dolthhaven.dolt_mod_how.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class DMHConfig {
    public static class Common {
        public final ConfigValue<Boolean> doMetalOresDropXP;
        public final ConfigValue<Boolean> doCropBlocksDropXP;
        public final ConfigValue<Integer> minCropXpDrops;
        public final ConfigValue<Integer> maxCropXpDrops;
        public final ConfigValue<Boolean> xpUponBlockPlace;
        public final ConfigValue<Integer> blockPlaceXpChance;

        public final ConfigValue<Boolean> muteExFriendlyAnvils;
        public final ConfigValue<Boolean> lessAnnoyingItemReclaim;
        public final ConfigValue<Double> valuePerRepair;

        public final ConfigValue<Boolean> doRichSoilGrowFungusColony;
        public final ConfigValue<Boolean> doHackyQuiverRecipe;
        public final ConfigValue<Boolean> doUnbloatKnifeEnchants;
        public final ConfigValue<Boolean> doDispenserCauldrons;
        public final ConfigValue<Boolean> doUntillableFarmland;
        public final ConfigValue<Boolean> removeOrangeVapor;

        public final ConfigValue<Boolean> killBulletPepperPlacement;
        public final ConfigValue<Boolean> frogsAreNotStupid;

        public final ConfigValue<Boolean> acidCorrodesCopper;
        public final ConfigValue<Boolean> sackOfSatingNoRestoreSat;
        public final ConfigValue<Boolean> actuallyGoodBiomeTreats;

        public final ConfigValue<Boolean> damageReductions;

        public final ConfigValue<String> discToSpawnAfterJukeboxStruckWithLightning;



        Common(ForgeConfigSpec.Builder builder) {
            builder.push("misc");
            lessAnnoyingItemReclaim = builder.comment("If interacting with blocks like Flower Pots and Amendment Placeable Books should put the item they contain into your inventory, thus merging with existing stacks, instead of adding it to your hand, which is EXTREMELY ANNOYING")
                            .define("Less Annoying Item Reclaim", true);

            builder.push("Vanilla");
            builder.push("metal_ores");
            doMetalOresDropXP = builder.comment("If mining ores that normally drop raw ores should grant XP").define("Experienced metal", true);
            builder.pop();

            builder.push("farmland");
            doUntillableFarmland = builder.comment("Whether farmland can be untilled by sneak-right clicking them with a hoe").define("Untillable Farmland", true);
            builder.pop();

            builder.push("disc");
            discToSpawnAfterJukeboxStruckWithLightning = builder.comment("The disc that should be spawned when a jukebox is struck with lightning. Disables if string is \"no\"").define("Lightning Disc", "minecraft:music_disc_ward");
            builder.pop();

            builder.push("xp");
            xpUponBlockPlace = builder.comment("If placing a block should have a chance to yield experience.").define("Experienced Building", true);
            blockPlaceXpChance = builder.comment("Specify n here, where the chance of a block dropping xp is 1/n. Integers only!").define("Building XP chance", 128);
            doCropBlocksDropXP = builder.comment("If all blocks that are crops should drop experience.").define("Experienced Crops", true);
            minCropXpDrops = builder.comment("Minimum XP a crop block will drop when broken.").define("Minimum Crop XP", 0);
            maxCropXpDrops = builder.comment("Maximum XP a crop block will drop when broken.").define("Maximum Crop XP", 1);
            builder.pop();

            builder.push("anvil");
            muteExFriendlyAnvils = builder.comment("Allow applying enchantment books to items with incompatible enchants. Voids incompatible enchants from the tool.").define("MutEx Friendly Anvils", true);
            valuePerRepair = builder.comment("The percentage of durability much each material should repair on a tool, in an anvil. 0.25 in vanilla.").define("Repair Item Amount", 0.33);
            builder.pop();

            builder.push("dispensers");
            doDispenserCauldrons = builder.comment("If dispensers should be able to dispense into cauldrons").define("Dispenser Cauldrons", true);
            builder.pop();

            builder.push("enchantments");
            damageReductions = builder.comment("If enchantments other than protection should be buffed. Experimental!").define("Erm what the sigma", false);
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

            builder.push("nethersdelight");

            builder.push("fungus_colonies");

            doRichSoilGrowFungusColony = builder.comment("If fungus colonies should grow on normal rich soil instead of soul rich soil").define("Rich Fungus", true);

            builder.pop();

            builder.push("bullet_peppers");
            killBulletPepperPlacement = builder.comment("If bullet peppers should become unplaceable, thus killing letios plants forever").define("Kill letios plants", true);
            builder.pop();

            builder.push("magma_cakes");
            frogsAreNotStupid = builder.comment("If frogs should become unable to consume magma cakes").define("Magma Cakes Good", false);
            builder.pop();

            builder.pop();

            builder.push("supplementaries");
            builder.push("quivers");

            doHackyQuiverRecipe = builder.comment("If processing a quiver in a cutting board will eject all the arrows in that quiver")
                    .define("Realistic Quivers", true);

            builder.pop();
            builder.pop();

            builder.push("Alexander Caverns");

            builder.push("Acid");
            acidCorrodesCopper = builder.comment("If acid should not corrode copper")
                    .define("Good Acid", true);
            builder.pop();

            builder.push("Biome Treats");
            actuallyGoodBiomeTreats = builder.comment("If biome treats should work regardless of hunger")
                    .define("Okay Treats", true);
            builder.pop();

            builder.push("Sack of Sating");
            sackOfSatingNoRestoreSat = builder.comment("If sacks of sating no longer restore saturation")
                    .define("Mid sack of sating", false);
            builder.pop();

            builder.pop();
        }
    }

    public static class Client {
        Client(ForgeConfigSpec.Builder builder) {
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
