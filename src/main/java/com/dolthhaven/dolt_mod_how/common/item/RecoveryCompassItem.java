package com.dolthhaven.dolt_mod_how.common.item;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

public class RecoveryCompassItem {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String TAG_LOCKED_POS = "LockedPos";
    public static final String TAG_LOCKED_DIMENSION = "LockedDimension";
    public static final String TAG_LOCKED = "IsLocked";

    public static boolean isLocked(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && (tag.contains(TAG_LOCKED_DIMENSION) || tag.contains(TAG_LOCKED_POS));
    }

    public static Optional<ResourceKey<Level>> getLockedDimension(CompoundTag tag) {
        return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, tag.get(TAG_LOCKED_DIMENSION)).result();
    }

    @Nullable
    public static GlobalPos getLockedPosition(CompoundTag tag) {
        boolean hasLockedPos = tag.contains(TAG_LOCKED_POS);
        boolean hasLockedDimension = tag.contains(TAG_LOCKED_DIMENSION);
        if (hasLockedPos && hasLockedDimension) {
            Optional<ResourceKey<Level>> optional = getLockedDimension(tag);
            if (optional.isPresent()) {
                BlockPos blockpos = NbtUtils.readBlockPos(tag.getCompound(TAG_LOCKED_POS));
                return GlobalPos.of(optional.get(), blockpos);
            }
        }

        return null;
    }

    private static void lock(ResourceKey<Level> level, BlockPos pos, CompoundTag tag) {
        tag.put(TAG_LOCKED_POS, NbtUtils.writeBlockPos(pos));
        Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, level).resultOrPartial(LOGGER::error).ifPresent((dimTag) -> {
            tag.put(TAG_LOCKED_DIMENSION, dimTag);
        });
        tag.putBoolean(TAG_LOCKED, true);
    }

    private static void unlock(CompoundTag tag) {
        tag.remove(TAG_LOCKED_POS);
        tag.remove(TAG_LOCKED_DIMENSION);
        tag.remove(TAG_LOCKED);
    }

    public static InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isLocked(stack)) {
            unlock(stack.getTag());
            player.playSound(SoundEvents.GRINDSTONE_USE, 0.5F, 0.5F);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        } else if (player.getLastDeathLocation().isPresent()) {
            player.playSound(SoundEvents.LODESTONE_COMPASS_LOCK);
            boolean replaceItem = !player.getAbilities().instabuild && stack.getCount() == 1;

            if (replaceItem) {
                lock(level.dimension(), player.getLastDeathLocation().get().pos(), stack.getOrCreateTag());
            } else {
                ItemStack newStack = new ItemStack(Items.RECOVERY_COMPASS);
                CompoundTag newTag = stack.hasTag() ? stack.getTag().copy() : new CompoundTag();
                newStack.setTag(newTag);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                lock(level.dimension(), player.getLastDeathLocation().get().pos(), newTag);
                if (!player.getInventory().add(newStack)) {
                    player.drop(newStack, false);
                }
            }

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        return Items.RECOVERY_COMPASS.use(level, player, hand);
    }
}
