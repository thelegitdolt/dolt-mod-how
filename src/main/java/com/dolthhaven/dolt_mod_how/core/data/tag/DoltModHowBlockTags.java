package com.dolthhaven.dolt_mod_how.core.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import umpaz.nethersdelight.common.tag.NDTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
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

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MUD_LANTERN.get(), STURDY_DEEPSLATE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(LANTERNFISH_BARREL.get());
    }
}
