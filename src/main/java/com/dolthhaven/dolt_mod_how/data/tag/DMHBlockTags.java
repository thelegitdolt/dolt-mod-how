package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static net.minecraft.world.level.block.Blocks.*;


public class DMHBlockTags extends BlockTagsProvider {
    public DMHBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper efh) {
        super(output, lookupProvider, DoltModHow.MOD_ID, efh);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.UNAFFECTED_BY_RICH_SOIL)
                .add(GLOWSHROOM_COLONY.get())
                .addOptional(new ResourceLocation(DMHUtils.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony"))
                .addOptional(new ResourceLocation(DMHUtils.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony"));

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(STURDY_DEEPSLATE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(PINE_NUTS_CRATE.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ALPHACENE_PATH.get());

        this.tag(DMHTags.NO_XP_CROPS)
                .addOptional(new ResourceLocation(DMHUtils.Constants.FARMERS_DELIGHT, "tomatoes"));

        this.tag(DMHTags.COCOA_BEANS_ADDITIONALLY_PLANTABLE_ON);

        this.tag(BlockTags.FLOWER_POTS).add(POTTED_BEACHGRASS.get(), POTTED_MYCELIUM_SPROUTS.get(),
                POTTED_TALL_BEACHGRASS.get(), POTTED_ARID_SPROUTS.get());
        this.tag(DMHTags.RARE_ORES).addTags(BlockTags.GOLD_ORES)
                .addOptionalTag(new ResourceLocation("forge", "ores/silver"))
                .addOptionalTag(new ResourceLocation("forge", "ores/tin"));
        this.tag(DMHTags.COMMON_ORES).add(AMETHYST_CLUSTER).addTags(BlockTags.IRON_ORES, BlockTags.COPPER_ORES)
                .addOptionalTag(new ResourceLocation("forge", "ores/jade"))
                .addOptionalTag(new ResourceLocation("forge", "ores/lead"))
                .addOptionalTag(new ResourceLocation("forge", "ores/zinc"));

        this.tag(DMHTags.CHANNELS_LIGHTNING).add(JUKEBOX);
        this.tag(DMHTags.NO_XP_REWARD_ON_PLACE).add(FROSTED_ICE);
    }

}
