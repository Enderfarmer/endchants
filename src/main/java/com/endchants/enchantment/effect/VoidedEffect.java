package com.endchants.enchantment.effect;

import com.endchants.accessor.LivingEntityMixinInterface;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record VoidedEffect(LevelBasedValue maxCoef) implements EnchantmentEntityEffect {
    public static final MapCodec<VoidedEffect> CODEC = LevelBasedValue.CODEC.fieldOf("maxCoef").xmap(VoidedEffect::new,
            VoidedEffect::maxCoef);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity livingEntity) {
            ((LivingEntityMixinInterface) livingEntity).endchants$incrementVoidedEff((int) maxCoef.calculate(i));
        }

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
