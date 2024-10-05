package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoltModHowParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DoltModHow.MOD_ID);

    public static final RegistryObject<SimpleParticleType> POISON_HEART = PARTICLE_TYPES.register("poison_heart", () -> new SimpleParticleType(false));


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
//        event.registerSpecial(POISON_HEART.get(), HeartParticle.Provider::new);
    }
}
