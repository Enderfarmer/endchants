package com.endchants.entity;

import java.util.List;

import com.endchants.ModDamageTypes;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WhirlwindEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private LivingEntity owner;
    private LevelBasedValue damage;
    private int enchantLevel;
    private int age;

    public void setProps(LivingEntity owner, LevelBasedValue damage, int enchantLevel) {
        this.owner = owner;
        this.damage = damage;
        this.enchantLevel = enchantLevel;
    }

    public WhirlwindEntity(EntityType<? extends WhirlwindEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(DefaultAnimations.genericIdleController());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        // Return the instance cache for this entity
        return animatableInstanceCache;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
    }

    @Override
    protected void defineSynchedData(Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
    }

    public void onCollide(LivingEntity entity) {
        if (!entity.equals(owner) && this.level() instanceof ServerLevel serverLevel) {
            entity.hurtServer(serverLevel,
                    new DamageSource(level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                            .get(ModDamageTypes.WHIRLWIND_DAMAGE).get()),
                    damage.calculate(enchantLevel));
        }
    }

    @Override
    public void tick() {
        super.tick();
        List<LivingEntity> collidingWith = this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox());
        collidingWith.forEach(this::onCollide);
        age++;
        if (age > 20)
            discard();
    }

}
