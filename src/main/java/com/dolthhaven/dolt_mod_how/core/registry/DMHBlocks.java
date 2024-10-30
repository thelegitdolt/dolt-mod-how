package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.block.GlowshroomColonyBlock;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.compat.DoltModHowPots;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> STURDY_DEEPSLATE = HELPER.createBlock("sturdy_deepslate", () ->
            new Block(DoltModHowBlockProps.STURDY_DEEPSLATE));

    public static final RegistryObject<Block> GLOWSHROOM_COLONY = HELPER.createBlockNoItem("glowshroom_colony", () ->
            new GlowshroomColonyBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)
                    .randomTicks()
                    .lightLevel(s -> 10)));


//    public static final RegistryObject<Block> LANTERNFISH_BARREL = HELPER.createBlock("lanternfish_barrel", ModList.get().isLoaded("fish_in_planks") ?
//            DMHFishBarrels.LANTERNFISH_BLOCK : () -> new Block(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)),
//            AbstractSubRegistryHelper.areModsLoaded("fish_in_planks") ? CreativeModeTab.TAB_BUILDING_BLOCKS : null);


    public static final RegistryObject<Block> POTTED_ARID_SPROUTS = HELPER.createBlockNoItem("potted_arid_sprouts",
            getPot(Util.Constants.ATMOSPHERIC, DoltModHowPots.POTTED_ARID_SPROUTS));
    public static final RegistryObject<Block> POTTED_BEACHGRASS = HELPER.createBlockNoItem("potted_beachgrass",
            getPot(Util.Constants.UPGRADE_AQUATIC, DoltModHowPots.POTTED_BEACHGRASS));
    public static final RegistryObject<Block> POTTED_TALL_BEACHGRASS = HELPER.createBlockNoItem("potted_tall_beachgrass",
            getPot(Util.Constants.UPGRADE_AQUATIC, DoltModHowPots.POTTED_TALL_BEACHGRASS));
    public static final RegistryObject<Block> POTTED_MYCELIUM_SPROUTS = HELPER.createBlockNoItem("potted_mycelium_sprouts",
            getPot(Util.Constants.ENVIRONMENTAL, DoltModHowPots.POTTED_MYCELIUM_SPROUTS));


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

    }

}
