package com.endchants.enchantment.effect;

import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public record FleshEaterEffect(LevelBasedValue amount) implements EntityEffectBase {
    public static final MapCodec<FleshEaterEffect> CODEC = LevelBasedValue.CODEC.fieldOf("amount").xmap(
            FleshEaterEffect::new,
            FleshEaterEffect::amount);

    @Override
    public void affectOwner(int i, LivingEntity owner) {
        if (owner instanceof ServerPlayer player) {
            if (Math.random() > 0.8
                    && player.getActiveEffects().stream().noneMatch(eff -> eff.is(MobEffects.WEAKNESS))) {
                int addedSaturation = Math.round(amount.calculate(i));
                player.getFoodData()
                        .setFoodLevel(Math.min(player.getFoodData().getFoodLevel() + addedSaturation, 20));
            }

        } else if (owner instanceof LivingEntity livingEntity) {
            if (Math.random() > 0.8) {
                int addedSaturation = Math.round(amount.calculate(i));
                livingEntity
                        .setHealth(Math.min((addedSaturation + livingEntity.getHealth()),
                                livingEntity.getMaxHealth()));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
