package sk.jurij.fabrictest.mixin;


import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.KeyCode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sk.jurij.fabrictest.FabricTest;

@Mixin(Keyboard.class)

public abstract class KeyMixin{

    @Shadow private boolean repeatEvents;

    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
    public void onKey(long window, int key, int scancode, int i, int j, CallbackInfo info) {
        if (key == 66 && InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 66)) FabricTest.gList();
    }

}