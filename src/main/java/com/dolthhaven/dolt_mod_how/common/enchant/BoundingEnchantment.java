package com.dolthhaven.dolt_mod_how.common.enchant;

import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.ModList;

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

    @Override
    public boolean isDiscoverable() {
        return ModList.get().isLoaded("map_atlases");
    }

    @Override
    public boolean isTradeable() {
        return ModList.get().isLoaded("map_atlases");
    }

}
