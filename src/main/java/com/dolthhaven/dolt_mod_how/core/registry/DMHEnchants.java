package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.enchant.BoundingEnchantment;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pepjebs.mapatlases.MapAtlasesMod;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHEnchants {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DoltModHow.MOD_ID);

    public static final EnchantmentCategory ATLAS = EnchantmentCategory.create("atlas", item -> item == MapAtlasesMod.MAP_ATLAS);

    public static final RegistryObject<Enchantment> BOUNDING = ENCHANTMENTS.register("bounding", () -> new BoundingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
}
