package com.dolthhaven.dolt_mod_how.core.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CompatTags {
    public static final TagKey<Item> EXPERIENCE_BOOST_ITEMS = externalItemTag("caverns_and_chasms", "experience_boost_items");



    public static final TagKey<Block> COMMON_ORES = blockTag("common_ores");
    public static final TagKey<Block> RARE_ORES = blockTag("rare_ores");

    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(modId, path));
    }


    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(DoltModHow.MOD_ID, path));
    }

    private static TagKey<Block> blockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(DoltModHow.MOD_ID, path));
    }
}
