package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.serialization.biome_modifiers.ModLoadedAddBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DMHBiomeModifierSerializers {
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DoltModHow.MOD_ID);

    public static RegistryObject<Codec<ModLoadedAddBiomeModifier>> MOD_LOADED_ADD = BIOME_MODIFIER_SERIALIZERS
            .register("mod_loaded_add", () -> ModLoadedAddBiomeModifier.CODEC);
}
