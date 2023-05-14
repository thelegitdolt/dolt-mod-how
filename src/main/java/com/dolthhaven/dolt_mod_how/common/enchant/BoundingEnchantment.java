package com.dolthhaven.dolt_mod_how.common.enchant;

import com.dolthhaven.dolt_mod_how.core.DMHEnchants;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class BoundingEnchantment extends Enchantment {
    public BoundingEnchantment(Rarity rare, EquipmentSlot... slots) {
        super(rare, DMHEnchants.ATLAS, slots);
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 25;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

}
