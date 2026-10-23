package com.endchants.enchantment.effect.ranged;

import com.endchants.ModEffects;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record WildRageEffect(LevelBasedValue chance) implements EntityEffectBase {
    public static final MapCodec<WildRageEffect> CODEC = LevelBasedValue.CODEC.fieldOf("chance")
            .xmap(WildRageEffect::new, WildRageEffect::chance);

    @Override
    public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
        if (Math.random() < chance.calculate(i)) {
            victim.addEffect(new MobEffectInstance(ModEffects.WILD_RAGE, 15 * 20, 0));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
