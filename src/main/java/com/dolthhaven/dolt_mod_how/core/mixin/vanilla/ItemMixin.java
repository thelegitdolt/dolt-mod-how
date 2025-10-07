package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.common.item.RecoveryCompassItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent.ITEM_PLACE_MAP;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At(value = "RETURN"), cancellable = true)
    private void DoltModHow$DoRecoveryCompass(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        InteractionResultHolder<ItemStack> result = cir.getReturnValue();

        Item self = (Item) (Object) this;
        if (result.getResult() == InteractionResult.PASS && self == Items.RECOVERY_COMPASS ) {
            InteractionResultHolder<ItemStack> newResult = RecoveryCompassItem.use(level, player, hand);
            if (newResult.getResult().consumesAction()) cir.setReturnValue(newResult);
        }
    }

    @Inject(method = "useOn", at = @At(value = "RETURN"), cancellable = true)
    private void DoltModHow$PlaceItems(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionResult result = cir.getReturnValue();
        Item self = (Item) (Object) this;
        if (result == InteractionResult.PASS) {
            Pair<Supplier<Boolean>, BlockItem> pair = ITEM_PLACE_MAP.get(self);
            if (pair != null && pair.getFirst().get()) {
                BlockPlaceContext context = new BlockPlaceContext(useOnContext);
                InteractionResult newResult = pair.getSecond().place(context);
                if (newResult.consumesAction()) {
                    cir.setReturnValue(newResult);
                }
            }
        }
    }

    @Inject(method = "isFoil", at = @At(value = "RETURN"), cancellable = true)
    private void DoltModHow$RecoveryCompassGlint(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(Items.RECOVERY_COMPASS) && RecoveryCompassItem.isLocked(stack)) cir.setReturnValue(true);
    }
}
