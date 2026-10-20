package com.endchants.enchantment.effect.ranged;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record BonusShotEffect() implements EntityEffectBase {
    public static final MapCodec<BonusShotEffect> CODEC = MapCodec.unit(BonusShotEffect::new);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        SendArrowToEntity.sendArrowToEntity(enchantedItemInUse.owner().getEyePosition(), enchantedItemInUse.owner(),
                enchantedItemInUse.owner(), serverLevel);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
