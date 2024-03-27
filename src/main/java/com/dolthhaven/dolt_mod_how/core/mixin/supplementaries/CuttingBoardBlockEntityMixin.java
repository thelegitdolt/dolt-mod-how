package com.dolthhaven.dolt_mod_how.core.mixin.supplementaries;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import net.mehvahdjukaar.supplementaries.common.items.QuiverItem;
import net.mehvahdjukaar.supplementaries.common.items.forge.QuiverItemImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Objects;

/**
 * Mixins cutting boards so that when they process quivers, they will always drop every arrow they have inside it.
 */
@Mixin(CuttingBoardBlockEntity.class)
public abstract class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {
    @Shadow
    public abstract IItemHandler getInventory();

    public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "lambda$processStoredItemUsingTool$2(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lvectorwing/farmersdelight/common/crafting/CuttingBoardRecipe;)V",
    at = @At("HEAD"), remap = false)
    private void DoltModHow$ProcessQuiver(ItemStack stack, Player player, CuttingBoardRecipe recipe, CallbackInfo ci) {
        if (!DoltModHowConfig.COMMON.doHackyQuiverRecipe.get()) return;

        assert this.level != null;
        ItemStack boardStack = this.getInventory().getStackInSlot(0);
        if (boardStack.getItem() instanceof QuiverItem && QuiverItemImpl.getQuiverData(boardStack) != null) {

            for (ItemStack resultStack : Objects.requireNonNull(QuiverItemImpl.getQuiverData(boardStack)).getContentView()) {
                Direction direction = this.getBlockState().getValue(CuttingBoardBlock.FACING).getCounterClockWise().getOpposite();

                ItemUtils.spawnItemEntity(this.level, resultStack.copy(), (double) this.worldPosition.getX() + 0.5 + (double) direction.getStepX() * 0.2, (double) this.worldPosition.getY() + 0.2, (double) this.worldPosition.getZ() + 0.5 + (double) direction.getStepZ() * 0.2, (float) direction.getStepX() * 0.2F, 0.0, (float) direction.getStepZ() * 0.2F);
            }
        }
    }
}
