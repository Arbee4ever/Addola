package net.arbee.addola.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.Environment;
import net.arbee.addola.client.render.ArbeeElytraFeatureRenderer;
import net.arbee.addola.client.render.ArbeeFeatureRender;
import net.fabricmc.api.EnvType;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class CapeMixin extends LivingEntityRenderer {

	@SuppressWarnings("unchecked")
	public CapeMixin(EntityRenderDispatcher dispatcher, PlayerEntityModel<AbstractClientPlayerEntity> model, float f) {
        super(dispatcher, model, f);
    }
	
	@SuppressWarnings("unchecked")
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At("RETURN"))
	public void init(EntityRenderDispatcher dispatcher, boolean b, CallbackInfo ci) {
	    this.addFeature(new ArbeeFeatureRender(this));
	    this.addFeature(new ArbeeElytraFeatureRenderer(this));
    }
}
