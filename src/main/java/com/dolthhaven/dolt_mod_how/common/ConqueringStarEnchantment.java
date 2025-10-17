package com.dolthhaven.dolt_mod_how.common;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ConqueringStarEnchantment extends Enchantment {
    public ConqueringStarEnchantment() {
        super(Rarity.RARE,
                EnchantmentCategory.create("knife_config", knife -> isEnabled() && knife instanceof KnifeItem),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return isEnabled() && !(enchantment instanceof BackstabbingEnchantment) && super.checkCompatibility(enchantment);
    }

    public static boolean isEnabled() {
        return ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT) && DMHConfig.COMMON.conqueringStar.get();
    }

    public boolean isTradeable() {
        return isEnabled();
    }

    public boolean isAllowedOnBooks() {
        return isEnabled();
    }

    public boolean isDiscoverable() {
        return isEnabled();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return isEnabled() && super.canApplyAtEnchantingTable(stack);
    }

}
