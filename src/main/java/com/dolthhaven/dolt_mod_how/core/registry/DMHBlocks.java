package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.integration.DMHFishBarrels;
import com.teamabnormals.blueprint.core.util.registry.AbstractSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> STURDY_DEEPSLATE = HELPER.createBlock("sturdy_deepslate", () ->
            new Block(DoltModHowBlockProps.STURDY_DEEPSLATE), CreativeModeTab.TAB_BUILDING_BLOCKS);

//    public static final RegistryObject<Block> LANTERNFISH_BARREL = HELPER.createBlock("lanternfish_barrel", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)),
//             CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> LANTERNFISH_BARREL = HELPER.createBlock("lanternfish_barrel", ModList.get().isLoaded("fish_in_planks") ?
            DMHFishBarrels.LANTERNFISH_BLOCK : () -> new Block(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)),
            AbstractSubRegistryHelper.areModsLoaded("fish_in_planks") ? CreativeModeTab.TAB_BUILDING_BLOCKS : null);


    public static class DoltModHowBlockProps {
        public static final Material STURDY_DEP_MAT = new Material(MaterialColor.DEEPSLATE, false, true, true, true, false, false, PushReaction.BLOCK);
        public static final BlockBehaviour.Properties STURDY_DEEPSLATE = BlockBehaviour.Properties.of(STURDY_DEP_MAT, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(6.0f, 12.0f).sound(SoundType.DEEPSLATE);

    }

}
