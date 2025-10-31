package com.dolthhaven.dolt_mod_how.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.violetmoon.quark.addons.oddities.module.BackpackModule;

@EmiEntrypoint
@SuppressWarnings("unused")
public class DMHEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipeHandler(BackpackModule.menyType, new BackpackScreenHandler());

        registry.removeEmiStacks(a -> {
            ItemStack stack = a.getItemStack();
            if (stack == null) return false;
            CompoundTag tag = stack.getTag();
            if (tag == null) return false;

            Potion potion = PotionUtils.getPotion(tag);
            String potionType = tag.getString("Potion");
            boolean subtle = tag.getBoolean("Subtle");

            if (potion == Potions.EMPTY || potion == Potions.WATER) return false;
            if (potionType.contains("long") || potionType.contains("strong") || subtle) return true;

            return false;
        });
    }
}
