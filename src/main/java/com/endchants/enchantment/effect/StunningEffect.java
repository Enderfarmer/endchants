package com.endchants.enchantment.effect;

import com.endchants.ModEffects;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record StunningEffect(LevelBasedValue chance) implements EnchantmentEntityEffect {
    public static final MapCodec<StunningEffect> CODEC = LevelBasedValue.CODEC.fieldOf("chance")
            .xmap(StunningEffect::new, StunningEffect::chance);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity livingEntity
                && Math.random() < chance.calculate(i)) {
            livingEntity.addEffect(new MobEffectInstance(ModEffects.STUNNED, (int) 60, 0));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
