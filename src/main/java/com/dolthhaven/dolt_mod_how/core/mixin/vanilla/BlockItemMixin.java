package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Inject(method = "place", at = @At("RETURN"))
    private void DoltModHow$FreeXPYay(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionResult result = cir.getReturnValue();
        if (context.getPlayer() instanceof ServerPlayer player) {
            boolean shouldAward =
                            DMHConfig.COMMON.xpUponBlockPlace.get() &&
                            !player.getAbilities().instabuild &&
                            result.consumesAction() &&
                            player.getRandom().nextInt(DMHConfig.COMMON.blockPlaceXpChance.get()) == 0;
            if (shouldAward) {
                ExperienceOrb.award((ServerLevel) player.level(), player.position(), player.getRandom().nextInt(3, 6));
            }
        }

    }
}
