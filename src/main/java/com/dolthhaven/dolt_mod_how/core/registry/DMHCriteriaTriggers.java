package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.WatchMobKillTrigger;
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
    public static final WatchMobKillTrigger WATCH_MOB_KILL = CriteriaTriggers.register(new WatchMobKillTrigger());
    public static final EmptyTrigger KILL_BUG_WITH_LIGHTNING = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("kill_stickbug_with_lightning")));
    public static final EmptyTrigger GENERATE_LOOT_WITH_BAD_LUCK = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("generate_loot_with_bad_luck")));
    public static final EmptyTrigger NETHER_THRASHER = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("nether_thrasher")));
    public static final EmptyTrigger WITNESS_HOLLER_POSSESS_JUKEBOX = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("witness_holler_possess_jukebox")));
    public static final EmptyTrigger TRIGGER_MIME_TOTEM = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("trigger_mime_totem")));
    public static final EmptyTrigger SLAY_BEWEREAGER_WITH_SILVER = CriteriaTriggers.register(new EmptyTrigger(DoltModHow.rl("slay_bewereager_with_silver")));
}
