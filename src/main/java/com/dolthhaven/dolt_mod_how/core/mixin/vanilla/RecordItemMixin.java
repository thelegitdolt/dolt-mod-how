package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RecordItem.class)
public class RecordItemMixin {
    @ModifyArg(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/JukeboxBlockEntity;setFirstItem(Lnet/minecraft/world/item/ItemStack;)V"))
    private ItemStack setOneCount(ItemStack par1) {
        if (DMHConfig.COMMON.musicDiscsStack.get()) par1.setCount(1);
        return par1;
    }
}
