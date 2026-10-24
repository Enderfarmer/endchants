package com.endchants.enchantment.effect.armor;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public record RushEffect() implements EntityEffectBase {
    public static final MapCodec<RushEffect> CODEC = MapCodec.unit(RushEffect::new);

    @Override
    public void affectOwner(int i, LivingEntity owner) {
        owner.addEffect(new MobEffectInstance(MobEffects.SPEED, i * 30, 1));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
