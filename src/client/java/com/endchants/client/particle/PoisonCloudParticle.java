package com.endchants.client.particle;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class PoisonCloudParticle extends SimpleAnimatedParticle {

    protected PoisonCloudParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
            double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet, 0.0125F);

        // Configure physics / motion spread
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        // Scale and lifespan
        this.quadSize *= 1.5F; // Make the cloud larger
        this.lifetime = 40; // Lives for 40 ticks (2 seconds)

        // Assign the sprite from the registered sprite set safely
        this.setSpriteFromAge(spriteSet);
    }

    // Provider class to hook into Fabric's ParticleFactoryRegistry
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType arg0, ClientLevel arg1, double arg2, double arg3,
                double arg4, double arg5, double arg6, double arg7, RandomSource arg8) {
            return new PoisonCloudParticle(arg1, arg2, arg3, arg4, arg5, arg6, arg7, this.sprites);
        }
    }
}