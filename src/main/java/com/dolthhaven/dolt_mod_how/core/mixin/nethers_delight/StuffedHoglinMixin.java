package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.soytutta.mynethersdelight.common.block.StuffedHoglinBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(StuffedHoglinBlock.class)
public class StuffedHoglinMixin {
    @WrapOperation(method = "getDrops", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private <E> boolean amogus(List<E> instance, E e, Operation<Boolean> original) {
        if (e instanceof ItemStack stack) {
            if (stack.is(Items.BONE_MEAL)) return false;
        }
        return original.call(instance, e);
    }
}

