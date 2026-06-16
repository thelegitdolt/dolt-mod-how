package com.dolthhaven.dolt_mod_how.core.mixin.neapolitan;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.teamabnormals.neapolitan.common.item.HealingItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(HealingItem.class)
public class HealingItemMixin {
    @ModifyArg(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lcom/teamabnormals/neapolitan/common/item/HealingItem;applyHealing(FLnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private float DoltModHow$HealEqualToNutrition(float healAmount) {
        Item item = (Item) (Object) this;
        if (DMHConfig.COMMON.hasRemovedHunger.get() && item.foodProperties != null) {
            return item.foodProperties.nutrition;
        }
        return healAmount;
    }
}
