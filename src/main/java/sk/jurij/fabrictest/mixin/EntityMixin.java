package sk.jurij.fabrictest.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sk.jurij.fabrictest.AutoAim;
import sk.jurij.fabrictest.EntitySelector;

@Mixin(Entity.class)
public abstract class EntityMixin {
    private Entity getEntity(){
        return this.getEntityWorld().getEntityById(this.getEntityId());
    }

    @Shadow @Final private EntityType<?> type;
    @Shadow public abstract World getEntityWorld();
    @Shadow public float yaw;
    @Shadow public float pitch;

    @Shadow public abstract int getEntityId();

    @Inject(at = @At("HEAD"), method = "isGlowing()Z", cancellable = true)
    public void isGlowing(CallbackInfoReturnable<Boolean> info){
        if (this.type == Registry.ENTITY_TYPE.get(EntitySelector.index)) info.setReturnValue(true);
        if (this.getEntity().equals(AutoAim.getClosest(MinecraftClient.getInstance().player))) info.setReturnValue(true);
    }
    //TODO: implement selectable colors
    @Inject(at = @At("HEAD"), method = "getTeamColorValue()I", cancellable = true)
    public void getTeamColorValue(CallbackInfoReturnable<Integer> info){
        if (this.type == Registry.ENTITY_TYPE.get(EntitySelector.index)) info.setReturnValue(16733525);
        if (this.getEntity().equals(AutoAim.getClosest(MinecraftClient.getInstance().player))) info.setReturnValue(5636095);
    }
    @Inject(at = @At("HEAD"), method = "changeLookDirection(DD)V")
    public void changeLookDirection(double x, double y, CallbackInfo info) {
        if (AutoAim.ABToggled && this.getEntity().equals(MinecraftClient.getInstance().player)){
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (AutoAim.getClosest(player) != null) {
                this.yaw = AutoAim.calculateYaw(AutoAim.getClosest(player), player);
                this.pitch = AutoAim.calculatePitch(AutoAim.getClosest(player), player);
            }
        }
    }
}

