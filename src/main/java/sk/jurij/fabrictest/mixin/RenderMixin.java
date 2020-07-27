package sk.jurij.fabrictest.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sk.jurij.fabrictest.CustomLabel;

@Mixin(EntityRenderer.class)
public abstract class RenderMixin{
    //TODO: move get methods to another class
    private String getHealth(Entity entity){
        return String.valueOf((int)Math.ceil(Double.parseDouble(entity.getDataTracker().get(entity.getDataTracker().getAllEntries().get(8).getData()).toString())));
    }
    private String getName(Entity entity){
        if (!entity.hasCustomName()) return entity.getType().getName().getString();
        return entity.getDisplayName().asString();
    }
    @Shadow public TextRenderer getFontRenderer() {
        return null;
    }
    @Final @Shadow protected EntityRenderDispatcher dispatcher;
    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (entity.isLiving() && !CustomLabel.hasCustomLabel(entity)) {
            CustomLabel label = CustomLabel.AddCustomLabel(entity);
            label.addRow(this::getName);
            label.addRow(this::getHealth);
        }
        if (CustomLabel.hasCustomLabel(entity)){
            CustomLabel.render(entity, dispatcher, matrices, this.getFontRenderer(), vertexConsumers, light);
            info.cancel();
        }
    }
}
