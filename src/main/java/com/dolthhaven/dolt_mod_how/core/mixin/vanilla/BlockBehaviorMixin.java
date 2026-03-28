package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.other.DoltModHowDataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {
    @Unique
    private static boolean DoltModHow$addEntity(BlockState state, LevelAccessor level, BlockPos pos, Entity entity, int compostLevel) {
        float chance = DoltModHowDataUtil.COMPOSTABLE_ENTITIES.getFloat(entity.getType());
        if ((compostLevel != 0 || !(chance >= 0f)) && level.getRandom().nextDouble() > chance) {
            return false;
        }

        int newLevel = compostLevel + 1;
        BlockState newState = state.setValue(ComposterBlock.LEVEL, newLevel);
        level.setBlock(pos, newState, 3);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(null, state));

        if (newLevel == 7) {
            level.scheduleTick(pos, newState.getBlock(), 20);
        }

        return true;
    }

    @Inject(method = "entityInside", at = @At("HEAD"))
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!state.is(Blocks.COMPOSTER)) return;

        int compostLevel = state.getValue(ComposterBlock.LEVEL);

        if (!level.isClientSide && compostLevel < 8 && DoltModHow$EntityInsideContent(entity, pos) &&
                DoltModHowDataUtil.COMPOSTABLE_ENTITIES.containsKey(entity.getType())) {
            if (compostLevel < 7) {
                boolean didFill = DoltModHow$addEntity(state, level, pos, entity, compostLevel);
                level.levelEvent(1500, pos, didFill ? 1 : 0);
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
