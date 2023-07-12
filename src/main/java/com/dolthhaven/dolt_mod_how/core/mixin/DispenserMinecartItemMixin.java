package com.dolthhaven.dolt_mod_how.core.mixin;

import net.mehvahdjukaar.supplementaries.common.items.DispenserMinecartItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(DispenserMinecartItem.class)
public class DispenserMinecartItemMixin {
    @ModifyVariable(method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift = At.Shift.AFTER))
    private AbstractMinecart DoltModHow$DispenserMinecartMakesSound(AbstractMinecart cart, UseOnContext context) {
        if (ModList.get().isLoaded("auditory"))
            context.getLevel().playSound(null, cart, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1.0f, 1.2f + context.getLevel().random.nextFloat() * 0.4F);
        return cart;
    }
}
