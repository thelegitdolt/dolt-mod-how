package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.enchant.BoundingEnchantment;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHEnchants {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DoltModHow.MOD_ID);

    public static final EnchantmentCategory ATLAS = EnchantmentCategory.create("atlas", item -> {
        ResourceLocation res = ForgeRegistries.ITEMS.getKey(item);
        if (res == null)
            return false;
        return res.getNamespace().equals("map_atlases") && res.getPath().equals("atlas");
    } );

    public static final RegistryObject<Enchantment> BOUNDING = ENCHANTMENTS.register("bounding", () -> new BoundingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
}
