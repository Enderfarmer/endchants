package com.endchants.enchantment.effect.ranged;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record TempoTheftEffect() implements EntityEffectBase {
    public static final MapCodec<TempoTheftEffect> CODEC = MapCodec.unit(TempoTheftEffect::new);

    @Override
    public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
        victim.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 80, i - 1));
    }

    @Override
    public void affectOwner(int i, LivingEntity owner) {
        owner.addEffect(new MobEffectInstance(MobEffects.SPEED, 80, i - 1));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
