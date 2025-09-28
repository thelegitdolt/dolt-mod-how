package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent;
import com.dolthhaven.dolt_mod_how.integration.DyeDepotCompat;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.addons.oddities.block.pipe.BasePipeBlock;
import org.violetmoon.quark.addons.oddities.block.pipe.CopperPipeBlock;

@Mixin(CopperPipeBlock.class)
public class CopperPipeBlockMixin {
    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean thing(boolean original, @Share("color") LocalRef<String> colorHolder, @Local ItemStack stack) {
        if (!original) {
            String colorMaybe = DyeDepotCompat.getAny(ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath().replace("_stained_glass", ""));
            if (colorMaybe == null) {
                return false;
            } colorHolder.set(colorMaybe);
        } return true;
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lorg/violetmoon/quark/addons/oddities/block/pipe/BasePipeBlock;getTargetState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState hi(BasePipeBlock instance, LevelAccessor facing, BlockPos pos, Operation<BlockState> original,
                          @Share("color") LocalRef<String> colorHolder) {
        String color = colorHolder.get();
        BlockState originalState = original.call(instance, facing, pos);
        if (color == null) {
            return originalState;
        } else {
            BlockState state = ForgeRegistries.BLOCKS.getValue(DoltModHow.rl(color + "_encased_pipe")).defaultBlockState();
            return DMHRightClickEvent.copyStates(state, originalState);
        }
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;updateNeighborsAt(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"))
    private void hi(Level instance, BlockPos pos, Block block, Operation<Void> original, @Share("color") LocalRef<String> colorHolder) {
        original.call(instance, pos,
                colorHolder.get() == null ? block : ForgeRegistries.BLOCKS.getValue(DoltModHow.rl(colorHolder.get() + "_encased_pipe")));
    }
}
