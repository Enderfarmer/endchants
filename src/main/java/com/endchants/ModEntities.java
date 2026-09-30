package com.endchants;

import com.endchants.entity.CustomAoeCloudEntity;
import com.endchants.entity.EntityDims;
import com.endchants.entity.ShockwaveEntity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
        public static final EntityType<ShockwaveEntity> SHOCKWAVE = register("shockwave",
                        new EntityDims(1, .5f), ShockwaveEntity::new);
        public static final EntityType<CustomAoeCloudEntity> CUSTOM_AOE_CLOUD = register("custom_aoe_cloud",
                        EntityType.Builder.of(CustomAoeCloudEntity::new, MobCategory.MISC).noLootTable().fireImmune()
                                        .sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE));

        public static <T extends Entity> EntityType<T> register(String name, EntityDims dimensions,
                        EntityType.EntityFactory<T> factory) {
                Identifier id = Endchants.id(name);
                ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, id);
                EntityType<T> entityType = EntityType.Builder.of(factory, MobCategory.MISC)
                                .sized(dimensions.width(), dimensions.height()).noSave().alwaysUpdateVelocity(true)
                                .build(resourceKey);
                return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, entityType);

        }

        public static <T extends Entity> EntityType<T> register(String name,
                        EntityType.Builder<T> builder) {
                Identifier id = Endchants.id(name);
                ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, id);
                EntityType<T> entityType = builder.build(resourceKey);
                return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, entityType);

        }

        public static void init() {
        }
}
