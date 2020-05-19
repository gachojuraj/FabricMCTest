package sk.jurij.fabrictest.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class Chatup
{
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

