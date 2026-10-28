package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.advancement.SpeedometerTrigger;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHCriteriaTriggers {
    public static final EmptyTrigger THUNDERDOME = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("thunderdome")));

    public static final EmptyTrigger GENERATE_LOOT_WITH_BAD_LUCK = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("generate_loot_with_bad_luck")));
    public static final EmptyTrigger NETHER_THRASHER = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("nether_thrasher")));

    public static final SpeedometerTrigger SPEEDOMETER_SPEED = CriteriaTriggers.register(new SpeedometerTrigger());
}
