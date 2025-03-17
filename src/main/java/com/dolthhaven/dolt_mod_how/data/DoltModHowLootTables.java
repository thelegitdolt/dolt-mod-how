package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;


public class DoltModHowLootTables extends LootTableProvider {
    public DoltModHowLootTables(PackOutput packOutput) {
        super(packOutput, BuiltInLootTables.all(), ImmutableList.of(
                new LootTableProvider.SubProviderEntry(DoltModHowBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext context) {
    }

    static List<Block> BLOCK_BLACKLIST = List.of();

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

        }

        private void colony(RegistryObject<? extends Block> block) {
            if (block.get() instanceof MushroomColonyBlock colony) {
                Item shroomItem = colony.mushroomType.get();
                Item colonyItem = colony.asItem();
                this.add(block.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(AlternativesEntry.alternatives(LootItem.lootTableItem(colonyItem)
                                                .when(stateCond(block, MushroomColonyBlock.COLONY_AGE, 3))
                                                .when(HAS_SHEARS))
                                        .otherwise(LootItem.lootTableItem(shroomItem)
                                                .apply(MushroomColonyBlock.COLONY_AGE.getPossibleValues(), value -> SetItemCountFunction
                                                        .setCount(ConstantValue.exactly(2.0f + value), false)
                                                        .when(stateCond(block, MushroomColonyBlock.COLONY_AGE, value)))))));
            }
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
