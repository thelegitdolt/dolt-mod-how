package com.dolthhaven.dolt_mod_how.common.loot.modifiers;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DropSelfLootModifier extends LootModifier {
    public static final Supplier<Codec<DropSelfLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .apply(inst, DropSelfLootModifier::new)));

    protected DropSelfLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (state != null) {
            generatedLoot.clear();
            simulateNormalBreaking(state, generatedLoot);
        } return generatedLoot;
    }

    private static void simulateNormalBreaking(BlockState state, ObjectArrayList<ItemStack> loot) {
        if (state.getBlock() instanceof SlabBlock slab && state.getValue(SlabBlock.TYPE) == SlabType.DOUBLE) {
            loot.add(new ItemStack(slab.asItem(), 2));
        } else {
            for (Property<?> property : state.getProperties()) {
                if (property.getName().equals("half") && state.getValue(property) instanceof DoubleBlockHalf half && half == DoubleBlockHalf.UPPER) {
                    return;
                }
            }
            loot.add(new ItemStack(state.getBlock().asItem(), 1));
        }
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
