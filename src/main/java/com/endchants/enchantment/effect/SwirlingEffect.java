package com.endchants.enchantment.effect;

import com.endchants.ModEntities;
import com.endchants.entity.WhirlwindEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record SwirlingEffect(LevelBasedValue damage) implements EnchantmentEntityEffect {
    public static final MapCodec<SwirlingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("damage")
            .xmap(SwirlingEffect::new, SwirlingEffect::damage);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        WhirlwindEntity whirlwind = ModEntities.WHIRLWIND.create(serverLevel, EntitySpawnReason.TRIGGERED);
        whirlwind.setProps(enchantedItemInUse.owner(), damage, i);
        whirlwind.setPos(enchantedItemInUse.owner().position());
        serverLevel.addFreshEntity(whirlwind);

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
