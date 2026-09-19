package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record FreezingEffect(LevelBasedValue amount) implements EnchantmentEntityEffect {
    public static final MapCodec<FreezingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("amount")
            .xmap(FreezingEffect::new, FreezingEffect::amount);

    @Override
    public void apply(ServerLevel serverLevel, int level,
            EnchantedItemInUse enchantedItemInUse, Entity victim,
            Vec3 vec3) {
        if (victim instanceof LivingEntity livingEntity) {
            livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + (int) amount.calculate(level));
            livingEntity.addEffect(new MobEffectInstance(
                    BuiltInRegistries.MOB_EFFECT.get(Identifier.withDefaultNamespace("slowness")).get(), 3, 0));

        }
    }

    @Override
    public MapCodec<FreezingEffect> codec() {
        return CODEC;
    }

}
