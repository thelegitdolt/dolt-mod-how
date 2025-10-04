package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent.ITEM_PLACE_MAP;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "useOn", at = @At(value = "RETURN"), cancellable = true)
    private void DoltModHow$PlaceItems(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionResult result = cir.getReturnValue();
        ItemStack self = (ItemStack) (Object) this;
        if (result == InteractionResult.PASS) {
            Pair<Supplier<Boolean>, BlockItem> pair = ITEM_PLACE_MAP.get(self.getItem());
            if (pair != null && pair.getFirst().get()) {
                BlockPlaceContext context = new BlockPlaceContext(useOnContext);
                InteractionResult newResult = pair.getSecond().place(context);
                if (newResult.consumesAction()) {
                    cir.setReturnValue(newResult);
                }
            }
        }
    }
}
