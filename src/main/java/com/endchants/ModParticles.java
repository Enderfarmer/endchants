package com.endchants;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class ModParticles {
    private static SimpleParticleType register(String name) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                ResourceKey.create(Registries.PARTICLE_TYPE, Endchants.id(name)), FabricParticleTypes.simple());
    }

    public static final SimpleParticleType POISON_CLOUD = register("poison_cloud");
    public static final SimpleParticleType CRITICAL_HIT = register("critical_hit");

    public static void init() {
    }
}
