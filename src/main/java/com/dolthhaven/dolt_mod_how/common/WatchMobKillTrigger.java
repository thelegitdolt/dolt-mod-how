package com.dolthhaven.dolt_mod_how.common;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WatchMobKillTrigger extends SimpleCriterionTrigger<WatchMobKillTrigger.TriggerInstance> {
    private static final ResourceLocation ID = DoltModHow.rl("watch_mob_kill");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected WatchMobKillTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext ctx) {
        ContextAwarePredicate killer = EntityPredicate.fromJson(json, "killer", ctx);
        ContextAwarePredicate victim = EntityPredicate.fromJson(json, "victim", ctx);
        return new WatchMobKillTrigger.TriggerInstance(player, killer, victim);
    }

    public void trigger(ServerPlayer player, Entity killer, Entity victim) {
        LootContext killerContext = EntityPredicate.createContext(player, killer);
        LootContext victimContext = EntityPredicate.createContext(player, victim);
        this.trigger(player, (instance) ->
                instance.matches(killerContext, victimContext));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance{
        private final ContextAwarePredicate killer;
        private final ContextAwarePredicate victim;

        public TriggerInstance(ContextAwarePredicate player, ContextAwarePredicate killer, ContextAwarePredicate victim) {
            super(ID, player);
            this.killer = killer;
            this.victim = victim;
        }

        public static WatchMobKillTrigger.TriggerInstance mobKillWatched(ContextAwarePredicate killer, ContextAwarePredicate victim) {
            return new WatchMobKillTrigger.TriggerInstance(ContextAwarePredicate.ANY, killer, victim);
        }

        public boolean matches(LootContext killer, LootContext victim) {
            return this.killer.matches(killer) && this.victim.matches(victim);
        }
    }
}
