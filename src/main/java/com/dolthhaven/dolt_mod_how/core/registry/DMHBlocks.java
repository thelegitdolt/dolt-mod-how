package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHSpeciesCompat;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> ALPHACENE_PATH = HELPER.createBlock("alphacene_path", () ->
            new DirtPathBlock(DMHBlockProps.ALPHACENE_PATH));

    public static final RegistryObject<Block> WAX_BLOCK = HELPER.createBlock("wax_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK)));




    public static class DMHBlockProps {
        public static final BlockBehaviour.Properties ALPHACENE_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN)
                .strength(0.65F).sound(BlockSubRegistryHelper.areModsLoaded(DMHUtils.Constants.SPECIES) ? DMHSpeciesCompat.opSound() : SoundType.GRAVEL).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);
    }

}
