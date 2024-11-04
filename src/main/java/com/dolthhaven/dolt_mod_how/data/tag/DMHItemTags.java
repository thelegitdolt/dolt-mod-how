package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public class DMHItemTags extends ItemTagsProvider {
    public DMHItemTags(GatherDataEvent event, BlockTagsProvider blockTags) {
        super(event.getGenerator().getPackOutput(),
              event.getLookupProvider(), blockTags.contentsGetter(), DoltModHow.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(DMHTags.ACID_BUCKETS)
                .add(DMHItems.GOLDEN_ACID_BUCKET.get())
                .addOptional(new ResourceLocation(Util.Constants.ALEXS_CAVES, "acid_bucket"));

        this.tag(DMHTags.PURPLE_SODA_BUCKETS)
                .add(DMHItems.GOLDEN_PURPLE_SODA_BUCKET.get())
                .addOptional(new ResourceLocation(Util.Constants.ALEXS_CAVES, "purple_soda_bucket"));

    }
}
