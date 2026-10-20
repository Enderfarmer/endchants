package com.endchants.entity;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;

public class ExplosiveArrow extends AbstractArrow {
    private int fuseTime = 0;

    public ExplosiveArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(new DustParticleOptions(
                16453134, 2f), this.position().x, this.position().y, this.position().z, 0, 0, 0);
        if (inGroundTime > 0)
            fuseTime = inGroundTime;

        if (fuseTime >= 20) {
            this.level().explode(this.getOwner(), this.position().x, this.position().y, this.position().z, 1.5f,
                    ExplosionInteraction.TRIGGER);
            this.discard();
        }

    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        this.level().explode(this.getOwner(), this.position().x, this.position().y, this.position().z, 1.5f,
                ExplosionInteraction.TRIGGER);
        this.discard();
    }

}
