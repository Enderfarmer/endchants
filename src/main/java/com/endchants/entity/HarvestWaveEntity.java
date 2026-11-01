package com.endchants.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;

public class HarvestWaveEntity extends ShockwaveEntity {
    public HarvestWaveEntity(EntityType<? extends HarvestWaveEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {

    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && level() instanceof ServerLevel serverLevel) {
            BlockPos blockPos = new BlockPos(getBlockX(), getBlockY(), getBlockZ());
            BlockState blockState = serverLevel.getBlockState(blockPos);

            if (blockState.is(BlockTags.CROPS) && blockState.hasProperty(CropBlock.AGE)) {
                if (blockState.getValue(CropBlock.AGE) == CropBlock.MAX_AGE) {
                    serverLevel.destroyBlock(blockPos, true);
                } else if (Math.random() < .2 && blockState.getBlock() instanceof BonemealableBlock bonemealableBlock) {
                    bonemealableBlock.performBonemeal(serverLevel, random, blockPos, blockState);
                }
            }
        }
    }

}
