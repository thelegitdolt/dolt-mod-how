package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.*;

public class DMHItemTags extends ItemTagsProvider {
    public DMHItemTags(GatherDataEvent event, BlockTagsProvider blockTags) {
        super(event.getGenerator().getPackOutput(),
              event.getLookupProvider(), blockTags.contentsGetter(), DoltModHow.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(DMHTags.ACID_BUCKETS)
                .add(GOLDEN_ACID_BUCKET.get())
                .addOptional(new ResourceLocation(Util.Constants.ALEXS_CAVES, "acid_bucket"));

        this.tag(DMHTags.PURPLE_SODA_BUCKETS)
                .add(GOLDEN_PURPLE_SODA_BUCKET.get())
                .addOptional(new ResourceLocation(Util.Constants.ALEXS_CAVES, "purple_soda_bucket"));

        this.tag(DMHTags.MOLTEN_LEAD_BUCKETS)
                .add(GOLDEN_MOLTEN_LEAD_BUCKET.get())
                .addOptional(new ResourceLocation(Util.Constants.OREGANIZED, "molten_lead_bucket"));

        this.tag(DMHTags.LEATHER).add(Items.LEATHER, LEATHER_SCRAPS.get());

        this.tag(ItemTags.PIGLIN_LOVED).add(GOLDEN_ACID_BUCKET.get(), GOLDEN_PURPLE_SODA_BUCKET.get(), GOLDEN_MOLTEN_LEAD_BUCKET.get());

        this.tag(BlueprintItemTags.BUCKETS)
                .addTags(DMHTags.ACID_BUCKETS, DMHTags.PURPLE_SODA_BUCKETS, DMHTags.MOLTEN_LEAD_BUCKETS);
    }
}
