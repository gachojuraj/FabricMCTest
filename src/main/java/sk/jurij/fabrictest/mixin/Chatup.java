package sk.jurij.fabrictest.mixin;

import com.mojang.realmsclient.util.JsonUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sk.jurij.fabrictest.FabricTest;


@Mixin(ChatHud.class)
public abstract class Chatup
{
    @Shadow @Final private MinecraftClient client;

    private static final int CHAT_UP_OFFSET = 10;

    @ModifyArg(method = "render", index = 1, at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;translatef(FFF)V",
            ordinal = 0
    ))
    private float offsetY(float y)
    {

        return y - CHAT_UP_OFFSET;
    }
    int i = 0;
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;)V", cancellable = true)
    public void _addMessage(Text message, CallbackInfo info){

    }
}

