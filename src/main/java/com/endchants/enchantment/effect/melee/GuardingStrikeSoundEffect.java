package com.endchants.enchantment.effect.melee;

import com.endchants.ModSoundEvents;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public record GuardingStrikeSoundEffect() implements EntityEffectBase {
    public static final MapCodec<GuardingStrikeSoundEffect> CODEC = MapCodec.unit(GuardingStrikeSoundEffect::new);

    @Override
    public void affectOwnerOnKill(int i, LivingEntity owner) {
        if (i == 1)
            owner.level().playSound(null, owner.blockPosition(), ModSoundEvents.GUARDING_STRIKE_1,
                    SoundSource.PLAYERS);
        if (i == 2)
            owner.level().playSound(null, owner.blockPosition(), ModSoundEvents.GUARDING_STRIKE_2,
                    SoundSource.PLAYERS);
        if (i == 3)
            owner.level().playSound(null, owner.blockPosition(), ModSoundEvents.GUARDING_STRIKE_3,
                    SoundSource.PLAYERS);

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
