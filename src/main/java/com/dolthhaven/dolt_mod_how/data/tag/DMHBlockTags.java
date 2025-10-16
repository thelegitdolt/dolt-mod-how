package com.dolthhaven.dolt_mod_how.data.tag;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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
                .add(TOADSTOOL_COLONY.get())
                .add(BOP_GLOW_SHROOM_COLONY.get())
                .addOptional(new ResourceLocation(DMHUtils.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony"))
                .addOptional(new ResourceLocation(DMHUtils.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony"));

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(STURDY_DEEPSLATE.get(), ZINC_BRICK_SLAB.get(), ZINC_BRICKS.get(), ZINC_BRICK_WALL.get(),
                CHISELED_ZINC_BRICKS.get(), ZINC_BRICK_STAIRS.get(),  ANCIENT_BRAZIER.get()).addTag(DMHTags.PIPE_BLOCKS);

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(PINE_NUTS_CRATE.get(), PEWEN_BEEHIVE.get(), PEWEN_BOOKSHELF.get(), PEWEN_CHEST.get(), PEWEN_LADDER.get(), TRAPPED_PEWEN_CHEST.get(), PEWEN_BEEHIVE.get(), PEWEN_BOARDS.get(), CHISELED_PEWEN_BOOKSHELF.get(), PEWEN_CABINET.get(),
                THORNWOOD_BEEHIVE.get(), THORNWOOD_LADDER.get(), THORNWOOD_BOOKSHELF.get(), THORNWOOD_BOARDS.get(), CHISELED_THORNWOOD_BOOKSHELF.get(), THORNWOOD_CHEST.get(), TRAPPED_THORNWOOD_CHEST.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ALPHACENE_PATH.get(), ARID_RAKED_SAND.get(), ASHEN_RAKED_SAND.get(), RED_ARID_RAKED_SAND.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ANCIENT_LEAF_PILE.get());

        this.tag(ModTags.MINEABLE_WITH_KNIFE).add(WARDENZOLA.get());
        this.tag(CCBlockTags.BRAZIERS).add(ANCIENT_BRAZIER.get());

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

        this.tag(ModTags.MINEABLE_WITH_KNIFE)
                .addOptional(DMHUtils.Constants.DINOSAUR_CHOP)
                .addOptional(DMHUtils.Constants.COOKED_DINOSAUR_CHOPS);
        this.tag(DMHTags.MINEABLE_SHEARS).add(HONEYCOMB_BLOCK, WAX_BLOCK.get());

        this.tag(DMHTags.RARE_ORES).addTags(BlockTags.GOLD_ORES)
                .addOptionalTag(new ResourceLocation("forge", "ores/silver"))
                .addOptionalTag(new ResourceLocation("forge", "ores/tin"));
        this.tag(DMHTags.COMMON_ORES).add(AMETHYST_CLUSTER).addTags(BlockTags.IRON_ORES, BlockTags.COPPER_ORES)
                .addOptional(new ResourceLocation("pigsteel", "porkslag"))
                .addOptional(new ResourceLocation("sullysmod", "jade_ore"))
                .addOptional(new ResourceLocation("sullysmod", "deepslate_jade_ore"))
                .addOptionalTag(new ResourceLocation("forge", "ores/lead"))
                .addOptionalTag(new ResourceLocation("forge", "ores/zinc"));

        this.tag(DMHTags.CHANNELS_LIGHTNING).add(JUKEBOX);
        this.tag(DMHTags.NO_XP_REWARD_ON_PLACE).add(FROSTED_ICE);
        this.tag(DMHTags.ENCASED_PIPES_BLOCKS).add(WHITE_ENCASED_PIPE.get(), BROWN_ENCASED_PIPE.get(), GRAY_ENCASED_PIPE.get(), LIGHT_GRAY_ENCASED_PIPE.get(), RED_ENCASED_PIPE.get(), ORANGE_ENCASED_PIPE.get(),
                YELLOW_ENCASED_PIPE.get(), LIME_ENCASED_PIPE.get(), GREEN_ENCASED_PIPE.get(), BLUE_ENCASED_PIPE.get(), LIGHT_BLUE_ENCASED_PIPE.get(), CYAN_ENCASED_PIPE.get(), PURPLE_ENCASED_PIPE.get(),
                MAGENTA_ENCASED_PIPE.get(), PINK_ENCASED_PIPE.get(), BLACK_ENCASED_PIPE.get(), ROSE_ENCASED_PIPE.get(), MAROON_ENCASED_PIPE.get(), GINGER_ENCASED_PIPE.get(), TAN_ENCASED_PIPE.get(),
                BEIGE_ENCASED_PIPE.get(), CORAL_ENCASED_PIPE.get(), OLIVE_ENCASED_PIPE.get(), FOREST_ENCASED_PIPE.get(), VERDANT_ENCASED_PIPE.get(), AMBER_ENCASED_PIPE.get(),
                TEAL_ENCASED_PIPE.get(), MINT_ENCASED_PIPE.get(), AQUA_ENCASED_PIPE.get(), SLATE_ENCASED_PIPE.get(), NAVY_ENCASED_PIPE.get(), INDIGO_ENCASED_PIPE.get());

        this.tag(DMHTags.PIPE_BLOCKS).addTag(DMHTags.ENCASED_PIPES_BLOCKS);
    }

}
