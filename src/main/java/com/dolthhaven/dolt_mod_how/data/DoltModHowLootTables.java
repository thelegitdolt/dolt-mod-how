package com.dolthhaven.dolt_mod_how.data;

import com.davigj.blasted_barrens.core.registry.BBBlocks;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.google.common.collect.ImmutableList;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import io.netty.util.AsciiString;
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
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.loot.CanToolPerformAction;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import umpaz.brewinandchewin.common.block.CheeseWheelBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

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

    public static class DoltModHowBlockLoot extends BlockLootSubProvider {
        private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());
        private static final List<Block> BLOCK_BLACKLIST = List.of();

        protected DoltModHowBlockLoot() {
            super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            this.dropSelf(WAX_BLOCK.get());
            this.dropOther(ALPHACENE_PATH.get(), Blocks.DIRT);

            this.dropOther(ARID_RAKED_SAND.get(), AtmosphericBlocks.ARID_SAND.get());
            this.dropOther(RED_ARID_RAKED_SAND.get(), AtmosphericBlocks.RED_ARID_SAND.get());
            this.dropOther(ASHEN_RAKED_SAND.get(), BBBlocks.ASHEN_SAND.get());
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
