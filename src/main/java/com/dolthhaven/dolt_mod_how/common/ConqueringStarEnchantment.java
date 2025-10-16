package com.dolthhaven.dolt_mod_how.common;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ConqueringStarEnchantment extends Enchantment {
    public ConqueringStarEnchantment() {
        super(Rarity.RARE,
                EnchantmentCategory.create("knife_config", knife -> knife instanceof KnifeItem && DMHConfig.COMMON.conqueringStar.get()),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof BackstabbingEnchantment) && super.checkCompatibility(enchantment);
    }
}
