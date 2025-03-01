package com.dolthhaven.dolt_mod_how.core.mixin.etcetera;

import com.dolthhaven.dolt_mod_how.core.compat.DMHACCompat;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.etcetera.block.entity.PricklyCanBlockEntity;
import com.ninni.etcetera.network.UpdatePricklyCanC2SPacket;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(UpdatePricklyCanC2SPacket.class)
public class UpdatePricklyCanC2SPacketMixin {

    @WrapOperation(method = "lambda$handle$0(Ljava/util/function/Supplier;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;clearContent()V"))
    private static void DoltModHow$ExplodeIfYouSeeRadioactiveStuff(Container instance, Operation<Void> original) {
        boolean alexCavesLoaded = ModList.get().isLoaded(DMHUtils.Constants.ALEXS_CAVES);

        if (alexCavesLoaded && instance instanceof PricklyCanBlockEntity can) {
            for (int i = 0; i < can.getContainerSize(); i++) {
                ItemStack stack = can.getItem(i);

                if (DMHACCompat.explodePricklyCan(stack, i, can)) {
                    return;
                }
            }
        }

        original.call(instance);
    }
}
