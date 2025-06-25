package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.violetmoon.quark.content.tools.item.PathfindersQuillItem;

@Mixin(PathfindersQuillItem.class)
public abstract class PathfinderItemMixin {
    @Shadow public abstract ResourceLocation getTarget(ItemStack stack);

    @Inject(method= "use", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$MakeACCaveMap(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (DMHUtils.alexCavesLoaded() && DMHConfig.COMMON.pathfinderQuillMakesCaveMaps.get()) {
            ItemStack stack = player.getItemInHand(hand);
            ItemStack mapStack = DMHACCompat.makeMapFromString(getTarget(stack));
            player.setItemInHand(hand, mapStack);

            Item item = DMHUtils.getPotentialItem(DMHUtils.Constants.ALEXS_CAVES, "cave_map");
            InteractionResultHolder<ItemStack> useResult = item.use(level, player, hand);

            cir.setReturnValue(useResult);
        }
    }

}
