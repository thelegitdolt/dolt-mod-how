package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.violetmoon.quark.content.tools.item.PathfindersQuillItem;

@Mixin(PathfindersQuillItem.class)
public abstract class PathfinderItemMixin {
    @Shadow public abstract ResourceLocation getTarget(ItemStack stack);

    @Inject(method= "use", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$MakeACCaveMap(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack mapStack = makeMapFromString(getTarget(stack));
        player.setItemInHand(hand, mapStack);
        InteractionResultHolder<ItemStack> useResult = ACItemRegistry.CAVE_MAP.get().use(level, player, hand);

        cir.setReturnValue(useResult);
    }

    @Unique
    private static ItemStack makeMapFromString(ResourceLocation loc) {
        ItemStack map = new ItemStack(ACItemRegistry.CAVE_MAP.get());
        CompoundTag tag = new CompoundTag();

        tag.putString("BiomeTargetResourceKey", loc.toString());
        map.setTag(tag);
        return map;
    }
}
