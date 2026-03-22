package com.dolthhaven.dolt_mod_how.common.loot.modifiers;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LuckModifier extends LootModifier {
    public static final Supplier<Codec<LuckModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.listOf().fieldOf("blacklist").forGetter(a -> a.blacklistTables))
                    .apply(inst, LuckModifier::new)));

    private final List<ResourceLocation> blacklistTables;

    protected LuckModifier(LootItemCondition[] conditionsIn, List<ResourceLocation> tables) {
        super(conditionsIn);
        this.blacklistTables = tables;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext ctx) {
        if (ctx.hasParam(LootContextParams.BLOCK_STATE) || ctx.hasParam(LootContextParams.KILLER_ENTITY) || ctx.getLuck() == 0) return generatedLoot;

        Item clover = DMHUtils.getPotentialItem(DMHUtils.Constants.FOUR_LEAF_CLOVER);
        float luck = ctx.getLuck();
        int lootSize = generatedLoot.size(); if (lootSize == 0) return generatedLoot;

        RandomSource random = ctx.getRandom();
        ResourceLocation lootId = ctx.getQueriedLootTableId();
        if (lootId.getPath().startsWith("chests/") && !blacklistTables.contains(lootId)) {
            double addThirteenOdds = -0.33 * luck;


            if (luck < 0) {
                for (int i = 0; i < weightedRound(-luck, random); i++) {
                    if (generatedLoot.isEmpty()) return generatedLoot;
                    ItemStack removedStack = generatedLoot.remove(random.nextInt(lootSize));

                    if (lootSize < 5) {
                        generatedLoot.add(removedStack.copyWithCount(removedStack.getCount() / 2 + random.nextInt(1)));
                    }
                }

                if (ctx.getRandom().nextDouble() < addThirteenOdds) {
                    generatedLoot.add(new ItemStack(Items.MUSIC_DISC_13));
                }
            }

            if (luck > 0) {
                List<ItemStack> stacks = rollLootTableWithNoLuck(ctx);

                for (int i = 0; i < weightedRound(luck, random); i++) {
                    Util.getRandomSafe(stacks, random).ifPresent(stack -> {
                        if (lootSize < 5 && random.nextBoolean()) {
                            generatedLoot.add(stack);
                        } else {
                            generatedLoot.add(stack);
                        }
                    });
                }

                if (clover != null && luck > 0.8 && random.nextInt(3) == 0) {
                    generatedLoot.add(new ItemStack(clover));
                }
            }
        }
        return generatedLoot;
    }

    private static float weightedRound(float a, RandomSource random) {
        float floored = Mth.floor(a);
        float decimal = a - floored;
        return floored + (random.nextDouble() < decimal ? 1 : 0);
    }

    private static List<ItemStack> rollLootTableWithNoLuck(LootContext ctx) {
        float oldLuck = ctx.getLuck();
        LootParams lootParams = ctx.params;
        lootParams.luck = 0;

        List<ItemStack> items = new ArrayList<>();
        ctx.getResolver().getLootTable(ctx.getQueriedLootTableId()).getRandomItems(ctx, items::add);

        lootParams.luck = oldLuck;
        return items;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
