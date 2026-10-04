package com.endchants.enchantment.effect;

import com.endchants.ModEntities;
import com.endchants.entity.ShockwaveEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record ShockwaveEffect(LevelBasedValue damage) implements EntityEffectBase {
    public static final MapCodec<ShockwaveEffect> CODEC = LevelBasedValue.CODEC.fieldOf("damage")
            .xmap(ShockwaveEffect::new, ShockwaveEffect::damage);

    @Override
    public void spawnEntityApply(ServerLevel serverLevel, int i, Vec3 ownerPosition, LivingEntity owner,
            LivingEntity victim) {
        ShockwaveEntity shockwave = ModEntities.SHOCKWAVE.create(serverLevel, EntitySpawnReason.TRIGGERED);

        Vec3 origin = owner.position();
        Vec3 target = victim.position();
        Vec3 dir = target.subtract(origin);
        Vec3 originPos = origin.add(0, owner.getEyeY() - origin.y - .2, 0);

        shockwave.setOwner(owner);
        shockwave.moveOrInterpolateTo(originPos);

        shockwave.shoot(dir.x, dir.y, dir.z, .6F /* velocity */, 0.0F /* divergence/inaccuracy */);
        shockwave.lookAt(Anchor.EYES, target);
        shockwave.setDamage(damage);
        shockwave.setEnchantLevel(i);
        serverLevel.addFreshEntity(shockwave);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
