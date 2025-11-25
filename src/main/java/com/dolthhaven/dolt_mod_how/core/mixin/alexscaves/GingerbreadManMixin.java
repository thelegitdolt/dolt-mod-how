package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.entity.living.GingerbreadManEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GingerbreadManEntity.class)
public class GingerbreadManMixin {
    @WrapOperation(method = "onLoseArm",
            at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack noMoreGingerbreadCrumbs(ItemLike item, Operation<ItemStack> original) {
        cookie: if (ModList.get().isLoaded(DMHUtils.Constants.WINDSWEPT)) {
            Item cookie = ForgeRegistries.ITEMS.getValue(DMHUtils.Constants.GINGERBREAD_COOKIE);
            if (cookie == null) break cookie;

            return original.call(cookie);
        }

        return original.call(item);
    }
}
