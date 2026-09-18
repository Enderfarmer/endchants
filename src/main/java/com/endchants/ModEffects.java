package com.endchants;

import com.endchants.effects.Radiance;
import com.endchants.effects.Rampaging;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final Holder<MobEffect> RADIANCE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            Endchants.id("radiance"),
            new Radiance(MobEffectCategory.BENEFICIAL, 0xFFFF00));
    public static final Holder<MobEffect> RAMPAGING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            Endchants.id("rampaging"),
            new Rampaging());

    public static void registerEffects() {

    }
}
