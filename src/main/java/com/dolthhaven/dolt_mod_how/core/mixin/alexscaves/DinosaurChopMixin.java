package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import com.github.alexmodguy.alexscaves.server.block.DinosaurChopBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

@Mixin(DinosaurChopBlock.class)
public class DinosaurChopMixin {
    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$KnifeChopUpKnife(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (ModList.get().isLoaded(DMHUtils.Constants.CAVE_DELIGHT)) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ModTags.KNIVES) && state.getBlock() instanceof DinosaurChopBlock chopBlock) {
                Item item = DMHACCompat.getMeatItem(chopBlock);
                int bites = state.getValue(DinosaurChopBlock.BITES);

                if (item != null) {
                    level.setBlock(pos, DMHACCompat.exhaustOneBite(state), Block.UPDATE_ALL_IMMEDIATE);
                    level.playSound(null, pos, SoundEvents.CANDLE_BREAK, SoundSource.PLAYERS, 0.8f, 0.8f);
                    Block.dropResources(state, level, pos);
                    ItemUtils.spawnItemEntity(level, new ItemStack(item),
                            pos.getX() + bites * 0.2, pos.getY() + 0.7, pos.getZ() + 0.5, -0.10, 0, 0);
                    cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
                }
            }
        }
    }


    @WrapOperation(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getFoodData()Lnet/minecraft/world/food/FoodData;"))
    private FoodData DoltModHow$MakeSoundWhenEatBlock(Player instance, Operation<FoodData> original) {
        instance.playSound(SoundEvents.GENERIC_EAT);
        return original.call(instance);
    }
}
