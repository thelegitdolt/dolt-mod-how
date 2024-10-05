package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> STURDY_DEEPSLATE = HELPER.createBlock("sturdy_deepslate", () ->
            new Block(DoltModHowBlockProps.STURDY_DEEPSLATE));

//    public static final RegistryObject<Block> LANTERNFISH_BARREL = HELPER.createBlock("lanternfish_barrel", ModList.get().isLoaded("fish_in_planks") ?
//            DMHFishBarrels.LANTERNFISH_BLOCK : () -> new Block(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)),
//            AbstractSubRegistryHelper.areModsLoaded("fish_in_planks") ? CreativeModeTab.TAB_BUILDING_BLOCKS : null);


    public static class DoltModHowBlockProps {
        public static final BlockBehaviour.Properties STURDY_DEEPSLATE = BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(4.5F, 9.0F)
                .pushReaction(PushReaction.IGNORE);

    }

}
