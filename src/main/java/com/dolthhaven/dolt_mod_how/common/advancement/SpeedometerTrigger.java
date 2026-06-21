package com.dolthhaven.dolt_mod_how.common.advancement;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SpeedometerTrigger extends SimpleCriterionTrigger<SpeedometerTrigger.TriggerInstance> {
    private static final ResourceLocation ID = DoltModHow.rl("speedometer_speed");

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext ctx) {
        float speed = json.get("speed").getAsFloat();
        return new TriggerInstance(player, speed);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, float speed) {
        this.trigger(player, (instance) -> instance.matches(speed));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final float speed;

        public TriggerInstance(ContextAwarePredicate player, float speed) {
            super(ID, player);
            this.speed = speed;
        }

        public boolean matches(float speed) {
            return speed >= this.speed;
        }
    }
}
