package net.arbee.addola.mixins;

import net.arbee.addola.accesses.PlayerEntityModelAccess;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> implements PlayerEntityModelAccess {
    private ModelPart crown;

    protected PlayerEntityModelMixin(float scale, float pivotY, int textureWidth, int textureHeight) {
        super(scale, pivotY, textureWidth, textureHeight);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    public void inject(float scale, boolean thinArms, CallbackInfo ci) {
        this.crown = new ModelPart(this, 24, 0);
        this.crown.addCuboid(0.0F, -6.0F, .0F, 1.0F, 3.0F, 1.0F, scale);
    }

    @Inject(method = "setVisible", at = @At("RETURN"))
    public void setVisible(boolean visible, CallbackInfo ci) {
        this.crown.visible = visible;
    }

    @Override
    public void renderCrown(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.crown.copyPositionAndRotation(this.head);
        this.crown.pivotX = 0.0F;
        this.crown.pivotY = 0.0F;
        this.crown.render(matrices, vertices, light, overlay);
    }
}
