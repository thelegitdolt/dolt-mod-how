package com.dolthhaven.dolt_mod_how.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RustScrapeProvider extends GlowParticle.ScrapeProvider {
    public RustScrapeProvider(SpriteSet sprite) {
        super(sprite);
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level, double xo, double yo, double zo, double xd, double yd, double zd) {
        GlowParticle particle = (GlowParticle) super.createParticle(type, level, xo, yo, zo, xd, yd, zd);
        if (level.random.nextBoolean()) {
            particle.setColor(156f / 256, 68f / 256, 28f / 256);
        } else {
            particle.setColor(84f / 256, 44f / 256, 32f / 256);
        }
        return particle;
    }
}
