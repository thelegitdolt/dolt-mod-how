package com.dolthhaven.dolt_mod_how.core.mixin;

import net.mehvahdjukaar.supplementaries.common.items.QuiverItem;
import net.mehvahdjukaar.supplementaries.common.items.forge.QuiverItemImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Objects;

@Mixin(CuttingBoardBlockEntity.class)
public class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {
    public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "lambda$processStoredItemUsingTool$2(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lvectorwing/farmersdelight/common/crafting/CuttingBoardRecipe;)V",
    at = @At("HEAD"), remap = false)
    private void DoltModHow$ProcessQuiver(ItemStack stack, Player player, CuttingBoardRecipe recipe, CallbackInfo ci) {
        if (stack.getItem() instanceof QuiverItem && QuiverItemImpl.getQuiverData(stack) != null) {
            for (ItemStack arrow : Objects.requireNonNull(QuiverItemImpl.getQuiverData(stack)).getContentView()) {
                if (arrow!= null) {
                    Direction direction = getBlockState().getValue(CuttingBoardBlock.FACING).getCounterClockWise();
                    ItemUtils.spawnItemEntity(Objects.requireNonNull(level), arrow.copy(),
                            worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2, worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2),
                            direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);
                }
            }
        }
    }
}
