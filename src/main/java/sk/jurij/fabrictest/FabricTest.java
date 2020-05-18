package sk.jurij.fabrictest;

import net.fabricmc.api.ModInitializer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.File;
import com.fazecast.jSerialComm.SerialPort;
import net.minecraft.util.registry.Registry;

public class FabricTest implements ModInitializer{
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

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
        catch (Exception ignored) {};
    }

    public static int prevNum = 0;
    public static void sendSerial(int num) {
        if (num != prevNum && port != null) {
            String s = String.valueOf(num);
            port.writeBytes(s.getBytes(), s.getBytes().length);
            prevNum = num;
        }

    }

    public static SerialPort openSerial(){
        try {
            SerialPort comPort = SerialPort.getCommPorts()[1];
            comPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            comPort.openPort();
            return comPort;
        }
        catch (Exception ignored) {}
        return null;
    }
    public static SerialPort port = openSerial();

    public static void gList() {
        JFrame f = new JFrame("frame");
        f.setLocationRelativeTo(null);
        DefaultListModel<String> l1 = new DefaultListModel<>();
        Registry.ENTITY_TYPE.forEach(name -> {
            l1.addElement(name.getName().asString());
        });
        JList<String> list = new JList<>(l1);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        f.setSize(400, 400);
        listScroller.setPreferredSize(new Dimension(250, 80));
        list.setBounds(0, 0, 400, 400);
        f.add(listScroller);
        f.setVisible(true);
        list.addListSelectionListener(new ListHandler());
    }
    public static int index = -1;
}
