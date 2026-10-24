package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.*;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.violetmoon.quark.addons.oddities.block.pipe.EncasedPipeBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.DMHBlockProps.CC_LAMP;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHBlocks {
    public static final BlockSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> ALPHACENE_PATH = HELPER.createBlock("alphacene_path", () ->
            new DirtPathBlock(DMHBlockProps.ALPHACENE_PATH));

    public static final RegistryObject<Block> WAX_BLOCK = HELPER.createBlock("wax_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK)));


    private static Supplier<? extends Block> getPot(String id, Supplier<? extends Block> block) {
        return ModList.get().isLoaded(id) ? block : () -> new Block(PropertyUtil.flowerPot());
    }




    public static class DMHBlockProps {
        public static final BlockBehaviour.Properties ALPHACENE_PATH = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN)
                .strength(0.65F).sound(BlockSubRegistryHelper.areModsLoaded(DMHUtils.Constants.SPECIES) ? DMHSpeciesCompat.opSound() : SoundType.GRAVEL).isViewBlocking(PropertyUtil::always).isSuffocating(PropertyUtil::always);

        public static final BlockBehaviour.Properties CC_LAMP = BlockBehaviour.Properties.of().lightLevel((state) -> 15).strength(0.3F).sound(SoundType.GLASS).isValidSpawn((a, b, c, d) -> true).sound(SoundType.AMETHYST);
        public static final PropertyUtil.WoodSetProperties PEWEN = PropertyUtil
                .WoodSetProperties.builder(MapColor.WOOD).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS).build();
        public static final PropertyUtil.WoodSetProperties THORNWOOD = PropertyUtil
                .WoodSetProperties.builder(MapColor.TERRACOTTA_BLACK).instrument(NoteBlockInstrument.BASS).build();
    }

}
