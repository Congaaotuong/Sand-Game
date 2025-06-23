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
        listModel.addElement("Wood");
        listModel.addElement("Glass");
        listModel.addElement("Stone");

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

    public void addElement(String element) {
        listModel.addElement(element);
    }

    public List<String> getElementList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            list.add(listModel.get(i));
        }
        return list;
    }
}
