package com.endchants.entity;

import com.endchants.ModDamageTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShockwaveEntity extends Projectile implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private LevelBasedValue damage = LevelBasedValue.constant(2.0f);
    private int enchantLevel = 0;
    private Vec3 startingPos = null;
    protected boolean invulnerable = true;

    public ShockwaveEntity(EntityType<? extends ShockwaveEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void setDamage(LevelBasedValue dmg) {
        this.damage = dmg;
    }

    public void setStartingPos(Vec3 pos) {
        this.startingPos = pos;
    }

    public void setEnchantLevel(int lvl) {
        this.enchantLevel = lvl;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity && !ownedBy(livingEntity)
                && this.level() instanceof ServerLevel serverLevel && !livingEntity.hurtMarked) {
            livingEntity
                    .hurtServer(serverLevel,
                            new DamageSource(level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                    .getOrThrow(ModDamageTypes.SHOCKWAVE_DAMAGE)),
                            damage.calculate(enchantLevel));
            livingEntity.setDeltaMovement(
                    entityHitResult.getEntity().position().subtract(startingPos).normalize().multiply(.15, .15, .15));
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        discard();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    @Override
    public void onAboveBubbleColumn(boolean bl, BlockPos blockPos) {
    }

    @Override
    public void onInsideBubbleColumn(boolean bl) {
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 movement = this.getDeltaMovement();
        // Manually advance position if extending base Entity / Projectile
        double newX = this.getX() + movement.x;
        double newY = this.getY() + movement.y;
        double newZ = this.getZ() + movement.z;

        this.setPos(newX, newY, newZ);
        if (startingPos == null) {
            startingPos = position();
        }
        if (this.position().distanceTo(startingPos) > 10) {
            this.discard();
        }
        // 1. Calculate hit result along movement path
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

        // 2. Trigger onHit if something was struck
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onHit(hitResult); // Calls onHit, onHitEntity, or onHitBlock
        }

    }

    @Override
    public void push(Entity entity) {
        if (!ownedBy(entity))
            super.push(entity);
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return !ownedBy(entity);
    }
}
