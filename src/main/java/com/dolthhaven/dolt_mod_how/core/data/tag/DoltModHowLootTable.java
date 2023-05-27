package com.dolthhaven.dolt_mod_how.core.data.tag;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DoltModHowLootTable extends LootTableProvider {
    public DoltModHowLootTable(DataGenerator gen) {
        super(gen);
    }

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(DoltCompatBlockLoot::new, LootContextParamSets.BLOCK));


    @Override
    public @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return tables;
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext context) {}


    private static class DoltCompatBlockLoot extends BlockLoot {
        @Override
        public void addTables() {
            this.dropSelf(MUD_LANTERN.get());
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks() {
             return List.of(MUD_LANTERN.get());
        }

    }


}
