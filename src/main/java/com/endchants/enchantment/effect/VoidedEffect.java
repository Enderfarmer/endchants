package com.endchants.enchantment.effect;

import com.endchants.accessor.LivingEntityMixinInterface;
import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record VoidedEffect(LevelBasedValue maxCoef) implements EntityEffectBase {
    public static final MapCodec<VoidedEffect> CODEC = LevelBasedValue.CODEC.fieldOf("maxCoef").xmap(VoidedEffect::new,
            VoidedEffect::maxCoef);

    @Override
    public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
        ((LivingEntityMixinInterface) victim).endchants$incrementVoidedEff((int) maxCoef.calculate(i));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
