package com.dolthhaven.dolt_mod_how.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
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
        public final ConfigValue<Boolean> replaceableMossCarpet;
        public final ConfigValue<Boolean> frogMistLikeHopeLights;
        public final ConfigValue<Boolean> lessAnnoyingSleep;
        public final ConfigValue<Integer> blockPlaceXpChance;

        public final ConfigValue<Boolean> lessAnnoyingItemReclaim;
        public final ConfigValue<Boolean> replaceSawmillWithStonecutter;
        public final ConfigValue<Boolean> hoesRakeSand;
        public final ConfigValue<Double> thunderstormMultiplier;

        public final ConfigValue<Boolean> doRichSoilGrowFungusColony;
        public final ConfigValue<Boolean> conqueringStar;
        @ConfigKey("quiver_recipe")
        public final ConfigValue<Boolean> doHackyQuiverRecipe;
        @ConfigKey("bookshelves_drop_themselves")
        public final ConfigValue<Boolean> bookshelvesDropThemselves;
        public final ConfigValue<Integer> stackSizeForSpeciesCrankbow;
        public final ConfigValue<Boolean> shouldCombustionGriefBlocks;
        public final ConfigValue<Boolean> doUnbloatKnifeEnchants;
        public final ConfigValue<Boolean> shouldPlaceBonePilesWithNormalBones;
        public final ConfigValue<Boolean> disableEndPoem;
        public final ConfigValue<Boolean> doDispenserCauldrons;
        public final ConfigValue<Boolean> doUntillableFarmland;
        public final ConfigValue<Boolean> removeOrangeVapor;

        public final ConfigValue<Boolean> killBulletPepperPlacement;
        public final ConfigValue<Boolean> frogsAreNotStupid;
        public final ConfigValue<Boolean> removeTremorzillaGrief;

        public final ConfigValue<Boolean> acidCorrodesCopper;
        public final ConfigValue<Boolean> sackOfSatingNoRestoreSat;
        public final ConfigValue<Boolean> actuallyGoodBiomeTreats;
        public final ConfigValue<Boolean> pathfinderQuillMakesCaveMaps;
        public final ConfigValue<Boolean> hideBeaconParticles;
        public final ConfigValue<Boolean> elytraInfiniteDurability;

        @ConfigKey("placeable_wardenzola")
        public final ConfigValue<Boolean> wheelifiedWardenzola;

        public final ConfigValue<Boolean> damageReductions;

        public final ConfigValue<String> discToSpawnAfterJukeboxStruckWithLightning;



        Common(ForgeConfigSpec.Builder builder) {
            builder.push("misc");
            lessAnnoyingItemReclaim = builder.comment("If interacting with blocks like Flower Pots and Amendment Placeable Books should put the item they contain into your inventory, thus merging with existing stacks, instead of adding it to your hand, which is EXTREMELY ANNOYING")
                            .define("Less Annoying Item Reclaim", true);
            builder.pop();

            builder.push("Vanilla");
            builder.push("metal_ores");
            doMetalOresDropXP = builder.comment("If mining ores that normally drop raw ores should grant XP").define("Experienced metal", true);
            builder.pop();

            builder.push("bed");
            lessAnnoyingSleep = builder.comment("If having monsters next to you or being too far from bed should no longer prevent you from sleeping").define("Good ahh sleep", true);
            builder.pop();

            builder.push("Thunderstorm rates");
            thunderstormMultiplier = builder.comment("An inverse multiplier to how often thunderstorms should happen. If you put 1/3 here thunderstorms happen 3 times more often.")
                            .define("Thunderstorm Multiplier", 0.75D);
            builder.pop();


            builder.push("bookshelves");
            bookshelvesDropThemselves = builder.comment("If bookshelves and chiseled bookshelves should drop themselves without silk touch").define("Bookshelves Drop Themselves", true);
            builder.pop();

            builder.push("elytra");
            elytraInfiniteDurability = builder.comment("If elytras should still work when they have 0 durability. Don't turn this on unless you also use another mod to make elytras unbreakable").define("Elytra infinite durability", false);
            builder.pop();

            builder.push("end_poem");
            disableEndPoem = builder.comment("If the end poem should never show up.").define("Opping End Poem", true);
            builder.pop();

            builder.push("beacons");
            hideBeaconParticles = builder.comment("If status effects given you by beacons should NOT give off potion particles").define("Hidden beacon particles", false);
            builder.pop();

            builder.push("farmland");
            doUntillableFarmland = builder.comment("Whether farmland can be untilled by sneak-right clicking them with a hoe").define("Untillable Farmland", true);
            builder.pop();

            builder.push("Moss carpet");
            replaceableMossCarpet = builder.comment("Whether Moss Carpets should become replaceable, like tall grass or something idk.").define("Replaceable Moss Carpet", true);
            builder.pop();

            builder.push("Stonecutter");
            replaceSawmillWithStonecutter = builder.comment("If Woodworks is loaded, whether all sawmill " +
                    "recipes should becomes stonecutter recipes instead.").define("Stonecutter more like woodcutter am I right", false);
            builder.pop();


            builder.push("disc");
            discToSpawnAfterJukeboxStruckWithLightning = builder.comment("The disc that should be spawned when a jukebox is struck with lightning. Disables if string is \"no\"").define("Lightning Disc", "minecraft:music_disc_ward");
            builder.pop();

            builder.push("xp");
            xpUponBlockPlace = builder.comment("If placing a block should have a chance to yield experience.").define("Experienced Building", true);
            blockPlaceXpChance = builder.comment("Specify n here, where the chance of a block dropping xp is 1/n. Integers only!").define("Building XP chance", 64);
            doCropBlocksDropXP = builder.comment("If all blocks that are crops should drop experience.").define("Experienced Crops", true);
            minCropXpDrops = builder.comment("Minimum XP a crop block will drop when broken.").define("Minimum Crop XP", 0);
            maxCropXpDrops = builder.comment("Maximum XP a crop block will drop when broken.").define("Maximum Crop XP", 1);
            builder.pop();

            builder.push("dispensers");
            doDispenserCauldrons = builder.comment("If dispensers should be able to dispense into cauldrons").define("Dispenser Cauldrons", true);
            builder.pop();

            builder.push("enchantments");
            damageReductions = builder.comment("If enchantments other than protection should be buffed. Experimental!").define("Erm what the sigma", false);
            builder.pop();


            builder.pop();


            builder.push("jaden nether expansion");
            builder.push("Bone Rods");
            shouldPlaceBonePilesWithNormalBones = builder
                    .comment("If bone rods should be placed by right clicking vanilla bones, instead of having a new bone rod item")
                    .comment("This will not change the loot table or recipe for bone rods. Modpack devs are expected to change those themselves")
                    .define("Bone ahh bone", false);
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

            builder.push("Ballistic");
            conqueringStar = builder.comment("If knives can receive the Ballistic enchantment, which gives them the effect of Dungeon's Delight cleavers to be thrown. REQUIRES DUNGEON's DELIGHT.").define("Cleaverfication Enchantment", false);
            builder.pop();

            builder.pop();

            builder.push("Mowzies Mobs");
            builder.push("Rakes");
            hoesRakeSand = builder.comment("If hoes should also be able to rake sand. Disabled by default option for my modpack")
                            .define("Hoes Rake Sand", false);
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

            builder.push("Cave Maps");
            pathfinderQuillMakesCaveMaps = builder.comment("If pathfinder quills should make a based sigma cave map instead of a cringe beta vanilla map")
                            .define("I'm sorry I said all that", true);
            builder.pop();

            builder.push("Biome Treats");
            actuallyGoodBiomeTreats = builder.comment("If biome treats should work regardless of hunger")
                    .define("Okay Treats", true);
            builder.pop();

            builder.push("Sack of Sating");
            sackOfSatingNoRestoreSat = builder.comment("If sacks of sating no longer restore saturation")
                    .define("Mid sack of sating", false);
            builder.pop();

            builder.push("Tremorzilla");
            removeTremorzillaGrief = builder.comment("If tremorzillas should no longer passively break all blocks in its hitbox. Blocks are still broken when the tremorzilla attacks, or with its beam attack.")
                        .define("Marginally Less Griefy Tremorzillas", false);
            builder.pop();

            builder.pop();


            builder.push("Dungeon's Delight");

            builder.push("Brewing and Chewing Wardenzola");
            wheelifiedWardenzola = builder.comment("If Wardenzola Dungeons Delight Should be Brewing and Chewingified; this means that they are placeable and have a keg recipe, as well as wedges.")
                    .define("Wardenzola Wheel", false);
            builder.pop();

            builder.pop();


            builder.push("John Species");

            builder.push("Crankbow");
            stackSizeForSpeciesCrankbow = builder.comment("The stack size crankbows should have. The formula for the capacity of crankbows is stack size * 2 + capacity enchantment level * stack size.")
                    .comment("This is for if you use a mod like Bigger Stacks to change the stack size")
                    .define("Crankbow stack size ", 64);
            builder.pop();

            builder.push("hopelight");
            frogMistLikeHopeLights = builder.comment("If species hopelights should act like JNE frogmists (only interactable when holding another hopelight or a pickaxe)").define("Frogmist Hopelights", true);
            builder.pop();

            builder.push("Combustion Effect");
            shouldCombustionGriefBlocks = builder.comment("If explosions caused by mobs that die with the Species Combustion effect should not any blocks")
                    .define("Less combusty combustion", false);
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
