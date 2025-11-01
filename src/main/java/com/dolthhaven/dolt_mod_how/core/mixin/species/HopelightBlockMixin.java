package com.dolthhaven.dolt_mod_how.core.mixin.species;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.ninni.species.server.block.HopelightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopelightBlock.class)
public class HopelightBlockMixin extends Block {
    public HopelightBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void hi(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!DMHConfig.COMMON.frogMistLikeHopeLights.get()) return;

        if (context instanceof EntityCollisionContext entityContext) {
            if (entityContext.getEntity() instanceof Player player) {
                ItemStack stack = player.getMainHandItem();
                if (!stack.is(this.asItem()) && !stack.is(ItemTags.HOES)) {
                    cir.setReturnValue(Shapes.empty());
                }
            }
        }
    }
}
