/*
Note: This mod is written and used for educational purposes only!
It may not be used on normal minecraft clients.
 */

package sk.jurij.fabrictest;

import net.fabricmc.api.ModInitializer;
import javax.sound.sampled.*;
import java.io.File;

public class FabricTest implements ModInitializer{
    @Override
    public void onInitialize() {
        System.out.println("Hello Fabric world!");
        System.setProperty("java.awt.headless", "false");
    }

    public static void playBl(){
        try {
            AudioInputStream n  = AudioSystem.getAudioInputStream(new File("X:\\ytdltest\\bl.wav"));
            Clip c = AudioSystem.getClip();
            c.open(n);
            FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            vol.setValue(-30.0f);
            c.start();
        }
        catch (Exception ignored) {}
    }
}
