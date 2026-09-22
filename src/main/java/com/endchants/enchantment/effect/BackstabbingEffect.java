package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public record BackstabbingEffect(LevelBasedValue coef) implements EnchantmentValueEffect {
    public static final MapCodec<BackstabbingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("coef")
            .xmap(BackstabbingEffect::new, BackstabbingEffect::coef);

    @Override
    public float process(int i, RandomSource randomSource, float f) {
        return f * coef.calculate(i);
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> codec() {
        return CODEC;
    }

}
