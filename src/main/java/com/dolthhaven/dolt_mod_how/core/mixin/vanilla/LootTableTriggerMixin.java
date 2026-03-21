package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import net.minecraft.advancements.critereon.LootTableTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTableTrigger.class)
public class LootTableTriggerMixin {
    @Inject(method = "trigger", at = @At("HEAD"))
    private void hi(ServerPlayer player, ResourceLocation loc, CallbackInfo ci) {
        if (player.getLuck() < 0 && loc.getPath().startsWith("chests/")) {
            DMHCriteriaTriggers.GENERATE_LOOT_WITH_BAD_LUCK.trigger(player);
        }
    }
}
