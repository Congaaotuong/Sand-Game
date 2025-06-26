package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ElementsList extends JPanel {

    private List<String> ElementsList = new ArrayList<>();
    private DefaultListModel<String> listModel;

    public ElementsList() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Elements in Simulation", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listModel.addElement("Ash");
        listModel.addElement("Bedrock");
        listModel.addElement("Clay");
        listModel.addElement("Cooling Core");
        listModel.addElement("Copper");
        listModel.addElement("Diesel");
        listModel.addElement("Fire");
        listModel.addElement("Glass");
        listModel.addElement("Heating Core");
        listModel.addElement("Ice");
        listModel.addElement("Magma");
        listModel.addElement("Molten Copper");
        listModel.addElement("Molten Glass");
        listModel.addElement("Rusted Copper");
        listModel.addElement("Sand");
        listModel.addElement("Smoke");
        listModel.addElement("Steam");
        listModel.addElement("Terracotta");
        listModel.addElement("Void");
        listModel.addElement("Water");
        listModel.addElement("Wet Sand");
        listModel.addElement("Wood");

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        list.setBackground(new Color(40, 40, 40));
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(new Color(70, 130, 180));
        list.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 4));
        add(scrollPane, BorderLayout.CENTER);
    }
}
