package com.dolthhaven.dolt_mod_how.common.item;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DMHGoldenBucketItem extends GoldenBucketItem {
    public static Map<ResourceLocation, Item> FLUID_TO_BUCKET_MAP = null;


    public DMHGoldenBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        int bucketLevel = tag.getInt("FluidLevel");
        BlockHitResult result = getPlayerPOVHitResult(level, player, this.getFluid() != Fluids.EMPTY && bucketLevel >= 2 || player.isCrouching() && this.getFluid() != Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.NONE : net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY);
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onBucketUse(player, level, stack, result);
        if (ret != null) {
            return ret;
        } else if (result.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else if (result.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            BlockPos pos = result.getBlockPos();
            Direction direction = result.getDirection();
            BlockPos sourcePos = pos.relative(direction);
            if (level.mayInteract(player, pos) && player.mayUseItemAt(sourcePos, direction, stack)) {
                BlockState sourceState = level.getBlockState(pos);
                if (this.getFluid() != Fluids.EMPTY && (bucketLevel >= 2 || sourceState.getFluidState().getType() != this.getFluid())) {
                    result = getPlayerPOVHitResult(level, player, net.minecraft.world.level.ClipContext.Fluid.NONE);
                    if (result.getType() == HitResult.Type.MISS) {
                        return InteractionResultHolder.pass(stack);
                    } else if (result.getType() != HitResult.Type.BLOCK) {
                        return InteractionResultHolder.pass(stack);
                    } else {
                        pos = result.getBlockPos();
                        direction = result.getDirection();
                        sourcePos = pos.relative(direction);
                        BlockState state = level.getBlockState(pos);
                        BlockPos newPos = !this.canBlockContainFluid(level, pos, state) && (this.getFluid() == Fluids.EMPTY || bucketLevel >= 2) ? sourcePos : pos;
                        if (this.emptyContents(player, level, newPos, result)) {
                            this.checkExtraContent(level, stack, newPos);
                            if (player instanceof ServerPlayer) {
                                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, newPos, stack);
                            }

                            player.awardStat(Stats.ITEM_USED.get(this));
                            return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(stack, player), level.isClientSide());
                        } else {
                            return InteractionResultHolder.fail(stack);
                        }
                    }
                } else {
                    Block newPos = sourceState.getBlock();
                    if (newPos instanceof BucketPickup bucketPickup) {
                        bucketPickup.pickupBlock(level, pos, sourceState);
                        Fluid fluid = sourceState.getFluidState().getType();

                        ItemStack newBucket = ItemStack.EMPTY;
                        if (fluid != Fluids.EMPTY && getFilledRealBucket(sourceState) != null) {
                            newBucket = ItemUtils.createFilledResult(stack, player, getFilledRealBucket(sourceState));
                            if (this.getFluid() != Fluids.EMPTY) {
                                setFluidLevel(newBucket, bucketLevel + 1);
                            }
                        }

                        if (!newBucket.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketPickup.getPickupSound(sourceState).ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                            if (!level.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, newBucket);
                            }

                            return InteractionResultHolder.sidedSuccess(newBucket, level.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(stack);
                }
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult result) {
        if (!(this.getFluid() instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            boolean replaceable = state.canBeReplaced(this.getFluid());
            if (state.isAir() || replaceable || block instanceof LiquidBlockContainer liquidContainer && liquidContainer.canPlaceLiquid(level, pos, state, this.getFluid())) {
                if (this.getFluid().getFluidType().isVaporizedOnPlacement(level, pos, new FluidStack(this.getFluid(), 0))) {
                    this.getFluid().getFluidType().onVaporize(player, level, pos, new FluidStack(this.getFluid(), 0));
                    return true;
                } else if (block instanceof LiquidBlockContainer cont && cont.canPlaceLiquid(level, pos, state, this.getFluid())) {
                    cont.placeLiquid(level, pos, state, ((FlowingFluid) this.getFluid()).getSource(false));
                    this.playEmptySound(player, level, pos);
                    return true;
                } else {
                    if (!level.isClientSide && replaceable && !state.liquid()) {
                        level.destroyBlock(pos, true);
                    }

                    if (!level.setBlock(pos, this.getFluid().defaultFluidState().createLegacyBlock(), 11) && !state.getFluidState().isSource()) {
                        return false;
                    } else {
                        this.playEmptySound(player, level, pos);
                        return true;
                    }
                }
            } else {
                return result != null && this.emptyContents(player, level, result.getBlockPos().relative(result.getDirection()), null);
            }
        }
    }


    private static void initBucketMap() {
        if (FLUID_TO_BUCKET_MAP == null) {
            FLUID_TO_BUCKET_MAP = Util.make(new HashMap<>(), map -> {
                map.put(DMHUtils.Constants.ACID, DMHItems.GOLDEN_ACID_BUCKET.get());
                map.put(DMHUtils.Constants.PURPLE_SODA, DMHItems.GOLDEN_PURPLE_SODA_BUCKET.get());
                map.put(DMHUtils.Constants.MOLTEN_LEAD, DMHItems.GOLDEN_MOLTEN_LEAD_BUCKET.get());
            });
        }
    }

    public static ItemStack getFilledRealBucket(BlockState state) {
        initBucketMap();
        ResourceLocation loc = DMHUtils.getFluidID(state.getFluidState().getType());
        Item item = FLUID_TO_BUCKET_MAP.get(loc);
        if (item != null) {
            return new ItemStack(item);
        }

        return null;
    }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.getFluid());
    }

}
