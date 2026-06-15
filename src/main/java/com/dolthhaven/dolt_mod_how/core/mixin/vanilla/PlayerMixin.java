package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.registry.DMHMobEffects;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The methods "startClinging", "getPositionUnderneath", "isUpsideDown" has code copied verbatim from Alex's Mobs code as shown which is available below:
 * <a href="https://github.com/AlexModGuy/AlexsMobs/blob/09755dade2cfbdf14839e026d3af446f9d3ff843/src/main/java/com/github/alexthe666/alexsmobs/effect/EffectClinging.java#L11">...</a>
 * As accessed by the project's GNU Public license.
 * You can download Alex's Mobs here: <a href="http://modrinth.com/mod/alexs-mobs">...</a>
 * To avoid overlap, features that make use of this code are turned off by default and must be enabled in the config.
 */
@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity{
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$RapaciousAlwaysEat(boolean p_36392_, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(DMHMobEffects.RAPACITY.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void DoltModHow$ClingToCeiling(CallbackInfo ci) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) return;
        if (!DMHConfig.COMMON.pouncingLetsYouClingToCeilings.get()) return;

        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(DMHUtils.Constants.POUNCING);
        if (effect != null && this.getEffect(effect) != null) {
            startClinging((Player) (Object) this);
        }
    }

    @Unique
    private static void startClinging(Player entity) {
        entity.refreshDimensions();
        entity.setNoGravity(false);

        if (isUpsideDown(entity)) {
            entity.fallDistance = 0;
            if (!entity.isShiftKeyDown()) {
                if (!entity.horizontalCollision) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.3F, 0));
                }
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.998F, 1F, 0.998F));
            }
        }
    }

    @Unique
    private static BlockPos getPositionUnderneath(Entity e) {
        return new BlockPos((int) e.getX(), (int) (e.getBoundingBox().maxY + 1.51F), (int) e.getZ());
    }

    @Unique
    private static boolean isUpsideDown(LivingEntity entity){
        BlockPos pos = getPositionUnderneath(entity);
        BlockState ground = entity.level().getBlockState(pos);
        return (entity.verticalCollision || ground.isFaceSturdy(entity.level(), pos, Direction.DOWN)) && !entity.onGround();
    }
}
