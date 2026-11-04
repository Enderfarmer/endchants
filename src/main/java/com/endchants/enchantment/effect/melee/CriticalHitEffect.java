package com.endchants.enchantment.effect.melee;

import com.mojang.serialization.MapCodec;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public record CriticalHitEffect() implements EnchantmentValueEffect {
    public static final MapCodec<CriticalHitEffect> CODEC = MapCodec.unit(CriticalHitEffect::new);

    @Override
    public float process(int i, RandomSource randomSource, float f) {
        return f * 3;
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> codec() {
        return CODEC;
    }
}
