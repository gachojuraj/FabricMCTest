package sk.jurij.fabrictest.mixin;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sk.jurij.fabrictest.FabricTest;

@Mixin(Main.class)
public abstract class ExampleMixine {

	@Inject(at = @At("HEAD"), method = "main([Ljava/lang/String;)V")
	private static void main(String[] args, CallbackInfo info) {
		//FabricTest.playBl();
		FabricTest.openSerial();
	}
}

