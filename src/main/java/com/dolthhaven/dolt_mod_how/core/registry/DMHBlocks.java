package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.block.CompatLanternBlock;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> MUD_LANTERN = HELPER.createBlock("mud_lantern", () ->
            new CompatLanternBlock(DoltModHowBlockProps.MUD_LANTERN), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> STURDY_DEEPSLATE = HELPER.createBlock("sturdy_deepslate", () ->
            new Block(DoltModHowBlockProps.STURDY_DEEPSLATE), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static class DoltModHowBlockProps {
        public static final BlockBehaviour.Properties MUD_LANTERN = BlockBehaviour.Properties.of(Material.ICE).requiresCorrectToolForDrops().strength(1.0f, 3.0f).sound(SoundType.MUD_BRICKS).lightLevel(state -> 7).noOcclusion();
        public static final Material STURDY_DEP_MAT = new Material(MaterialColor.DEEPSLATE, false, true, true, true, false, false, PushReaction.BLOCK);
        public static final BlockBehaviour.Properties STURDY_DEEPSLATE = BlockBehaviour.Properties.of(STURDY_DEP_MAT, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(6.0f, 12.0f).sound(SoundType.DEEPSLATE);



    }

}
