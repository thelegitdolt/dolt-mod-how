package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID)
public class DMHCriteriaTriggers {
    public static final EmptyTrigger THUNDERDOME = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("thunderdome")));
    public static final EmptyTrigger REWIND_GREAT_DISTANCES = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("rewind_great_distances")));

    // for Not Endorsed advancement
    public static final EmptyTrigger DUI = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("dui")));
    public static final EmptyTrigger NETHER_THRASHER = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("nether_thrasher")));
    public static final EmptyTrigger TRIGGER_MIME_TOTEM = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("trigger_mime_totem")));
    public static final EmptyTrigger PVZ = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("pvz")));
    public static final EmptyTrigger SLAY_BEWEREAGER_WITH_SILVER = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("slay_bewereager_with_silver")));

}
