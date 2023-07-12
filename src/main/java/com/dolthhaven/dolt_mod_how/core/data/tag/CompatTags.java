package com.dolthhaven.dolt_mod_how.core.data.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CompatTags {
    public static final TagKey<Item> EXPERIENCE_BOOST_ITEMS = externalItemTag("caverns_and_chasms", "experience_boost_items");


    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(modId, path));
    }
}
