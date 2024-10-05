package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CompatTags {
    public static final TagKey<Block> COMMON_ORES = blockTag("common_ores");
    public static final TagKey<Block> RARE_ORES = blockTag("rare_ores");
    public static final TagKey<Block> NO_XP_CROPS = blockTag("no_xp_crops");
    public static final TagKey<Block> CHANNELS_LIGHTNING = blockTag("channels_lightning");

    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagUtil.itemTag(modId, path);
    }


    private static TagKey<Item> itemTag(String path) {
        return externalItemTag(DoltModHow.MOD_ID, path);
    }

    private static TagKey<Block> blockTag(String path) {
        return TagUtil.blockTag(DoltModHow.MOD_ID, path);
    }
}
