package com.endchants.client;

import com.endchants.ModEntities;
import com.endchants.ModParticles;
import com.endchants.client.particle.CriticalHitParticle;
import com.endchants.client.particle.PoisonCloudParticle;
import com.endchants.client.renderer.CustomArrowRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EndchantsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(ModEntities.SHOCKWAVE,
				context -> new GeoEntityRenderer<>(context, ModEntities.SHOCKWAVE));
		EntityRenderers.register(ModEntities.HARVEST_WAVE,
				context -> new GeoEntityRenderer<>(context, ModEntities.HARVEST_WAVE));
		EntityRenderers.register(ModEntities.WHIRLWIND,
				context -> new GeoEntityRenderer<>(context, ModEntities.WHIRLWIND));
		EntityRenderers.register(ModEntities.CUSTOM_AOE_CLOUD, NoopRenderer::new);
		EntityRenderers.register(ModEntities.SHORT_ARROW, CustomArrowRenderer::new);
		EntityRenderers.register(ModEntities.EXPLOSIVE_ARROW, CustomArrowRenderer::new);
		EntityRenderers.register(ModEntities.DRILL, context -> new GeoEntityRenderer<>(context, ModEntities.DRILL));

		ParticleFactoryRegistry.getInstance().register(ModParticles.POISON_CLOUD,
				spriteSet -> new PoisonCloudParticle.Provider(spriteSet));
		ParticleFactoryRegistry.getInstance().register(ModParticles.CRITICAL_HIT,
				spriteSet -> new CriticalHitParticle.Provider(spriteSet));

	}
}