package com.dolthhaven.dolt_mod_how.integration.emi;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.violetmoon.quark.addons.oddities.module.BackpackModule;

import java.util.Locale;
import java.util.Map;

@EmiEntrypoint
@SuppressWarnings("unused")
public class DMHEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipeHandler(BackpackModule.menyType, new BackpackScreenHandler());
//        if (DMHConfig.CLIENT.hidePotions.get()) {
//            registry.removeEmiStacks(a -> {
//                ItemStack stack = a.getItemStack();
//                if (stack.isEmpty()) return false;
//                CompoundTag tag = stack.getTag();
//                if (tag == null) return false;
//
//                Potion potion = PotionUtils.getPotion(tag);
//
//                // hides all subtle potions that aren't normal water potions
//                boolean subtle = tag.getBoolean("Subtle");
//                if (subtle && potion == Potions.WATER && stack.getItem() != Items.POTION) return true;
//
//                // do not hide non-potions and water potions
//                if (potion == Potions.EMPTY || potion == Potions.WATER) return false;
//
//                    // hide all non-water variant potions
//                else if (stack.getItem() != Items.POTION) return true;
//
//                String potionType = tag.getString("Potion").toLowerCase(Locale.ROOT);
//
//                // hides all normal potions that are long and strong and subtle variants
//                if (potionType.contains("long") || potionType.contains("strong") || subtle) return true;
//
//                return false;
//            });
//        }

        if (DMHConfig.CLIENT.hideEnchants.get()) {
            registry.removeEmiStacks(a -> {
                ItemStack stack = a.getItemStack();
                if (stack.isEmpty() || stack.getItem() != Items.ENCHANTED_BOOK) return false;

                Map.Entry<Enchantment, Integer> enchant = EnchantmentHelper.getEnchantments(stack).entrySet().iterator().next();
                return enchant.getValue() < enchant.getKey().getMaxLevel();
            });
        }
    }
}
