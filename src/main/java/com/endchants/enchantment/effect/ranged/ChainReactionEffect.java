package com.endchants.enchantment.effect.ranged;

import com.endchants.ModEntities;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.endchants.entity.ShortArrow;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record ChainReactionEffect(LevelBasedValue chance) implements EntityEffectBase {
    public static final MapCodec<ChainReactionEffect> CODEC = LevelBasedValue.CODEC.fieldOf("chance").xmap(
            ChainReactionEffect::new,
            ChainReactionEffect::chance);

    @Override
    public void applyToLiving(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse,
            LivingEntity victim, Vec3 vec3) {

        if (Math.random() < chance.calculate(i)) {
            Vec3 direction = vec3.subtract(enchantedItemInUse.owner().position()).normalize();
            double angle = Math.toDegrees(Math.atan2(direction.z, direction.x));
            for (int j = 0; j < 5; j++) {
                double radians = Math.toRadians(angle + j * 72);
                double x = Math.cos(radians);
                double z = Math.sin(radians);
                Vec3 spawnPosition = victim.position().add(x, 0, z);
                ShortArrow arrow = new ShortArrow(ModEntities.SHORT_ARROW, serverLevel);
                arrow.setPos(spawnPosition.x, spawnPosition.y + 1.5, spawnPosition.z);
                arrow.setOwner(enchantedItemInUse.owner());
                arrow.setYRot((float) Math.atan2(direction.z, direction.x));
                serverLevel.addFreshEntity(arrow);
                arrow.setDeltaMovement(new Vec3(x, 0, z).normalize().scale(0.5));
            }
        }

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
