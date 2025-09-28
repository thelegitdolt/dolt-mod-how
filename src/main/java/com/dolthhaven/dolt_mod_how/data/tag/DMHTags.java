package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.Util;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DMHTags {
    public static final TagKey<Block> COMMON_ORES = blockTag("common_ores");
    public static final TagKey<Block> RARE_ORES = blockTag("rare_ores");
    public static final TagKey<Block> NO_XP_CROPS = blockTag("no_xp_crops");
    public static final TagKey<Block> CHANNELS_LIGHTNING = blockTag("channels_lightning");
    public static final TagKey<Block> COCOA_BEANS_ADDITIONALLY_PLANTABLE_ON = blockTag("cocoa_beans_additionally_plantable_on");
    public static final TagKey<Block> NO_XP_REWARD_ON_PLACE = blockTag("no_xp_reward_on_place");
    public static final TagKey<Block> PIPE_BLOCKS = externalBlockTag("quark", "pipes");
    public static final TagKey<Block> MINEABLE_SHEARS = externalBlockTag("minecraft", "mineable/shear");
    public static final TagKey<Block> ENCASED_PIPES_BLOCKS = externalBlockTag("quark", "encased_pipes");


    public static final TagKey<Item> ACID_BUCKETS = externalItemTag("forge", "buckets/acid");
    public static final TagKey<Item> PURPLE_SODA_BUCKETS = externalItemTag("forge", "buckets/purple_soda");
    public static final TagKey<Item> MOLTEN_LEAD_BUCKETS = externalItemTag("forge", "buckets/molten_lead");
    public static final TagKey<Item> LEATHER = itemTag("leather");
    public static final TagKey<Item> PIPES = externalItemTag("quark", "pipes");
    public static final TagKey<Item> ENCASED_PIPES = externalItemTag("quark", "encased_pipes");

    public static final TagKey<Item> SHEARS = externalItemTag("forge", "shears");
    public static final TagKey<Item> SCULK_CHEESE = externalItemTag(DMHUtils.Constants.DUNGEONS_DELIGHT, "sculk_cheese");


    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagUtil.itemTag(modId, path);
    }

    private static TagKey<Block> externalBlockTag(String modId, String path) {
        return TagUtil.blockTag(modId, path);
    }


    private static TagKey<Item> itemTag(String path) {
        return externalItemTag(DoltModHow.MOD_ID, path);
    }


    private static TagKey<Block> blockTag(String path) {
        return TagUtil.blockTag(DoltModHow.MOD_ID, path);
    }
}
