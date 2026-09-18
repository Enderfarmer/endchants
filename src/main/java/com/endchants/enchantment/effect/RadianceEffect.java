package com.endchants.enchantment.effect;

import com.endchants.ModEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record RadianceEffect(int levelCoef) implements EnchantmentEntityEffect {
    public static final MapCodec<RadianceEffect> CODEC = Codec.INT
            .xmap(levelCoef -> new RadianceEffect(levelCoef), RadianceEffect::levelCoef)
            .fieldOf("levelCoef");

    @Override
    public void apply(ServerLevel serverLevel, int level,
            EnchantedItemInUse enchantedItemInUse, Entity victim,
            Vec3 vec3) {
        if (!victim.isAlive() && Math.random() < 0.15) {
            MobEffectInstance effectInstance = new MobEffectInstance(
                    ModEffects.RADIANCE, 200, levelCoef);
            AreaEffectCloud cloud = new AreaEffectCloud(victim.level(), victim.getX(), victim.getY(), victim.getZ());
            cloud.addEffect(effectInstance);
            cloud.setRadius(3.0F);
            cloud.setDuration(10);
            serverLevel.addFreshEntity(cloud);

        }
    }

    @Override
    public MapCodec<RadianceEffect> codec() {
        return CODEC;
    }

}
