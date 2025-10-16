package com.dolthhaven.dolt_mod_how.data;

import com.davigj.blasted_barrens.core.registry.BBBlocks;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import com.google.common.collect.ImmutableList;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.loot.CanToolPerformAction;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import umpaz.brewinandchewin.BrewinAndChewin;
import umpaz.brewinandchewin.common.block.CheeseWheelBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;


public class DoltModHowLootTables extends LootTableProvider {
    protected static final LootItemCondition.Builder HAS_SHEARS_TAG = CanToolPerformAction.canToolPerformAction(ToolActions.SHEARS_HARVEST);
    protected static final LootItemCondition.Builder HAS_KNIFE = MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.KNIVES));

    public DoltModHowLootTables(PackOutput packOutput) {
        super(packOutput, BuiltInLootTables.all(), ImmutableList.of(
                new LootTableProvider.SubProviderEntry(DoltModHowBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext context) {
    }

    static List<Block> BLOCK_BLACKLIST = List.of(BOP_GLOW_SHROOM_COLONY.get(), TOADSTOOL_COLONY.get());

    public static class DoltModHowBlockLoot extends BlockLootSubProvider {
        private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());

        protected DoltModHowBlockLoot() {
            super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            this.dropSelf(STURDY_DEEPSLATE.get());
            this.dropSelf(PINE_NUTS_CRATE.get());
            this.dropOther(ALPHACENE_PATH.get(), Blocks.DIRT);

            this.colony(GLOWSHROOM_COLONY);

            this.dropPottedContents(POTTED_TALL_BEACHGRASS.get());
            this.dropPottedContents(POTTED_BEACHGRASS.get());
            this.dropPottedContents(POTTED_ARID_SPROUTS.get());
            this.dropPottedContents(POTTED_MYCELIUM_SPROUTS.get());
            this.dropPottedContents(POTTED_STRAWBERRIES.get());
            this.dropPottedContents(POTTED_WHITE_STRAWBERRIES.get());
            this.dropPottedContents(POTTED_CABBAGE.get());
            this.dropPottedContents(POTTED_ONION.get());
            this.dropPottedContents(POTTED_TOMATOES.get());

            this.add(PEWEN_CHEST.get(), this::createNameableBlockEntityTable);
            this.add(TRAPPED_PEWEN_CHEST.get(), this::createNameableBlockEntityTable);
            this.add(PEWEN_BOOKSHELF.get(), block -> this.createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3)));
            this.add(PEWEN_BEEHIVE.get(), BlockLootSubProvider::createBeeHiveDrop);
            this.dropSelf(PEWEN_LADDER.get());
            this.dropSelf(PEWEN_BOARDS.get());
            this.add(PEWEN_CABINET.get(), this::createNameableBlockEntityTable);
            this.dropWhenSilkTouch(CHISELED_PEWEN_BOOKSHELF.get());
            this.add(ANCIENT_LEAF_PILE.get(), block -> createMultifaceBlockDrops(block,
                    MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))));
            this.dropSelf(ANCIENT_BRAZIER.get());

            this.add(THORNWOOD_CHEST.get(), this::createNameableBlockEntityTable);
            this.add(TRAPPED_THORNWOOD_CHEST.get(), this::createNameableBlockEntityTable);
            this.add(THORNWOOD_CABINET.get(), this::createNameableBlockEntityTable);
            this.add(THORNWOOD_BEEHIVE.get(), BlockLootSubProvider::createBeeHiveDrop);
            this.dropSelf(THORNWOOD_LADDER.get());
            this.dropSelf(WAX_BLOCK.get());
            this.dropSelf(THORNWOOD_BOARDS.get());
            this.add(THORNWOOD_CABINET.get(), this::createNameableBlockEntityTable);
            this.add(THORNWOOD_BOOKSHELF.get(), block -> this.createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3)));
            this.dropWhenSilkTouch(CHISELED_THORNWOOD_BOOKSHELF.get());

            this.dropOther(ARID_RAKED_SAND.get(), AtmosphericBlocks.ARID_SAND.get());
            this.dropOther(RED_ARID_RAKED_SAND.get(), AtmosphericBlocks.RED_ARID_SAND.get());
            this.dropOther(ASHEN_RAKED_SAND.get(), BBBlocks.ASHEN_SAND.get());

            this.dropSelf(ZINC_BRICKS.get());
            this.dropSelf(ZINC_BRICK_STAIRS.get());
            this.add(ZINC_BRICK_SLAB.get(), this::createSlabItemTable);
            this.dropSelf(ZINC_BRICK_WALL.get());
            this.dropSelf(CHISELED_ZINC_BRICKS.get());

            this.cheese(WARDENZOLA);
            this.pipes();


        }

        private void colony(RegistryObject<? extends Block> block) {
            if (block.get() instanceof MushroomColonyBlock colony) {
                Item shroomItem = colony.mushroomType.get();
                Item colonyItem = colony.asItem();
                this.add(block.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(AlternativesEntry.alternatives(LootItem.lootTableItem(colonyItem)
                                                .when(stateCond(block, MushroomColonyBlock.COLONY_AGE, 3))
                                                .when(HAS_SHEARS_TAG))
                                        .otherwise(LootItem.lootTableItem(shroomItem)
                                                .apply(MushroomColonyBlock.COLONY_AGE.getPossibleValues(), value -> SetItemCountFunction
                                                        .setCount(ConstantValue.exactly(2.0f + value), false)
                                                        .when(stateCond(block, MushroomColonyBlock.COLONY_AGE, value)))))));
            }
            else {
                throw new IllegalArgumentException("Not mushroom colony");
            }
        }

        private void cheese(RegistryObject<? extends Block> wheel) {
            if (wheel.get() instanceof CheeseWheelBlock cheese) {
                Item wedge = cheese.cheeseWedgeType.get();
                this.add(wheel.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(wedge).apply(CheeseWheelBlock.SERVINGS.getPossibleValues(), value -> SetItemCountFunction
                                        .setCount(ConstantValue.exactly(value + 1), false).when(stateCond(wheel, CheeseWheelBlock.SERVINGS, value)))
                                        .when(HAS_KNIFE))));
            }
            else {
                throw new IllegalArgumentException("Not cheese");
            }
        }

        private void pipes() {
            this.dropSelf(WHITE_ENCASED_PIPE.get());
            this.dropSelf(BROWN_ENCASED_PIPE.get());
            this.dropSelf(GRAY_ENCASED_PIPE.get());
            this.dropSelf(LIGHT_GRAY_ENCASED_PIPE.get());
            this.dropSelf(RED_ENCASED_PIPE.get());
            this.dropSelf(ORANGE_ENCASED_PIPE.get());
            this.dropSelf(YELLOW_ENCASED_PIPE.get());
            this.dropSelf(LIME_ENCASED_PIPE.get());
            this.dropSelf(GREEN_ENCASED_PIPE.get());
            this.dropSelf(BLUE_ENCASED_PIPE.get());
            this.dropSelf(LIGHT_BLUE_ENCASED_PIPE.get());
            this.dropSelf(CYAN_ENCASED_PIPE.get());
            this.dropSelf(PURPLE_ENCASED_PIPE.get());
            this.dropSelf(MAGENTA_ENCASED_PIPE.get());
            this.dropSelf(PINK_ENCASED_PIPE.get());
            this.dropSelf(BLACK_ENCASED_PIPE.get());
            this.dropSelf(ROSE_ENCASED_PIPE.get());
            this.dropSelf(MAROON_ENCASED_PIPE.get());
            this.dropSelf(GINGER_ENCASED_PIPE.get());
            this.dropSelf(TAN_ENCASED_PIPE.get());
            this.dropSelf(BEIGE_ENCASED_PIPE.get());
            this.dropSelf(CORAL_ENCASED_PIPE.get());
            this.dropSelf(OLIVE_ENCASED_PIPE.get());
            this.dropSelf(FOREST_ENCASED_PIPE.get());
            this.dropSelf(VERDANT_ENCASED_PIPE.get());
            this.dropSelf(AMBER_ENCASED_PIPE.get());
            this.dropSelf(TEAL_ENCASED_PIPE.get());
            this.dropSelf(MINT_ENCASED_PIPE.get());
            this.dropSelf(AQUA_ENCASED_PIPE.get());
            this.dropSelf(SLATE_ENCASED_PIPE.get());
            this.dropSelf(NAVY_ENCASED_PIPE.get());
            this.dropSelf(INDIGO_ENCASED_PIPE.get());
        }

        private static <V extends Comparable<V>> LootItemCondition.Builder stateCond(RegistryObject<? extends Block> block, Property<V> property, V v) {
            return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, v.toString()));
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues()
                    .stream().filter(block -> block.builtInRegistryHolder().key().location().getNamespace().equals(DoltModHow.MOD_ID))
                    .filter(block -> !BLOCK_BLACKLIST.contains(block))
                    .collect(Collectors.toSet());
        }
    }
}
