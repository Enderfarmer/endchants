package com.endchants.enchantment.effect.armor;

import com.endchants.ModSoundEvents;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public record RushEffect() implements EntityEffectBase {
    public static final MapCodec<RushEffect> CODEC = MapCodec.unit(RushEffect::new);

    @Override
    public void affectOwner(int i, LivingEntity owner) {
        owner.addEffect(new MobEffectInstance(MobEffects.SPEED, i * 30, 1));
        if (i == 1) {
            owner.level().playSound(null, owner.blockPosition(), ModSoundEvents.RUSH_1, SoundSource.PLAYERS);
        } else if (i == 2) {
            owner.level().playSound(null, owner.blockPosition(), ModSoundEvents.RUSH_2, SoundSource.PLAYERS);
        } else if (i == 3) {
            owner.level().playSound(null, owner.blockPosition(), ModSoundEvents.RUSH_3, SoundSource.PLAYERS);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
