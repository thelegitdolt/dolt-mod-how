package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
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


public class DMHBlockTags extends BlockTagsProvider {
    public DMHBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper efh) {
        super(output, lookupProvider, DoltModHow.MOD_ID, efh);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.UNAFFECTED_BY_RICH_SOIL)
                .add(GLOWSHROOM_COLONY.get())
                .addOptional(new ResourceLocation(DMHUtils.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony"))
                .addOptional(new ResourceLocation(DMHUtils.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony"));

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(STURDY_DEEPSLATE.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(PINE_NUTS_CRATE.get(), PEWEN_BEEHIVE.get(), PEWEN_BOOKSHELF.get(), PEWEN_CHEST.get(), PEWEN_LADDER.get(), TRAPPED_PEWEN_CHEST.get(), PEWEN_BEEHIVE.get(), PEWEN_BOARDS.get(), CHISELED_PEWEN_BOOKSHELF.get(), PEWEN_CABINET.get(),
                THORNWOOD_BEEHIVE.get(), THORNWOOD_LADDER.get(), THORNWOOD_BOOKSHELF.get(), THORNWOOD_BOARDS.get(), CHISELED_THORNWOOD_BOOKSHELF.get(), THORNWOOD_CHEST.get(), TRAPPED_THORNWOOD_CHEST.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ALPHACENE_PATH.get(), ARID_RAKED_SAND.get(), ASHEN_RAKED_SAND.get(), RED_ARID_RAKED_SAND.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ANCIENT_LEAF_PILE.get());


        this.tag(DMHTags.NO_XP_CROPS)
                .addOptional(new ResourceLocation(DMHUtils.Constants.FARMERS_DELIGHT, "tomatoes"));

        this.tag(DMHTags.COCOA_BEANS_ADDITIONALLY_PLANTABLE_ON);

        this.tag(BlockTags.FLOWER_POTS).add(POTTED_BEACHGRASS.get(), POTTED_MYCELIUM_SPROUTS.get(),
                POTTED_TALL_BEACHGRASS.get(), POTTED_ARID_SPROUTS.get(), POTTED_TOMATOES.get(), POTTED_ONION.get(), POTTED_CABBAGE.get(),
                POTTED_STRAWBERRIES.get(), POTTED_WHITE_STRAWBERRIES.get());

        this.tag(BlueprintBlockTags.WOODEN_CHESTS).add(PEWEN_CHEST.get(), THORNWOOD_CHEST.get());
        this.tag(BlueprintBlockTags.WOODEN_TRAPPED_CHESTS).add(TRAPPED_PEWEN_CHEST.get(), TRAPPED_PEWEN_CHEST.get());
        this.tag(BlueprintBlockTags.WOODEN_LADDERS).add(PEWEN_LADDER.get(), THORNWOOD_LADDER.get());
        this.tag(BlueprintBlockTags.WOODEN_BEEHIVES).add(PEWEN_BEEHIVE.get(), THORNWOOD_BEEHIVE.get());
        this.tag(BlueprintBlockTags.WOODEN_BOOKSHELVES).add(PEWEN_BOOKSHELF.get(), THORNWOOD_BOOKSHELF.get());
        this.tag(BlueprintBlockTags.WOODEN_BOARDS).add(PEWEN_BOARDS.get(), THORNWOOD_BOARDS.get());
        this.tag(BlueprintBlockTags.WOODEN_CHISELED_BOOKSHELVES).add(CHISELED_PEWEN_BOOKSHELF.get(), CHISELED_THORNWOOD_BOOKSHELF.get());
        this.tag(BlueprintBlockTags.LEAF_PILES).add(ANCIENT_LEAF_PILE.get());

        this.tag(DMHTags.RARE_ORES).addTags(BlockTags.GOLD_ORES)
                .addOptionalTag(new ResourceLocation("forge", "ores/silver"))
                .addOptionalTag(new ResourceLocation("forge", "ores/tin"));
        this.tag(DMHTags.COMMON_ORES).add(AMETHYST_CLUSTER).addTags(BlockTags.IRON_ORES, BlockTags.COPPER_ORES)
                .addOptional(new ResourceLocation("pigsteel", "porkslag"))
                .addOptionalTag(new ResourceLocation("forge", "ores/jade"))
                .addOptionalTag(new ResourceLocation("forge", "ores/lead"))
                .addOptionalTag(new ResourceLocation("forge", "ores/zinc"));

        this.tag(DMHTags.CHANNELS_LIGHTNING).add(JUKEBOX);
        this.tag(DMHTags.NO_XP_REWARD_ON_PLACE).add(FROSTED_ICE);
    }

}
