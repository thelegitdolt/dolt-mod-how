package com.dolthhaven.dolt_mod_how.core.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import umpaz.nethersdelight.common.tag.NDTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static net.minecraft.world.level.block.Blocks.*;
import static umpaz.nethersdelight.common.registry.NDBlocks.*;
import static vectorwing.farmersdelight.common.registry.ModBlocks.*;


public class DoltModHowBlockTags extends BlockTagsProvider {
    public DoltModHowBlockTags(DataGenerator gen, @Nullable ExistingFileHelper efh) {
        super(gen, DoltModHow.MOD_ID, efh);
    }

    @Override
    public void addTags() {
        this.tag(NDTags.FUNGUS_COLONY_GROWABLE_ON).add(RICH_SOIL.get());
        this.tag(ModTags.UNAFFECTED_BY_RICH_SOIL).add(CRIMSON_FUNGUS_COLONY.get(), WARPED_FUNGUS_COLONY.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(STURDY_DEEPSLATE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(LANTERNFISH_BARREL.get());

        this.tag(CompatTags.RARE_ORES).add(GOLD_ORE, DEEPSLATE_GOLD_ORE).addOptional(
                new ResourceLocation("caverns_and_chasms", "silver_ore")
        ).addOptional(new ResourceLocation("caverns_and_chasms", "deepslate_silver_ore"));

        this.tag(CompatTags.COMMON_ORES).add(COPPER_ORE, DEEPSLATE_COPPER_ORE, IRON_ORE, DEEPSLATE_IRON_ORE)
                .addOptional(new ResourceLocation("sullysmod", "jade_ore"))
                .addOptional(new ResourceLocation("sullysmod", "deepslate_jade_ore"));

        this.tag(CompatTags.CHANNELS_LIGHTNING).add(JUKEBOX);
    }
}
