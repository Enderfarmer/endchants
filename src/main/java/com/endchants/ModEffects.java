package com.endchants;

import com.endchants.effects.Radiance;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final Radiance RADIANCE = new Radiance(MobEffectCategory.BENEFICIAL, 0xFFFF00);

    public static void registerEffects() {
        Registry.register(BuiltInRegistries.MOB_EFFECT, Endchants.id("radiance"), RADIANCE);
    }
}
