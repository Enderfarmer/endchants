package com.endchants.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.endchants.ModEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

@Mixin(Mob.class)
public class WildRageMixin {
    @Unique
    boolean shouldApplyWildRage = false;
    @Unique
    boolean appliedWildRage = false;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if ((Object) this instanceof PathfinderMob this_) {
            GoalSelector goals = ((MobAccessor) this_).getGoals();
            if (shouldApplyWildRage && !appliedWildRage) {
                goals.removeAllGoals(g -> true);
                goals.addGoal(2, new MeleeAttackGoal(this_, 1.0D, false));
                goals.addGoal(3, new NearestAttackableTargetGoal<LivingEntity>(this_, LivingEntity.class, true));
                appliedWildRage = true;
            }
            shouldApplyWildRage = this_.hasEffect(ModEffects.WILD_RAGE);
            appliedWildRage = shouldApplyWildRage && appliedWildRage;
        }

    }
}
