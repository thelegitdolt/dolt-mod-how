package com.dolthhaven.dolt_mod_how.core.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireAspectEnchantment.class)
public class FireAspectEnchantmentMixin extends Enchantment {
    protected FireAspectEnchantmentMixin(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }
//
//    @Override
//    public boolean canEnchant(ItemStack p_44642_) {
//        return p_44642_.getItem() instanceof AxeItem ? true : super.canEnchant(p_44642_);
//    }
}
