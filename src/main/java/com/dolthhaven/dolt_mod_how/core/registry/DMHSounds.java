package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHSounds {
    public static final SoundSubRegistryHelper HELPER = DoltModHow.REGISTRY_HELPER.getSoundSubHelper();

    public static final RegistryObject<SoundEvent> POULPO = HELPER.createSoundEvent("item.poulpo.poulpoes");

    public static final RegistryObject<SoundEvent> TANKARD_SHOOTS = HELPER.createSoundEvent("item.dolt_mod_how.tankard_shoots");
    public static final RegistryObject<SoundEvent> TANKARD_HIT = HELPER.createSoundEvent("item.dolt_mod_how.tankard_hit");
}
