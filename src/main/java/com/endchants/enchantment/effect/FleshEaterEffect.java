package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record FleshEaterEffect(LevelBasedValue amount) implements EnchantmentEntityEffect {
    public static final MapCodec<FleshEaterEffect> CODEC = LevelBasedValue.CODEC.fieldOf("amount").xmap(
            FleshEaterEffect::new,
            FleshEaterEffect::amount);

    @Override
    public void apply(ServerLevel serverLevel, int level, EnchantedItemInUse enchantedItemInUse, Entity entity,
            Vec3 vec3) {
        if (entity instanceof ServerPlayer player) {
            if (Math.random() > 0.8) {
                int addedSaturation = Math.round(amount.calculate(level));
                player.getFoodData().setFoodLevel(Math.min(player.getFoodData().getFoodLevel() + addedSaturation, 20));
            }

        } else if (entity instanceof LivingEntity livingEntity) {
            if (Math.random() > 0.8) {
                int addedSaturation = Math.round(amount.calculate(level));
                livingEntity
                        .setHealth(Math.min((addedSaturation + livingEntity.getHealth()), livingEntity.getMaxHealth()));
            }
        }

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
