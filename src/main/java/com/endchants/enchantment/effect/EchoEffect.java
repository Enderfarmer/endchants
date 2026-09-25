package com.endchants.enchantment.effect;

import com.endchants.ModEffects;
import com.endchants.accessor.PlayerMixinInterface;
import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record EchoEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<EchoEffect> CODEC = MapCodec.unit(EchoEffect::new);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity livingEntity) {
            LivingEntity owner = enchantedItemInUse.owner();
            if (owner instanceof ServerPlayer plair) {
                var playerMixed = (PlayerMixinInterface) plair;

                if (playerMixed.endchants$getEchoCd() == 0) {
                    System.out.println("Cooled down!");
                    playerMixed.endchants$setEchoCd(6 - i);
                    plair.addEffect(new MobEffectInstance(ModEffects.RAMPAGING, 10, 100, false, false));
                    serverLevel.getServer().execute(() -> {
                        livingEntity.hurtTime = 0;
                        livingEntity.hurtMarked = false;
                        livingEntity.invulnerableTime = 0;

                    });

                } else {
                    System.out.println("Not cooled down! Cd left: " + playerMixed.endchants$getEchoCd());
                }
            } else {
                System.out.println(owner.getClass().getName());
            }

        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
