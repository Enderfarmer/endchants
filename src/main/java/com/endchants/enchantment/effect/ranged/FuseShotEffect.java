package com.endchants.enchantment.effect.ranged;

import com.endchants.ModEntities;
import com.endchants.accessor.LivingEntityMixinInterface;
import com.endchants.enchantment.effect.EntityEffectBase;
import com.endchants.entity.ExplosiveArrow;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record FuseShotEffect(LevelBasedValue everyNthShot) implements EntityEffectBase {
    public static final MapCodec<FuseShotEffect> CODEC = LevelBasedValue.CODEC.fieldOf("everynthshot")
            .xmap(FuseShotEffect::new, FuseShotEffect::everyNthShot);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        LivingEntity owner = enchantedItemInUse.owner();
        LivingEntityMixinInterface mixin = (LivingEntityMixinInterface) owner;
        mixin.endchants$incrementShotNumber((int) everyNthShot.calculate(i));
        if (mixin.endchants$getShotNumber() == 0) {
            Vec3 arrowVel = entity.getDeltaMovement();
            Vec3 arrowPos = entity.position();

            entity.discard();

            ExplosiveArrow arrow = ModEntities.EXPLOSIVE_ARROW.create(serverLevel, EntitySpawnReason.TRIGGERED);
            arrow.setPos(arrowPos);
            arrow.setDeltaMovement(arrowVel);

            serverLevel.addFreshEntity(arrow);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
