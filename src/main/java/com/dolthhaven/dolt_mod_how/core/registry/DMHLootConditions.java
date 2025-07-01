package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DMHLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister
            .create(Registries.LOOT_CONDITION_TYPE, DoltModHow.MOD_ID);

    public static final RegistryObject<LootItemConditionType> CONFIG = LOOT_CONDITIONS.register("config",
            () -> DataUtil.registerConfigCondition(DoltModHow.MOD_ID, DMHConfig.COMMON));
}
