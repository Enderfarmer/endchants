package com.endchants.client.particle;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class CriticalHitParticle extends SingleQuadParticle {
    protected CriticalHitParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
            double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet.first());
        this.lifetime = 20;
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType arg0, ClientLevel arg1, double arg2, double arg3,
                double arg4, double arg5, double arg6, double arg7, RandomSource arg8) {
            return new CriticalHitParticle(arg1, arg2, arg3, arg4, arg5, arg6, arg7, this.sprites);
        }
    }
}
