package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.ForgeRegistries;

public class DMHSpeciesCompat {
    private static final String GRASS = "block.alphacene_grass.";

    public static final SoundType ALPHACENE_GRASS = new SoundType(0.8F, 1,
            opSound(GRASS + "break"),
            opSound(GRASS + "step"),
            opSound(GRASS + "place"),
            opSound(GRASS + "hit"),
            opSound(GRASS + "fall")
    );

    private static SoundEvent opSound(String namespace) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(DMHUtils.Constants.SPECIES, namespace));
    }
}
