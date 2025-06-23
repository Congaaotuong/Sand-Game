package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ElementsList {

    private List<String> elementList = new ArrayList<>();
    private JDialog infoDialog;

    public ElementsList() {
        elementList.add("Wood");
        elementList.add("Glass");
        elementList.add("Stone");
    }

    public void addElement(String element) {
        elementList.add(element);
    }

    public void showElementInfo() {
        if (infoDialog != null && infoDialog.isShowing()) {
            infoDialog.toFront(); // đưa dialog lên trước nếu đang mở
            return;
        }

        infoDialog = new JDialog();
        infoDialog.setTitle("Element List");
        infoDialog.setSize(300, 400);
        infoDialog.setLocationRelativeTo(null);
        infoDialog.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Elements in Simulation", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String element : elementList) model.addElement("• " + element);

        JList<String> list = new JList<>(model);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        list.setBackground(new Color(30, 30, 30));
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(new Color(60, 60, 60));
        list.setSelectionForeground(Color.YELLOW);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        infoDialog.setContentPane(mainPanel);
        infoDialog.setVisible(true);
    }

    public List<String> getElementList() {
        return elementList;
    }
}
