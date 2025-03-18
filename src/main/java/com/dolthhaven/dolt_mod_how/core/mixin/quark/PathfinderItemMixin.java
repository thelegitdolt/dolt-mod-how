package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.github.alexmodguy.alexscaves.server.item.CaveMapItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.violetmoon.quark.content.tools.item.PathfindersQuillItem;

import java.util.Optional;

import static net.minecraftforge.registries.ForgeRegistries.BIOMES;

@Mixin(PathfindersQuillItem.class)
public abstract class PathfinderItemMixin {
    @Shadow public abstract ResourceLocation getTarget(ItemStack stack);

    @Inject(method= "use", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$MakeACCaveMap(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        Optional<ResourceKey<Biome>> biome = BIOMES.getResourceKey(BIOMES.getValue(getTarget(stack)));
        if (biome.isEmpty()) {
            cir.cancel();
            cir.setReturnValue(InteractionResultHolder.pass(stack));
            return;
        }


        Vec3 pos = player.position();
        level.playSound(player, pos.x, pos.y, pos.z, SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.5F, 1.0F);
        ItemStack mapStack = CaveMapItem.createMap(biome.get());
        player.setItemInHand(hand, mapStack);
        InteractionResultHolder<ItemStack> useResult = mapStack.getItem().use(level, player, hand);

        cir.cancel();
        cir.setReturnValue(useResult);
    }
}
