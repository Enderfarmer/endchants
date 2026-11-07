package com.endchants.enchantment.subpredicate;

import org.jetbrains.annotations.Nullable;

import com.endchants.Endchants;
import com.endchants.ModMeleeEnchantments;
import com.endchants.ModParticles;
import com.endchants.ModSoundEvents;
import com.mojang.serialization.MapCodec;

import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class CriticalHitPredicate implements EntitySubPredicate {
    public static final MapCodec<CriticalHitPredicate> CODEC = MapCodec.unit(CriticalHitPredicate::new);

    @Override
    public boolean matches(Entity arg0, ServerLevel arg1, @Nullable Vec3 arg2) {
        if (arg0 instanceof LivingEntity entity) {
            LivingEntity attacker = entity.getLastAttacker();
            if (attacker != null) {
                ItemStack stack = attacker.getMainHandItem();
                if (stack != null) {
                    int level = Endchants.getLevel(ModMeleeEnchantments.CRITICAL_HIT, stack, arg1);
                    if (level > 0) {
                        if (Math.random() < .05 * level + .05) {
                            arg1.playSound(null, entity.blockPosition(), ModSoundEvents.CRITICAL_HIT,
                                    net.minecraft.sounds.SoundSource.PLAYERS);
                            arg1.sendParticles(ModParticles.CRITICAL_HIT, entity.getX(), entity.getY() + 1,
                                    entity.getZ(), 10, 0.5,
                                    0.5, 0.5, 0.1);
                            return true;
                        }
                    }
                }
            } else if (Math.random() < .05) {
                arg1.playSound(null, entity.blockPosition(), ModSoundEvents.CRITICAL_HIT,
                        SoundSource.PLAYERS);
                arg1.sendParticles(ModParticles.CRITICAL_HIT, entity.getX(), entity.getY() + 1, entity.getZ(), 10, 0.5,
                        0.5, 0.5, 0.1);
                return true;
            }

        }

        return false;
    }

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return CODEC;
    }

}
