package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.server.entity.ai.AnimalLootChestsGoal;
import com.ninni.spawn.server.entity.accessor.ChestBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalLootChestsGoal.class)
public class AnimalLootChestsGoalMixin {
    @Inject(method = "isChestRaidable", at = @At("HEAD"), cancellable = true, remap = false)
    private void sex(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(pos).getBlock() instanceof BaseEntityBlock) {
            if (world.getBlockEntity(pos) instanceof ChestBlockEntityAccessor accessor && accessor.getOctopusOwner() != null) {
                cir.setReturnValue(false);
            }
        }

    }
}
