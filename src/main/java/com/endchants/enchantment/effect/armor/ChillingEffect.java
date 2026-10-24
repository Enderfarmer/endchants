package com.endchants.enchantment.effect.armor;

import java.util.List;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.endchants.enchantment.effect.melee.FreezingEffect;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record ChillingEffect(LevelBasedValue amount) implements EntityEffectBase {
    public static final MapCodec<ChillingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("amount")
            .xmap(ChillingEffect::new, ChillingEffect::amount);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (Math.random() < .02) {
            FreezingEffect effect = new FreezingEffect(amount);
            double x = vec3.x;
            double y = vec3.y;
            double z = vec3.z;
            List<LivingEntity> entities = serverLevel.getNearbyEntities(LivingEntity.class,
                    TargetingConditions.forNonCombat(),
                    enchantedItemInUse.owner(), new AABB(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4));

            entities.forEach(enity -> effect.affectVictim(i, vec3, enity));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
