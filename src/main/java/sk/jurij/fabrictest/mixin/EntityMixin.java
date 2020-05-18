package sk.jurij.fabrictest.mixin;

import net.fabricmc.fabric.mixin.resource.loader.MixinKeyedResourceReloadListener;
import net.minecraft.client.ClientGameSession;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sk.jurij.fabrictest.FabricTest;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow @Final private EntityType<?> type;

    @Inject(at = @At("HEAD"), method = "isGlowing()Z", cancellable = true)
    public void isGlowing(CallbackInfoReturnable<Boolean> info){
        if (this.type == Registry.ENTITY_TYPE.get(FabricTest.index)) info.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "getTeamColorValue()I", cancellable = true)
    public void getTeamColorValue(CallbackInfoReturnable<Integer> info){
        if (this.type == Registry.ENTITY_TYPE.get(FabricTest.index)) info.setReturnValue(16733525);
    }
}

