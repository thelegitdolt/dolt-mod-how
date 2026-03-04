package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public class DMHEntityTags extends EntityTypeTagsProvider {
    public DMHEntityTags(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(),
                event.getLookupProvider(), DoltModHow.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(DMHTags.HOSTILE_MOUNTS)
                .addOptional(new ResourceLocation(DMHUtils.Constants.UPGRADE_AQUATIC, "thrasher"));
        tag(DMHTags.HUMANOID_ZOMBIES)
                .add(EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED)
                .addOptional(new ResourceLocation(DMHUtils.Constants.WINDSWEPT, "chilled"));
    }
}
