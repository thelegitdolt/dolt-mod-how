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


}
