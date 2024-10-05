package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static net.minecraft.world.level.block.Blocks.*;


public class DoltModHowBlockTags extends BlockTagsProvider {
    public DoltModHowBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper efh) {
        super(output, lookupProvider, DoltModHow.MOD_ID, efh);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.UNAFFECTED_BY_RICH_SOIL)
                .add(GLOWSHROOM_COLONY.get())
                .addOptional(new ResourceLocation(Util.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony"))
                .addOptional(new ResourceLocation(Util.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony"));

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(STURDY_DEEPSLATE.get());

        this.tag(CompatTags.NO_XP_CROPS)
                .addOptional(new ResourceLocation("farmersdelight", "tomatoes"));

        this.tag(CompatTags.RARE_ORES).add(GOLD_ORE, DEEPSLATE_GOLD_ORE).addOptional(
                new ResourceLocation("caverns_and_chasms", "silver_ore")
        ).addOptional(new ResourceLocation("caverns_and_chasms", "deepslate_silver_ore"));

        this.tag(CompatTags.COMMON_ORES).add(COPPER_ORE, DEEPSLATE_COPPER_ORE, IRON_ORE, DEEPSLATE_IRON_ORE)
                .addOptional(new ResourceLocation("sullysmod", "jade_ore"))
                .addOptional(new ResourceLocation("sullysmod", "deepslate_jade_ore"))
                .addOptional(new ResourceLocation("oreganized", "lead_ore"))
                .addOptional(new ResourceLocation("oreganized", "deepslate_lead_ore"));

        this.tag(CompatTags.CHANNELS_LIGHTNING).add(JUKEBOX);
    }

}
