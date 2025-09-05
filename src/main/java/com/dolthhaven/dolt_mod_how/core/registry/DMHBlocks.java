package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.block.ChiseledPewenBookshelfBlock;
import com.dolthhaven.dolt_mod_how.common.block.GlowshroomColonyBlock;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.*;
import com.teamabnormals.blueprint.common.block.BlueprintBeehiveBlock;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.common.block.LeafPileBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.Util;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.DMHBlockProps.ZINC_PLATED_BRICKS;
import static net.minecraft.world.level.material.MapColor.GLOW_LICHEN;
import static net.minecraft.world.level.material.MapColor.TERRACOTTA_YELLOW;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> STURDY_DEEPSLATE = HELPER.createBlock("sturdy_deepslate", () ->
            new Block(DMHBlockProps.STURDY_DEEPSLATE));
    public static final RegistryObject<Block> ALPHACENE_PATH = HELPER.createBlock("alphacene_path", () ->
            new DirtPathBlock(DMHBlockProps.ALPHACENE_PATH));

    public static final RegistryObject<Block> GLOWSHROOM_COLONY = HELPER.createBlockNoItem("glowshroom_colony", () ->
            new GlowshroomColonyBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)
                    .randomTicks()
                    .lightLevel(s -> 10)));
    public static final RegistryObject<Block> BOP_GLOW_SHROOM_COLONY = HELPER.createBlockNoItem("bop_glowshroom_colony", () ->
            new GlowshroomColonyBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).mapColor(GLOW_LICHEN)));
    public static final RegistryObject<Block> TOADSTOOL_COLONY = HELPER.createBlockNoItem("toadstool_colony", () ->
            new GlowshroomColonyBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM)));


    public static final RegistryObject<Block> PINE_NUTS_CRATE = HELPER.createBlock("pine_nuts_crate", () ->
            new BlueprintDirectionalBlock(DMHBlockProps.PINE_NUT_CRATE));

    public static final RegistryObject<Block> PEWEN_BEEHIVE = HELPER.createFuelBlock("pewen_beehive", () ->
            new BlueprintBeehiveBlock(DMHBlockProps.PEWEN.beehive()), 300);
    public static final RegistryObject<BlueprintChestBlock> PEWEN_CHEST = HELPER.createChestBlock("pewen", DMHBlockProps.PEWEN.chest());
    public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_PEWEN_CHEST = HELPER.createTrappedChestBlockNamed("pewen", DMHBlockProps.PEWEN.chest());
    public static final RegistryObject<Block> PEWEN_LADDER = HELPER.createFuelBlock("pewen_ladder", () ->
            new LadderBlock(DMHBlockProps.PEWEN.ladder()), 300);
    public static final RegistryObject<Block> PEWEN_BOARDS = HELPER.createFuelBlock("pewen_boards", () ->
            new RotatedPillarBlock(DMHBlockProps.PEWEN.planks()), 300);
    public static final RegistryObject<Block> PEWEN_BOOKSHELF = HELPER.createFuelBlock("pewen_bookshelf", () ->
            new Block(DMHBlockProps.PEWEN.bookshelf()), 300);
    public static final RegistryObject<Block> CHISELED_PEWEN_BOOKSHELF = HELPER.createFuelBlock("chiseled_pewen_bookshelf", () ->
            new ChiseledPewenBookshelfBlock(DMHBlockProps.PEWEN.chiseledBookshelf()), 300);
    public static final RegistryObject<Block> PEWEN_CABINET = HELPER.createFuelBlock("pewen_cabinet", () ->
            new CabinetBlock(BlockBehaviour.Properties.copy(ModBlocks.OAK_CABINET.get())), 300);

    public static final RegistryObject<Block> THORNWOOD_BEEHIVE = HELPER.createFuelBlock("thornwood_beehive", () ->
            new BlueprintBeehiveBlock(DMHBlockProps.THORNWOOD.beehive()), 300);
    public static final RegistryObject<BlueprintChestBlock> THORNWOOD_CHEST = HELPER.createChestBlock("thornwood", DMHBlockProps.PEWEN.chest());
    public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_THORNWOOD_CHEST = HELPER.createTrappedChestBlockNamed("thornwood", DMHBlockProps.THORNWOOD.chest());
    public static final RegistryObject<Block> THORNWOOD_LADDER = HELPER.createFuelBlock("thornwood_ladder", () ->
            new LadderBlock(DMHBlockProps.THORNWOOD.ladder()), 300);
    public static final RegistryObject<Block> THORNWOOD_BOARDS = HELPER.createFuelBlock("thornwood_boards", () ->
            new RotatedPillarBlock(DMHBlockProps.THORNWOOD.planks()), 300);
    public static final RegistryObject<Block> THORNWOOD_BOOKSHELF = HELPER.createFuelBlock("thornwood_bookshelf", () ->
            new Block(DMHBlockProps.THORNWOOD.bookshelf()), 300);
    public static final RegistryObject<Block> CHISELED_THORNWOOD_BOOKSHELF = HELPER.createFuelBlock("chiseled_thornwood_bookshelf", () ->
            new ChiseledPewenBookshelfBlock(DMHBlockProps.THORNWOOD.chiseledBookshelf()), 300);
    public static final RegistryObject<Block> THORNWOOD_CABINET = HELPER.createFuelBlock("thornwood_cabinet", () ->
            new CabinetBlock(BlockBehaviour.Properties.copy(ModBlocks.OAK_CABINET.get())), 300);

    public static final RegistryObject<Block> ANCIENT_LEAF_PILE = HELPER.createBlock("ancient_leaf_pile", () ->
            new LeafPileBlock(DMHBlockProps.PEWEN.leafPile()));


//    public static final RegistryObject<Block> LANTERNFISH_BARREL = HELPER.createBlock("lanternfish_barrel", ModList.get().isLoaded("fish_in_planks") ?
//            DMHFishBarrels.LANTERNFISH_BLOCK : () -> new Block(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)),
//            AbstractSubRegistryHelper.areModsLoaded("fish_in_planks") ? CreativeModeTab.TAB_BUILDING_BLOCKS : null);


    public static final RegistryObject<Block> POTTED_ARID_SPROUTS = HELPER.createBlockNoItem("potted_arid_sprouts",
            getPot(DMHUtils.Constants.ATMOSPHERIC, DMHAtmosphericCompat.POTTED_ARID_SPROUTS));
    public static final RegistryObject<Block> POTTED_BEACHGRASS = HELPER.createBlockNoItem("potted_beachgrass",
            getPot(DMHUtils.Constants.UPGRADE_AQUATIC, DMHUACompat.POTTED_BEACHGRASS));
    public static final RegistryObject<Block> POTTED_TALL_BEACHGRASS = HELPER.createBlockNoItem("potted_tall_beachgrass",
            getPot(DMHUtils.Constants.UPGRADE_AQUATIC, DMHUACompat.POTTED_TALL_BEACHGRASS));
    public static final RegistryObject<Block> POTTED_MYCELIUM_SPROUTS = HELPER.createBlockNoItem("potted_mycelium_sprouts",
            getPot(DMHUtils.Constants.ENVIRONMENTAL, DMHEnvironmentalCompat.POTTED_MYCELIUM_SPROUTS));

    public static final RegistryObject<Block> POTTED_STRAWBERRIES = HELPER.createBlockNoItem("potted_strawberries",
            getPot(DMHUtils.Constants.NEAPOLITAN, DMHNeapolitanCompat.POTTED_STRAWBERRIES));
    public static final RegistryObject<Block> POTTED_WHITE_STRAWBERRIES = HELPER.createBlockNoItem("potted_white_strawberries",
            getPot(DMHUtils.Constants.NEAPOLITAN, DMHNeapolitanCompat.POTTED_WHITE_STRAWBERRIES));

    public static final RegistryObject<Block> POTTED_ONION = HELPER.createBlockNoItem("potted_onion", () ->
            new FlowerPotBlock(ModBlocks.ONION_CROP.get(), PropertyUtil.flowerPot()));
    public static final RegistryObject<Block> POTTED_TOMATOES = HELPER.createBlockNoItem("potted_tomatoes", () ->
            new FlowerPotBlock(ModBlocks.BUDDING_TOMATO_CROP.get(), PropertyUtil.flowerPot()));
    public static final RegistryObject<Block> POTTED_CABBAGE = HELPER.createBlockNoItem("potted_cabbage", () ->
            new FlowerPotBlock(ModBlocks.CABBAGE_CROP.get(), PropertyUtil.flowerPot()));

    public static final RegistryObject<Block> ARID_RAKED_SAND = HELPER.createBlock("arid_raked_sand", ModList.get().isLoaded(DMHUtils.Constants.MOWZIES_MOBS) ?
            DMHMowziesMobsCompat.RAKED_ARID_SAND : () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> RED_ARID_RAKED_SAND = HELPER.createBlock("red_arid_raked_sand", ModList.get().isLoaded(DMHUtils.Constants.MOWZIES_MOBS) ?
            DMHMowziesMobsCompat.RAKED_RED_ARID_SAND : () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_SAND)));
    public static final RegistryObject<Block> ASHEN_RAKED_SAND = HELPER.createBlock("ashen_raked_sand", ModList.get().isLoaded(DMHUtils.Constants.MOWZIES_MOBS) ?
            DMHMowziesMobsCompat.RAKED_ASHEN_SAND : () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_SAND)));

    public static final RegistryObject<Block> ZINC_BRICKS = HELPER.createBlock("zinc_bricks", () -> new Block(ZINC_PLATED_BRICKS));
    public static final RegistryObject<Block> ZINC_BRICK_STAIRS = HELPER.createBlock("zinc_brick_stairs", () ->
            new StairBlock(() -> ZINC_BRICKS.get().defaultBlockState(), ZINC_PLATED_BRICKS));
    public static final RegistryObject<Block> ZINC_BRICK_SLAB = HELPER.createBlock("zinc_brick_slab", () ->
            new SlabBlock(ZINC_PLATED_BRICKS));
    public static final RegistryObject<Block> ZINC_BRICK_WALL = HELPER.createBlock("zinc_brick_wall", () ->
            new WallBlock(ZINC_PLATED_BRICKS));
    public static final RegistryObject<Block> CHISELED_ZINC_BRICKS = HELPER.createBlock("chiseled_zinc_bricks", () -> new Block(ZINC_PLATED_BRICKS));

    public static final RegistryObject<Block> WARDENZOLA = HELPER.createBlock("wardenzola", ModList.get().isLoaded(DMHUtils.Constants.BREWING_AND_CHEWING) ?
            DMHBCCompat.WARDENZOLA : () -> new Block(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    private static Supplier<? extends Block> getPot(String id, Supplier<? extends Block> block) {
        return ModList.get().isLoaded(id) ? block : () -> new Block(PropertyUtil.flowerPot());
    }



    public static class DMHBlockProps {
        public static final BlockBehaviour.Properties STURDY_DEEPSLATE = BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(4.5F, 9.0F)
                .pushReaction(PushReaction.IGNORE);
        public static final BlockBehaviour.Properties ALPHACENE_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN)
                .strength(0.65F).sound(BlockSubRegistryHelper.areModsLoaded(DMHUtils.Constants.SPECIES) ? DMHSpeciesCompat.opSound() : SoundType.GRAVEL).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);
        public static final BlockBehaviour.Properties PINE_NUT_CRATE = BlockBehaviour.Properties.of().mapColor(TERRACOTTA_YELLOW)
                .strength(1.5f).sound(SoundType.WOOD).ignitedByLava();

        public static final BlockBehaviour.Properties ZINC_PLATED_BRICKS = BlockBehaviour.Properties.of().mapColor(GLOW_LICHEN)
                .strength(3.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM);

        public static final PropertyUtil.WoodSetProperties PEWEN = PropertyUtil
                .WoodSetProperties.builder(MapColor.WOOD).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS).build();
        public static final PropertyUtil.WoodSetProperties THORNWOOD = PropertyUtil
                .WoodSetProperties.builder(MapColor.TERRACOTTA_BLACK).instrument(NoteBlockInstrument.BASS).build();
    }

}
