package com.dolthhaven.dolt_mod_how.core.mixin.auditoryboatsounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BoatItem.class)
public class BoatItemMixin {
    @ModifyVariable(method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift = At.Shift.AFTER
            ))
    private Boat DoltModHow$BluePrintBoatPlaceSound(Boat boat, Level level) {
        level.playSound(null, boat, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 0.8f + level.random.nextFloat() * 0.4F);
        return boat;
    }
}
