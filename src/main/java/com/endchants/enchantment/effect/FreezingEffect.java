package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;

public record FreezingEffect(LevelBasedValue amount) implements EntityEffectBase {
    public static final MapCodec<FreezingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("amount")
            .xmap(FreezingEffect::new, FreezingEffect::amount);

    @Override
    public void affectVictim(int i, Vec3 ownerPosition, LivingEntity victim) {
        victim.setTicksFrozen(victim.getTicksFrozen() + (int) amount.calculate(i));
        victim.addEffect(new MobEffectInstance(
                BuiltInRegistries.MOB_EFFECT.get(Identifier.withDefaultNamespace("slowness")).get(), 3, 0));
    }

    @Override
    public MapCodec<FreezingEffect> codec() {
        return CODEC;
    }

}
