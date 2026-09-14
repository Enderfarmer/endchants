package com.endchants.effects;

import com.endchants.Endchants;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Radiance extends MobEffect {
    public Radiance(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int i) {
        if (livingEntity instanceof Player player) {
            player.heal(i * 3.0F + 3.0F);
        }
        livingEntity.removeEffect(BuiltInRegistries.MOB_EFFECT.get(Endchants.id("radiance")).orElseThrow());
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
