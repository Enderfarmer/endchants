package com.endchants.enchantment.effect.armor;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.MapCodec;

import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public record RecyclingEffect(LevelBasedValue quiverSize) implements EntityEffectBase {
    public static final MapCodec<RecyclingEffect> CODEC = LevelBasedValue.CODEC.fieldOf("quiverSize")
            .xmap(RecyclingEffect::new, RecyclingEffect::quiverSize);

    @Override
    public void affectOwner(int i, LivingEntity owner) {
        if (owner.getArrowCount() >= 10) {
            owner.setArrowCount(0);
            ItemStack quiver = new ItemStack(Items.ARROW, (int) quiverSize.calculate(i));
            ItemEntity item = EntityType.ITEM.create(owner.level(), EntitySpawnReason.TRIGGERED);
            item.setItem(quiver);
            item.setPos(owner.position());
            owner.level().addFreshEntity(item);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
