package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.block.CompatLanternBlock;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> MUD_LANTERN = HELPER.createBlock("mud_lantern", () ->
            new CompatLanternBlock(DoltModHowBlockProps.MUD_LANTERN), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static class DoltModHowBlockProps {
        public static final BlockBehaviour.Properties MUD_LANTERN = BlockBehaviour.Properties.of(Material.ICE).requiresCorrectToolForDrops().strength(1.0f, 3.0f).sound(SoundType.MUD_BRICKS).lightLevel(state -> 10).noOcclusion();

    }

}
