package com.endchants.enchantment.effect;

import com.endchants.ModEntities;
import com.endchants.entity.CustomAoeCloudEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record CloudEffect(Holder<MobEffect> effect, float radius, float chance, LevelBasedValue duration,
                LevelBasedValue amplifier)
                implements EntityEffectBase {
        public static final MapCodec<CloudEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
                        .group(MobEffect.CODEC.fieldOf("effect").forGetter(CloudEffect::effect),
                                        Codec.FLOAT.fieldOf("radius").forGetter(CloudEffect::radius),
                                        Codec.FLOAT.fieldOf("chance").forGetter(CloudEffect::chance),
                                        LevelBasedValue.CODEC.fieldOf("duration").forGetter(CloudEffect::duration),
                                        LevelBasedValue.CODEC.fieldOf("amplifier").forGetter(CloudEffect::amplifier))
                        .apply(instance, CloudEffect::new));

        @Override
        public void spawnEntityApply(ServerLevel serverLevel, int i, Vec3 ownerPosition, LivingEntity owner) {
                if (Math.random() < chance) {
                        CustomAoeCloudEntity cloud = ModEntities.CUSTOM_AOE_CLOUD.create(serverLevel,
                                        EntitySpawnReason.TRIGGERED);
                        cloud.setRadius(radius);
                        cloud.setPotionContents(PotionContents.EMPTY.withEffectAdded(
                                        new MobEffectInstance(effect, (int) duration.calculate(i),
                                                        (int) amplifier.calculate(i))));
                        cloud.setDuration(60);
                        cloud.setRadiusPerTick(0.0f);
                        cloud.setPos(ownerPosition);
                        cloud.setOwner(owner);
                        serverLevel.addFreshEntity(cloud);
                }
        }

        @Override
        public MapCodec<? extends EnchantmentEntityEffect> codec() {
                return CODEC;
        }
}