package com.endchants.enchantment.effect.armor;

import com.endchants.enchantment.effect.EntityEffectBase;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public record BuffByHealth(float healthPercentage, boolean hasToBeEqual, Holder<MobEffect> effect)
        implements EntityEffectBase {
    public static final MapCodec<BuffByHealth> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.FLOAT.fieldOf("health").forGetter(BuffByHealth::healthPercentage),
                    Codec.BOOL.fieldOf("hasToBeEqual").forGetter(BuffByHealth::hasToBeEqual),
                    MobEffect.CODEC.fieldOf("effect").forGetter(
                            BuffByHealth::effect))

            .apply(instance, BuffByHealth::new));

    @Override
    public void affectOwner(int i, LivingEntity owner) {
        if (hasToBeEqual) {
            if (Math.ceil(owner.getMaxHealth() * healthPercentage) == Math.ceil(owner.getHealth())) {
                owner.addEffect(new MobEffectInstance(effect, 2, i - 1));
            }
        } else {
            if (Math.ceil(owner.getMaxHealth() * healthPercentage) > Math.ceil(owner.getHealth())) {
                owner.addEffect(new MobEffectInstance(effect, 2, i - 1));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
