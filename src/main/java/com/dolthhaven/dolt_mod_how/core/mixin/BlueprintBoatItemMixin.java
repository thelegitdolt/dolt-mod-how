package com.dolthhaven.dolt_mod_how.core.mixin;

import com.teamabnormals.blueprint.common.item.BlueprintBoatItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(BlueprintBoatItem.class)
public class BlueprintBoatItemMixin extends Item {
    public BlueprintBoatItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @ModifyVariable(method = "Lcom/teamabnormals/blueprint/common/item/BlueprintBoatItem;use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift = At.Shift.AFTER
            ))
    private Boat DoltModHow$BluePrintBoatPlaceSound(Boat boat, Level level) {
        if (ModList.get().isLoaded("auditory"))
            level.playSound(null, boat, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 0.8f + level.random.nextFloat() * 0.4F);
        return boat;
    }
}
