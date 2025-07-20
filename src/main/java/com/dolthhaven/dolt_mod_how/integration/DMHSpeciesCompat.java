package com.dolthhaven.dolt_mod_how.integration;

import com.ninni.species.registry.SpeciesEnchantments;
import com.ninni.species.registry.SpeciesSoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;

public class DMHSpeciesCompat {

    public static SoundType opSound() {
        try {
            return SpeciesSoundEvents.ALPHACENE_GRASS;
        }
        catch (Exception e) {
            return SoundType.GRAVEL;
        }
    }

    public static int calculateCrankbowStack(ItemStack stack, int stackSize) {
        return stackSize * 2 + stack.getEnchantmentLevel(SpeciesEnchantments.CAPACITY.get()) * stackSize;
    }
}
