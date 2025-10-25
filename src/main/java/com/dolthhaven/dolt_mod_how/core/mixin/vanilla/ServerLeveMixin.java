package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLeveMixin extends Level {

    protected ServerLeveMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Definition(id = "THUNDER_DELAY", field = "Lnet/minecraft/server/level/ServerLevel;THUNDER_DELAY:Lnet/minecraft/util/valueproviders/IntProvider;")
    @Definition(id = "sample", method = "Lnet/minecraft/util/valueproviders/IntProvider;sample(Lnet/minecraft/util/RandomSource;)I")
    @Expression("THUNDER_DELAY.sample(?)")
    @ModifyExpressionValue(method = "advanceWeatherCycle", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int DoltModHow(int thing){
        double multiplier = DMHConfig.COMMON.thunderstormMultiplier.get();
        if (multiplier == 1) return thing;
        return Mth.floor(thing * multiplier);
    }
}
