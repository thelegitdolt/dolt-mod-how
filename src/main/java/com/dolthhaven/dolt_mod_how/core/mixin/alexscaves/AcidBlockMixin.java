package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.github.alexmodguy.alexscaves.server.block.AcidBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AcidBlock.class)
public class AcidBlockMixin {
    @Shadow(remap = false) private static Map<Block, Block> CORRODES_INTERACTIONS;

    @Inject(method = "initCorrosion", at = @At("TAIL"), remap = false)
    private static void DoltModHow$NoAutomaticallyOxidatingCopperPlease(CallbackInfo ci) {
        if (!DMHConfig.COMMON.acidCorrodesCopper.get()) return;

        CORRODES_INTERACTIONS.entrySet().removeIf(entry -> {
            ResourceLocation location = ForgeRegistries.BLOCKS.getKey(entry.getKey());
            if (location == null) return false;

            return location.getPath().contains("copper");
        });
    }
}
