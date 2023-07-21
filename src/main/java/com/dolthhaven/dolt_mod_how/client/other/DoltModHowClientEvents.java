package com.dolthhaven.dolt_mod_how.client.other;

import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import com.github.ilja615.fish_in_planks.FishParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DoltModHowClientEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(DMHParticles.LANTERNFISH_PARTICLE.get(), FishParticle.Factory::new);
    }
}
