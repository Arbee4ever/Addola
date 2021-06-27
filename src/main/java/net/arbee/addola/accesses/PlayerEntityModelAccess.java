package net.arbee.addola.accesses;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public interface PlayerEntityModelAccess {
    void renderCrown(MatrixStack matrices, VertexConsumer vertices, int light, int overlay);
}
