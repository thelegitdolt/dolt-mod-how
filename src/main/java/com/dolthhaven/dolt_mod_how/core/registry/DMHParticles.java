package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.client.particle.RustScrapeProvider;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DMHParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DoltModHow.MOD_ID);

    public static final RegistryObject<SimpleParticleType> POISON_HEART = PARTICLES.register("poison_heart", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RUST_SCRAPE = PARTICLES.register("rust_scrape", () -> new SimpleParticleType(false));

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(POISON_HEART.get(), HeartParticle.Provider::new);
        event.registerSpriteSet(RUST_SCRAPE.get(), RustScrapeProvider::new);
    }
}

