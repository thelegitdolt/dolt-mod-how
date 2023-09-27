package com.dolthhaven.dolt_mod_how.core.other;

import net.mehvahdjukaar.moonlight.api.util.DispenserHelper;
import net.mehvahdjukaar.moonlight.api.util.fake_player.FakePlayerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FakePlayerCauldronInteract extends DispenserHelper.AdditionalDispenserBehavior {
    public FakePlayerCauldronInteract(Item item) {
        super(item);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> customBehavior(BlockSource source, @NotNull ItemStack stack) {
        ServerLevel level = source.getLevel();
        Direction dir = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos pos = source.getPos().relative(dir);
        Player fp = FakePlayerManager.getDefault(level);

        ItemStack aeadb = stack.copy();
        aeadb.setCount(1);
        fp.setItemInHand(InteractionHand.MAIN_HAND, aeadb);

        InteractionResult v = InteractionResult.PASS;

        BlockState cauldronState = level.getBlockState(pos);
        if (cauldronState.getBlock() instanceof AbstractCauldronBlock cauldron) {
            stack.shrink(1);
             v = cauldron.use(cauldronState, level, pos, fp, InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.atCenterOf(pos), dir, pos, false));
        }

        ItemStack remainderStack = fp.getItemInHand(InteractionHand.MAIN_HAND);

        if (source.<DispenserBlockEntity>getEntity().addItem(remainderStack) < 0)
            new DefaultDispenseItemBehavior().dispense(source, remainderStack);

        return v.consumesAction() ? InteractionResultHolder.sidedSuccess(stack, false) : InteractionResultHolder.fail(stack);
    }
}
