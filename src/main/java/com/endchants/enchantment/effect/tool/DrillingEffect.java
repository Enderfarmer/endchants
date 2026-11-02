package com.endchants.enchantment.effect.tool;

import com.endchants.ModEntities;
import com.endchants.entity.DrillEntity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;

public class DrillingEffect {
    private static final LevelBasedValue MAX_BLOCKS = LevelBasedValue.perLevel(10, 5);

    public static void apply(ServerLevel serverLevel, Vec3 position, int level) {
        DrillEntity drill = ModEntities.DRILL.create(serverLevel, EntitySpawnReason.TRIGGERED);
        drill.setPos(position);
        drill.setMaxBlocks((int) MAX_BLOCKS.calculate(level));
        serverLevel.addFreshEntity(drill);
    }
}
