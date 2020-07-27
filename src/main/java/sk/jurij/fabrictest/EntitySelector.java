package sk.jurij.fabrictest;

import net.minecraft.util.registry.Registry;

import javax.swing.*;
import java.awt.*;

// TODO: implement in-game gui
// TODO: make this non-static
public class EntitySelector {
    public static void openList() {
        JFrame f = new JFrame("EntityType picker");
        f.setLocationRelativeTo(null);
        DefaultListModel<String> l1 = new DefaultListModel<>();
        l1.addElement("none");
        Registry.ENTITY_TYPE.forEach(name -> l1.addElement(name.getName().getString()));
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
