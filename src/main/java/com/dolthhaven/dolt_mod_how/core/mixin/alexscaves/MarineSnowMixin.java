package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.item.MarineSnowItem;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Objects;

@Pseudo
@Mixin(MarineSnowItem.class)
public class MarineSnowMixin {
    @Inject(method = "lambda$initGrowth$0", at = @At(value = "HEAD"))
    private static  <K, V> void changeGlowInteractions(HashMap<K, V> map, CallbackInfo ci) {
        ForgeRegistries.BLOCKS.getKeys().stream().filter(rl -> rl.getPath().contains("coral")
                        && (rl.getPath().contains("dead") || rl.getPath().contains("elder_prismarine")) && !rl.getPath().contains("coralstone"))
                .forEach(location -> {
                    Block dead = DMHUtils.getPotentialBlock(location);
                    Block alive = DMHUtils.getPotentialBlock(location.withPath(path -> path.replace("dead_", "").replace("elder_", "")));
                    if (dead != null && alive != null) ((HashMap<Block, Block>) map).put(dead, alive);
                });
    }

    @WrapWithCondition(method = "lambda$initGrowth$0", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static  <K, V> boolean changeGlowInteractions(HashMap<K, V> instance, K key, V value) {
        if (key instanceof Block block) {
            ResourceLocation location = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
            if (location.getPath().endsWith("coral") || location.getPath().endsWith("coral_fan")) return false;
        }
        return true;
    }

    @Inject(method = "lambda$initGrowth$1", at = @At(value = "HEAD"))
    private static  <K, V> void changeDupeInteraction(HashMap<K, V> map, CallbackInfo ci) {
        ((HashMap<Block, ItemStack>)map).put(ACBlockRegistry.PING_PONG_SPONGE.get(), new ItemStack(ACBlockRegistry.PING_PONG_SPONGE.get().asItem()));
    }

    @WrapWithCondition(method = "lambda$initGrowth$1", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K, V> boolean changeDupeInteractions(HashMap<K, V> instance, K key, V value) {
        if (key instanceof Block block) {
            return ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(DMHUtils.Constants.ALEXS_CAVES);
        } return true;
    }
}
