package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.other.DoltModHowDataUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin extends Block implements WorldlyContainerHolder {
    @Shadow @Final public static IntegerProperty LEVEL;

    public ComposterBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Unique
    private static BlockState DoltModHow$addEntity(BlockState state, LevelAccessor level, BlockPos pos, Entity entity, int compostLevel) {
        float chance = DoltModHowDataUtil.COMPOSTABLE_ENTITIES.getFloat(entity.getType());
        if ((compostLevel != 0 || !(chance >= 0f)) && level.getRandom().nextDouble() > chance) {
            return state;
        }

        int newLevel = compostLevel + 1;
        BlockState newState = state.setValue(LEVEL, newLevel);
        level.setBlock(pos, newState, 3);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(null, state));

        if (newLevel == 7) {
            level.scheduleTick(pos, newState.getBlock(), 20);
        }

        return newState;
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        int compostLevel = state.getValue(LEVEL);

        if (!level.isClientSide && compostLevel < 8 && DoltModHow$EntityInsideContent(entity, pos) &&
                DoltModHowDataUtil.COMPOSTABLE_ENTITIES.containsKey(entity.getType())) {
            if (compostLevel < 7) {
                DoltModHow$addEntity(state, level, pos, entity, compostLevel);
            }
            entity.discard();
        }
    }

    @Unique
    private static boolean DoltModHow$EntityInsideContent(Entity entity, BlockPos pos) {
        float yThresh = (float) pos.getY() + (6.0F  / 16.0F);

        return entity.getY() < yThresh && entity.getBoundingBox().maxY > (double) pos.getY() + 0.25;
    }

}
