package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Panel extends JPanel implements ActionListener {

    private GUI gui;

    private List<JButton> jButtons = new ArrayList<>();

    public Panel(GUI gui) {
        this.gui = gui;

        int i = 0;

        for (String lang : GUI.getLangs()) {
            JButton jButton = new JButton(lang);
            jButton.setActionCommand(lang);
            jButton.addActionListener(this);
            jButtons.add(i, jButton);
            this.add(jButtons.get(i++));
        }
    }
    public void actionPerformed(ActionEvent e) {
        gui.setLang(e.getActionCommand());
        for (JButton jButton : jButtons) {
            this.remove(jButton);
            jButton.setEnabled(false);
        }
    }
}



