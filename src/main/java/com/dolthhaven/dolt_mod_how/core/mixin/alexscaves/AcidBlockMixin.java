package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.github.alexmodguy.alexscaves.server.block.AcidBlock;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.level.block.WeatheringCopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;

@Mixin(value = AcidBlock.class, remap = false)
public class AcidBlockMixin {
    @WrapWithCondition(method = "lambda$initCorrosion$1(Ljava/util/HashMap;)V", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K, V> boolean DoltModHow$NoAutomaticallyOxidatingCopperPlease(HashMap<K, V> instance, K key, V value) {
        return !DMHConfig.COMMON.acidCorrodesCopper.get() || !(key instanceof WeatheringCopper);
    }
}
