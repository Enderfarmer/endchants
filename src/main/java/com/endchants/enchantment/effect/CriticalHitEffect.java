package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public record CriticalHitEffect() implements EnchantmentValueEffect {
    public static final MapCodec<CriticalHitEffect> CODEC = MapCodec.unit(CriticalHitEffect::new);

    @Override
    public float process(int i, RandomSource randomSource, float f) {
        if (randomSource.nextDouble() < (i * 0.05 + 0.1)) {
            return f * 3;
        }
        return f;
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> codec() {
        return CODEC;
    }
}
