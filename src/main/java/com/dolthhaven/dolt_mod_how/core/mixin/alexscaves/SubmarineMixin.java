package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import com.dolthhaven.dolt_mod_how.integration.DMHACCCCompat;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(SubmarineEntity.class)
public abstract class SubmarineMixin {
    @Shadow public abstract int getOxidizationLevel();

    @Shadow public abstract boolean isWaxed();

    @ModifyArg(method = "interact", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/item/SubmarineEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private SoundEvent DoltModHow$SameSoundEffectPlease(SoundEvent soundEvent) {
        if (soundEvent == ACSoundRegistry.SUBMARINE_REPAIR.get()) {
            SoundEvent newEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(DMHUtils.Constants.CAVERNS_AND_CHASMS, "entity.copper_golem.repair"));
            soundEvent = newEvent == null ? soundEvent : newEvent;
        }
        return soundEvent;
    }

    @Definition(id = "itemStack", local = @Local(type = ItemStack.class))
    @Definition(id = "is", method = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    @Definition(id = "COPPER_INGOT", field = "Lnet/minecraft/world/item/Items;COPPER_INGOT:Lnet/minecraft/world/item/Item;")
    @Expression("itemStack.is(COPPER_INGOT)")
    @WrapOperation(method = "interact", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean DoltModHow$SameSoundEffectPlease(ItemStack instance, Item item, Operation<Boolean> original) {
        if (DMHUtils.cavernsChasmsLoaded()) return instance.is(DMHTags.COPPER_INGOTS);
        return original.call(instance, item);
    }

    @ModifyArg(method = "hurt", at = @At(value = "INVOKE", target = "Lcom/github/alexmodguy/alexscaves/server/entity/item/SubmarineEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private ItemLike item(ItemLike par1) {
        if (DMHUtils.cavernsChasmsLoaded()) {
            int oxy = this.getOxidizationLevel();
            int waxed = this.isWaxed() ? 4 : 0;
            return DMHCCCompat.COPPER_INGOTS.get().get(oxy + waxed);
        }
        return par1;
    }
}
