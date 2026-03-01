package com.dolthhaven.dolt_mod_how.core.mixin.amendments;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.AmendmentsBugfix;
import net.mehvahdjukaar.amendments.common.block.DirectionalCakeBlock;
import net.mehvahdjukaar.amendments.common.block.DoubleCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(DirectionalCakeBlock.class)
public class DirectionalCakeBlockMixin {
    @Inject(method = "useGeneric", at = @At("HEAD"), remap = false, cancellable = true)
    private void hi(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, boolean canEat, CallbackInfoReturnable<InteractionResult> cir){
        ItemStack stack = player.getItemInHand(handIn);
        if (stack.is(ModTags.KNIVES)) {
            if (state.getBlock() instanceof DoubleCakeBlock doubleCake) {
                ResourceLocation idrl = DMHUtils.getBlockId(doubleCake);
                if (idrl == null) return;

                String id = idrl.getPath().replace("/double_", ":");
                Block block = DMHUtils.getPotentialBlock(new ResourceLocation(id));
                Item sliceItem = DMHUtils.getPotentialItem(AmendmentsBugfix.CAKE_SLICE_MAP.get(id));
                if (sliceItem == null || block == null) return;
                BlockState cakeState = block.defaultBlockState();


                int bites = state.getValue(CakeBlock.BITES);
                if (bites < 6) {
                    level.setBlock(pos, state.setValue(CakeBlock.BITES, bites + 1), 3);
                } else {
                    level.setBlock(pos, cakeState, 3);
                }

                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(sliceItem));
                level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
                cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
}
