package com.dolthhaven.dolt_mod_how.core.mixin.caverns_and_chasms;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.github.alexthe666.citadel.repack.jaad.Play;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem.setFluidLevel;

@Mixin(GoldenBucketItem.class)
public abstract class GoldenBucketMixin extends Item implements DispensibleContainerItem {
    public GoldenBucketMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Shadow
    public abstract Fluid getFluid();

    @Shadow protected abstract void playEmptySound(@org.jetbrains.annotations.Nullable Player player, LevelAccessor level, BlockPos pos);

    @Inject(method = "getFilledBucket*", at = @At("HEAD"), cancellable = true, remap = false)
    private static void DoltModHow$RegisterThisModBuckets(BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if (state.getFluidState().is(ACFluidRegistry.ACID_FLUID_SOURCE.get())) {
            cir.setReturnValue(new ItemStack(DMHItems.GOLDEN_ACID_BUCKET.get()));
        } else if (state.getFluidState().is(ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE.get())) {
            cir.setReturnValue(new ItemStack(DMHItems.GOLDEN_PURPLE_SODA_BUCKET.get()));
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BucketPickup;pickupBlock(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/item/ItemStack;",
            shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, remap = false, cancellable = true)
    private void DoltModHow$RegisterThings(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, ItemStack stack, CompoundTag tag, int bucketLevel, BlockHitResult result, InteractionResultHolder ret, BlockPos pos, Direction direction, BlockPos sourcePos, BlockState sourceState, BucketPickup bucketPickup, Block var14) {
        FluidState fluidState = sourceState.getFluidState();
        Fluid fluid;
        if (fluidState.is(ACFluidRegistry.ACID_FLUID_SOURCE.get())) {
            fluid = ACFluidRegistry.ACID_FLUID_SOURCE.get();
        } else if (fluidState.is(ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE.get())) {
            fluid = ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE.get();
        } else {
            return;
        }

        ItemStack newBucket = ItemStack.EMPTY;
        if (fluid != Fluids.EMPTY && GoldenBucketItem.getFilledBucket(sourceState) != null) {
            newBucket = ItemUtils.createFilledResult(stack, player, GoldenBucketItem.getFilledBucket(sourceState));
            if (this.getFluid() != Fluids.EMPTY) {
                setFluidLevel(newBucket, bucketLevel + 1);
            }
        }

        if (!newBucket.isEmpty()) {
            player.awardStat(Stats.ITEM_USED.get(this));
            bucketPickup.getPickupSound(sourceState).ifPresent((soundEvent) -> {
                player.playSound(soundEvent, 1.0F, 1.0F);
            });
            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, newBucket);
            }
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(newBucket, level.isClientSide()));
        }
    }

    @Inject(method = "emptyContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LiquidBlockContainer;placeLiquid(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z", shift = At.Shift.BEFORE), cancellable = true)
    private void DMH$CheckIfCanPlaceInNether(Player player, Level level, BlockPos pos, BlockHitResult result, CallbackInfoReturnable<Boolean> cir) {
        if (this.getFluid().getFluidType().isVaporizedOnPlacement(level, pos, new FluidStack(this.getFluid(), 0))) {
            this.getFluid().getFluidType().onVaporize(player, level, pos, new FluidStack(this.getFluid(), 0));
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "emptyContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;liquid()Z",
            shift = At.Shift.BY, by = -2), cancellable = true)
    private void DMH$CheckIfCanPlaceInNetherTwo(Player player, Level level, BlockPos pos, BlockHitResult result, CallbackInfoReturnable<Boolean> cir) {
        if (this.getFluid().getFluidType().isVaporizedOnPlacement(level, pos, new FluidStack(this.getFluid(), 0))) {
            this.getFluid().getFluidType().onVaporize(player, level, pos, new FluidStack(this.getFluid(), 0));
            cir.setReturnValue(true);
        }
    }
}
