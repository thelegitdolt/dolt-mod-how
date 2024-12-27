package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.block.GlowshroomColonyBlock;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.compat.*;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.minecraft.world.level.material.MapColor.TERRACOTTA_YELLOW;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> STURDY_DEEPSLATE = HELPER.createBlock("sturdy_deepslate", () ->
            new Block(DoltModHowBlockProps.STURDY_DEEPSLATE));
    public static final RegistryObject<Block> ALPHACENE_PATH = HELPER.createBlock("alphacene_path", () ->
            new DirtPathBlock(DoltModHowBlockProps.ALPHACENE_PATH));

    public static final RegistryObject<Block> GLOWSHROOM_COLONY = HELPER.createBlockNoItem("glowshroom_colony",
            () -> new GlowshroomColonyBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).randomTicks().lightLevel(s -> 10)));
    public static final RegistryObject<Block> PINE_NUTS_CRATE = HELPER.createBlock("pine_nuts_crate", () ->
            new BlueprintDirectionalBlock(DoltModHowBlockProps.PINE_NUT_CRATE));


//    public static final RegistryObject<Block> LANTERNFISH_BARREL = HELPER.createBlock("lanternfish_barrel", ModList.get().isLoaded("fish_in_planks") ?
//            DMHFishBarrels.LANTERNFISH_BLOCK : () -> new Block(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)),
//            AbstractSubRegistryHelper.areModsLoaded("fish_in_planks") ? CreativeModeTab.TAB_BUILDING_BLOCKS : null);


    public static final RegistryObject<Block> POTTED_ARID_SPROUTS = HELPER.createBlockNoItem("potted_arid_sprouts",
            getPot(Util.Constants.ATMOSPHERIC, DMHAtmosphericCompat.POTTED_ARID_SPROUTS));
    public static final RegistryObject<Block> POTTED_BEACHGRASS = HELPER.createBlockNoItem("potted_beachgrass",
            getPot(Util.Constants.UPGRADE_AQUATIC, DMHUACompat.POTTED_BEACHGRASS));
    public static final RegistryObject<Block> POTTED_TALL_BEACHGRASS = HELPER.createBlockNoItem("potted_tall_beachgrass",
            getPot(Util.Constants.UPGRADE_AQUATIC, DMHUACompat.POTTED_TALL_BEACHGRASS));
    public static final RegistryObject<Block> POTTED_MYCELIUM_SPROUTS = HELPER.createBlockNoItem("potted_mycelium_sprouts",
            getPot(Util.Constants.ENVIRONMENTAL, DMHEnvironmentalCompat.POTTED_MYCELIUM_SPROUTS));


    private static Supplier<Block> getPot(String id, Supplier<Block> pot) {
        if (ModList.get().isLoaded(id)) {
            return pot;
        }
        else {
            return () -> new Block(PropertyUtil.flowerPot());
        }
    }

    public static class DoltModHowBlockProps {
        public static final BlockBehaviour.Properties STURDY_DEEPSLATE = BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(4.5F, 9.0F)
                .pushReaction(PushReaction.IGNORE);
        public static final BlockBehaviour.Properties ALPHACENE_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN)
                .strength(0.65F).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always)
                .sound(BlockSubRegistryHelper.areModsLoaded(Util.Constants.SPECIES) && DMHSpeciesCompat.ALPHACENE_GRASS != null
                        ? DMHSpeciesCompat.ALPHACENE_GRASS : SoundType.GRAVEL);
        public static final BlockBehaviour.Properties PINE_NUT_CRATE = BlockBehaviour.Properties.of().mapColor(TERRACOTTA_YELLOW)
                .strength(1.5f).sound(SoundType.WOOD).ignitedByLava();
    }
}
