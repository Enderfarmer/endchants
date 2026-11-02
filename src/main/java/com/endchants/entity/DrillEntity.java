package com.endchants.entity;

import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DrillEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private int maxBlocks = 10;
    private int blocksDrilled = 0;
    private static final double DRILL_SPEED = 0.2;
    private static final Vec3 MOVEMENT = new Vec3(0, -DRILL_SPEED, 0);

    public int getMaxBlocks() {
        return maxBlocks;
    }

    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    public DrillEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putInt("maxBlocks", maxBlocks);
        valueOutput.putInt("blocksDrilled", blocksDrilled);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        maxBlocks = valueInput.getInt("maxBlocks").orElse(10);
        blocksDrilled = valueInput.getInt("blocksDrilled").orElse(0);
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        return false;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();
        if (blocksDrilled >= maxBlocks) {
            this.discard();
            return;
        }
        this.move(MoverType.SELF, MOVEMENT);
        this.setDeltaMovement(MOVEMENT);
        if (this.onGround())
            doDrill();
    }

    private void doDrill() {
        if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel) {
            if (this.getBlockStateOn().is(BlockTags.BASE_STONE_OVERWORLD)
                    || this.getBlockStateOn().is(BlockTags.BASE_STONE_NETHER)
                    || this.getBlockStateOn().is(Blocks.END_STONE) || this.getBlockStateOn().is(BlockTags.DIRT)
                    || this.getBlockStateOn().is(BlockTags.STONE_ORE_REPLACEABLES)) {

                if (serverLevel.destroyBlock(this.getOnPos(), true))
                    blocksDrilled++;
            } else {
                this.discard();
            }
        }

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController());
    }
}
