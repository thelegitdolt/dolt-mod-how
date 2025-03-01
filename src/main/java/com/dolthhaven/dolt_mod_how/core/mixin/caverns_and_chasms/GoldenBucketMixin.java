package com.dolthhaven.dolt_mod_how.core.mixin.caverns_and_chasms;

import com.dolthhaven.dolt_mod_how.common.item.DMHGoldenBucketItem;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoldenBucketItem.class)
public abstract class GoldenBucketMixin extends Item implements DispensibleContainerItem {
    @Shadow public abstract Fluid getFluid();

    public GoldenBucketMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Inject(method = "use", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/BucketPickup;pickupBlock(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/item/ItemStack;",
    shift = At.Shift.AFTER), cancellable = true)
    private void injected(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir,
                          @Local BlockHitResult result, @Local BlockState state) {
        if (getFluid() != Fluids.EMPTY) {
            return;
        }


        ItemStack stack = DMHGoldenBucketItem.getFilledRealBucket(state);

        if (stack != null && state.getBlock() instanceof BucketPickup pickup) {
            pickup.pickupBlock(level, result.getBlockPos(), state);
            ItemStack newBucket = ItemUtils.createFilledResult(player.getItemInHand(hand), player, stack);

            player.awardStat(Stats.ITEM_USED.get(this));
            pickup.getPickupSound(state)
                    .ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
            level.gameEvent(player, GameEvent.FLUID_PICKUP, result.getBlockPos());
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, newBucket);
            }
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(newBucket, level.isClientSide()));
        }
    }
}
