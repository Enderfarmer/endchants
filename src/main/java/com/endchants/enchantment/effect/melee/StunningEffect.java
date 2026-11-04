package com.endchants.enchantment.effect.melee;

import com.endchants.ModEffects;
import com.endchants.ModSoundEvents;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record StunningEffect(LevelBasedValue chance) implements EntityEffectBase {
    public static final MapCodec<StunningEffect> CODEC = LevelBasedValue.CODEC.fieldOf("chance")
            .xmap(StunningEffect::new, StunningEffect::chance);

    @Override
    public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
        if (Math.random() < chance.calculate(i)) {
            victim.addEffect(new MobEffectInstance(ModEffects.STUNNED, (int) 60, 0));
            victim.level().playSound(null, victim.blockPosition(), ModSoundEvents.STUNNING, SoundSource.PLAYERS);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
