package com.dolthhaven.dolt_mod_how.core.mixin;

import com.dolthhaven.dolt_mod_how.core.DMHEnchants;
import com.mojang.authlib.GameProfile;
import lilypuree.mapatlases.MapAtlasesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.addons.oddities.module.BackpackModule;

import java.util.List;

@Mixin(ServerPlayer.class)
public abstract class SeverPlayerMixin extends Player {
    public SeverPlayerMixin(Level p_219727_, BlockPos p_219728_, float p_219729_, GameProfile p_219730_, @Nullable ProfilePublicKey p_219731_) {
        super(p_219727_, p_219728_, p_219729_, p_219730_, p_219731_);
    }

    @Shadow public abstract ServerLevel getLevel();


    @Inject(method = "Lnet/minecraft/server/level/ServerPlayer;restoreFrom(Lnet/minecraft/server/level/ServerPlayer;Z)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getShoulderEntityLeft()Lnet/minecraft/nbt/CompoundTag;"))
    private void DoltModHow$BoundingEnchantWorks(ServerPlayer player, boolean what, CallbackInfo ci) {
        if (!(this.getLevel().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || !player.isSpectator())) {
            List<ItemStack> allItems =  this.getInventory().items;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(BackpackModule.backpack)) {
                ItemStack backpack = player.getItemBySlot(EquipmentSlot.CHEST);
                LazyOptional<IItemHandler> handlerOpt = backpack.getCapability(ForgeCapabilities.ITEM_HANDLER, null);
                if (handlerOpt.isPresent()) {
                    IItemHandler handler = handlerOpt.orElse(new ItemStackHandler());

                    for(int i = 0; i < handler.getSlots(); ++i) {
                        if (!handler.getStackInSlot(i).isEmpty()) {
                            allItems.add(handler.getStackInSlot(i));
                        }
                    }

                }
            }
            for (ItemStack stack : allItems.stream().filter(i ->
                    i.is(MapAtlasesMod.MAP_ATLAS.get()) && EnchantmentHelper.getEnchantmentLevel(DMHEnchants.BOUNDING.get(), player) == 1).toList()) {
                if (player.getInventory().add(stack)) {
                    player.drop(stack, true);
                }

            }
        }
    }
}
