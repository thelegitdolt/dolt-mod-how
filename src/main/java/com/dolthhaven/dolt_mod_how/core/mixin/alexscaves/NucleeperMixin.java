package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import com.github.alexmodguy.alexscaves.server.entity.living.NucleeperEntity;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NucleeperEntity.class)
public class NucleeperMixin {
    @ModifyArg(method = "dropCustomDeathLoot", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/living/NucleeperEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private ItemLike hi(ItemLike par1) {
        return DMHACCompat.getFusionDisc();
    }
}
