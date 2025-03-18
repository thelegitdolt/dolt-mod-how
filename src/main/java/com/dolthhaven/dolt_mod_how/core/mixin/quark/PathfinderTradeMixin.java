package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.item.CaveMapItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static net.minecraftforge.registries.ForgeRegistries.BIOMES;

@Mixin(targets = "org.violetmoon.quark.content.tools.module.PathfinderMapsModule.PathfinderQuillTrade")
public abstract class PathfinderTradeMixin {
    @WrapOperation(method = "getOffer", at = @At(value = "INVOKE", remap = false,
            target = "Lorg/violetmoon/quark/content/tools/item/PathfindersQuillItem;forBiome(Ljava/lang/String;I)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack DoltModHow$AlexsCavesMapTime(String biome, int color, Operation<ItemStack> original) {
        if (DMHUtils.alexCavesLoaded()) {
            Biome biomesValue = BIOMES.getValue(new ResourceLocation(biome));
            if (biomesValue == null || BIOMES.getResourceKey(biomesValue).isEmpty()) {
                return ItemStack.EMPTY;
            }
            return CaveMapItem.createMap(BIOMES.getResourceKey(biomesValue).get());
        }
        return ItemStack.EMPTY;
    }
}
