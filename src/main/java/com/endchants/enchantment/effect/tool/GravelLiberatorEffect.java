package com.endchants.enchantment.effect.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.block.Blocks;

public record GravelLiberatorEffect(LevelBasedValue radius) {
    public static final GravelLiberatorEffect INSTANCE = new GravelLiberatorEffect(LevelBasedValue.perLevel(2));

    public void apply(ServerLevel serverLevel, int i,
            BlockPos blockPos) {
        int radiuS = (int) radius.calculate(i);
        BlockPos min = blockPos.offset(-radiuS, -radiuS, -radiuS);
        BlockPos max = blockPos.offset(radiuS, radiuS, radiuS);
        BlockPos.betweenClosed(min, max).forEach(blockP -> {
            if (serverLevel.getBlockState(blockP).is(Blocks.GRAVEL)) {
                serverLevel.destroyBlock(blockP, true);
            }
        });

    }

}
