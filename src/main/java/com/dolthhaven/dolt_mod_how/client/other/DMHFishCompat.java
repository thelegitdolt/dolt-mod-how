package com.dolthhaven.dolt_mod_how.client.other;

import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import com.github.ilja615.fish_in_planks.FishParticle;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

public class DMHFishCompat {
    public static void register(RegisterParticleProvidersEvent event) {
        event.register(DMHParticles.LANTERNFISH_PARTICLE.get(), FishParticle.Factory::new);
    }

}
