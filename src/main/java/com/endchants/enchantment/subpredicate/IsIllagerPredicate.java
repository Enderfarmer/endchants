package com.endchants.enchantment.subpredicate;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.phys.Vec3;

public class IsIllagerPredicate implements EntitySubPredicate {
    public static final MapCodec<IsIllagerPredicate> CODEC = MapCodec.unit(IsIllagerPredicate::new);

    @Override
    public boolean matches(Entity arg0, ServerLevel arg1, @Nullable Vec3 arg2) {
        return arg0 instanceof Raider;
    }

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return CODEC;
    }
}
