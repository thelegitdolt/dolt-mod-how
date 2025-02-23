package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.other.FakePlayerCauldronInteract;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;


@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {

    @Inject(
            method = "dispenseFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"
            ),
            cancellable = true
    )
    public void onCuttingBoardDispenseFromInject(ServerLevel level, BlockPos pos, CallbackInfo ci, @Local BlockSourceImpl source, @Local ItemStack stack) {
        if (!DMHConfig.COMMON.doDispenserCauldrons.get()) return;

        BlockState facingState = level.getBlockState(pos.relative(source.getBlockState().getValue(DispenserBlock.FACING)));

        if (facingState.getBlock() instanceof AbstractCauldronBlock cauldron) {
            Map<Item, CauldronInteraction> interactions = cauldron.interactions;
            if (interactions.get(stack.getItem()) != null) {

                ItemStack stackRemainder = new FakePlayerCauldronInteract(stack.getItem()).dispense(source, stack);
                ci.cancel();
            }
        }
    }
}
