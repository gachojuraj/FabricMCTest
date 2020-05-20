package sk.jurij.fabrictest.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class RenderMixin{

    @Inject(at = @At("HEAD"), method = "update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V")
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        //Not used yet
    }
}
