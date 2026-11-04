package com.endchants.enchantment.effect;

import com.endchants.ModSoundEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record RepellingEffect(float repellingStrength) implements EntityEffectBase {
    public static final MapCodec<RepellingEffect> CODEC = Codec.FLOAT.fieldOf("repelling_strength")
            .xmap(RepellingEffect::new, RepellingEffect::repellingStrength);

    @Override
    public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
        if (Math.random() < 0.5) {
            Vec3 direction = ownerPosition.subtract(victim.position()).normalize().reverse();
            direction.add(0, 0.5, 0);
            double strength = repellingStrength * i;
            victim.setDeltaMovement(victim.getDeltaMovement().add(direction.scale(strength)));
            victim.level().playSound(null, victim.blockPosition(), ModSoundEvents.REPELLING, SoundSource.PLAYERS);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}
