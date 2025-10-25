package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.loot.condition.TrichotomyLootCondition;
import com.dolthhaven.dolt_mod_how.common.loot.modifiers.DropSelfLootModifier;
import com.dolthhaven.dolt_mod_how.common.loot.modifiers.RemoveItemLootModifier;
import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DMHLoot {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister
            .create(Registries.LOOT_CONDITION_TYPE, DoltModHow.MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister
            .create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, DoltModHow.MOD_ID);

    public static final RegistryObject<LootItemConditionType> CONFIG = LOOT_CONDITIONS.register("config",
            () -> DataUtil.registerConfigCondition(DoltModHow.MOD_ID, DMHConfig.COMMON));

    public static final RegistryObject<LootItemConditionType> TRICHOTOMY = LOOT_CONDITIONS.register("trichotomy",
            () -> new LootItemConditionType(new TrichotomyLootCondition.Serializer()));


    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> DROP_SELF = LOOT_MODIFIERS.register("drop_self", DropSelfLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REMOVE_ITEM = LOOT_MODIFIERS.register("remove_item", RemoveItemLootModifier.CODEC);

}
