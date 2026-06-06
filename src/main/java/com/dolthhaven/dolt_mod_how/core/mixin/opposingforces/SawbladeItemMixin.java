package com.dolthhaven.dolt_mod_how.core.mixin.opposingforces;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.unusualmodding.opposing_force.events.ForgeEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(ForgeEvents.class)
public class SawbladeItemMixin {
    @WrapWithCondition(method = "onBlockBreak", at = @At(value = "INVOKE", target = "Lcom/unusualmodding/opposing_force/items/SawbladeItem;chopTree(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V"))
    private static boolean treeChop(Level belowPos, BlockPos logState, Player logPos) {
        return DMHConfig.COMMON.opposeForceSawBladeChopTree.get();
    }
}
