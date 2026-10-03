package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LeechingEffect(LevelBasedValue partOfMaxHealth) implements EnchantmentEntityEffect {
    public static final MapCodec<LeechingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("part_of_max_health")
            .xmap(LeechingEffect::new, LeechingEffect::partOfMaxHealth);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity livingEntity && !livingEntity.isAlive()) {
            float maxHealth = livingEntity.getMaxHealth();
            float healAmount = maxHealth * partOfMaxHealth.calculate(i);
            enchantedItemInUse.owner().heal(healAmount);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}
