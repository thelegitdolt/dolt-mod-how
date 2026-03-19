package com.dolthhaven.dolt_mod_how.common.loot.modifiers;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class ThirteenModifier extends LootModifier {
    public static final Supplier<Codec<ThirteenModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.listOf().fieldOf("whitelist").forGetter(a -> a.tables))
                    .apply(inst, ThirteenModifier::new)));

    private final List<ResourceLocation> tables;

    protected ThirteenModifier(LootItemCondition[] conditionsIn, List<ResourceLocation> tables) {
        super(conditionsIn);
        this.tables = tables;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (tables.contains(context.getQueriedLootTableId()) && context.getLuck() < 0) {
            double odds = -0.33 * context.getLuck();
            if (context.getRandom().nextDouble() < odds) {
                generatedLoot.add(new ItemStack(Items.MUSIC_DISC_13));
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
