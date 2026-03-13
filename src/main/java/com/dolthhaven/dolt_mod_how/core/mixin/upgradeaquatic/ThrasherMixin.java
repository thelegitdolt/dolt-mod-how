package com.dolthhaven.dolt_mod_how.core.mixin.upgradeaquatic;

import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.dolthhaven.dolt_mod_how.integration.DMHUACompat;
import com.teamabnormals.upgrade_aquatic.common.entity.monster.Thrasher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(Thrasher.class)
public abstract class ThrasherMixin extends Entity {
    @Inject(method = "tick", at = @At("HEAD"))
    private void grantAdvancement(CallbackInfo ci) {
        if (!DMHUACompat.isGreatThrasher(this))
            return;

        if ((this.level().getGameTime()) % 4 != 0) {
            return;
        }

        if (this.level().dimension() == Level.NETHER) {
            List<Player> players = this.level().getEntitiesOfClass(Player.class,
                    new AABB(this.position(), this.position().add(1, 1, 1)).inflate(20, 20, 20));

            players.forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    DMHCriteriaTriggers.NETHER_THRASHER.trigger(serverPlayer);
                }
            });
        }
    }

    public ThrasherMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }
}
