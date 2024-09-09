import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RoomView extends JPanel implements ModelObserver {
    private Room model;
    private GameController controller;
    private JPanel infoPanel;

    private JScrollPane itemPanel;
    private JPanel holderPanel;

    private JPanel neighbourPanel;

    public RoomView(Room r, GameController c) {
        this.model = r;
        this.controller = c;

        model.addObserver(this);
        holderPanel = new JPanel(new GridBagLayout());
        holderPanel.setBackground(Color.GRAY);
        this.infoPanel = new JPanel(new GridBagLayout());

        this.setPreferredSize(new Dimension(1000, 350));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        neighbourPanel = new JPanel(new GridLayout(1, 0));
        for (Room rm : model.getNeighbours()) {
            JButton button = new JButton(rm.name);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.RoomClicked(rm);
                }
            });
            neighbourPanel.add(button);
        }

        neighbourPanel.setPreferredSize(new Dimension(900, 100));
        neighbourPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridy = 0;
        gbc.gridx = 0;
        this.add(neighbourPanel, gbc);


        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        infoPanel.add(new JLabel("Name: "), gbcInfo);
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 0;
        infoPanel.add(new JLabel(model.name), gbcInfo);
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 1;
        infoPanel.add(new JLabel("Capactiy: "), gbcInfo);
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 1;
        infoPanel.add(new JLabel(String.valueOf(model.GetCapacity())), gbcInfo);
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 2;
        infoPanel.add(new JLabel("Effects: "), gbcInfo);
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 2;
        StringBuilder infotext = new StringBuilder();
        if (model.isGassed()) {
            infotext.append("gas ");
        }
        if (model.isCursed()) {
            infotext.append("curse ");
        }
        if (model.isSticky()) {
            infotext.append("sticky ");
        }
        infoPanel.add(new JLabel(infotext.toString()), gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 3;
        infoPanel.add(new JLabel("Characters: "), gbcInfo);

        gbcInfo.gridx = 1;
        gbcInfo.gridy = 3;

        infotext = new StringBuilder();
        for (Character ch : model.getCharacters())
            infotext.append(ch.name).append(" ");
        infoPanel.add(new JLabel(infotext.toString()), gbcInfo);
        infoPanel.setPreferredSize(new Dimension(300, 150));


        holderPanel.setPreferredSize(new Dimension(900, 250));
        holderPanel.setBackground(new Color(150,150,150));


        GridBagConstraints holderGbc = new GridBagConstraints();
        holderGbc.gridx = 0;
        holderGbc.gridy = 0;
        holderGbc.weightx = 1.0;
        holderGbc.weighty = 1.0;
        holderGbc.anchor = GridBagConstraints.CENTER;
        holderGbc.insets = new Insets(0, 0, 0, 0);
        holderGbc.fill = GridBagConstraints.NONE;
        holderPanel.add(infoPanel, holderGbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(holderPanel, gbc);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Item i : model.getItems()) {
            ItemView view = new ItemView(i, controller);
            view.setAlignmentY(Component.TOP_ALIGNMENT);
            panel.add(view);
        }
        itemPanel = new JScrollPane(panel);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        itemPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        itemPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridheight = 2;
        itemPanel.setPreferredSize(new Dimension(100, 350));
        this.add(itemPanel, gbc);
    }


    public void Draw() {
        infoPanel.removeAll();
        neighbourPanel.removeAll();

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        infoPanel.add(new JLabel("Name: "), gbcInfo);
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 0;
        infoPanel.add(new JLabel(model.name), gbcInfo);
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 1;
        infoPanel.add(new JLabel("Capactiy: "), gbcInfo);
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 1;
        infoPanel.add(new JLabel(String.valueOf(model.GetCapacity())), gbcInfo);
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 2;
        infoPanel.add(new JLabel("Effects: "), gbcInfo);
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 2;
        StringBuilder infotext = new StringBuilder();
        if (model.isGassed()) {
            infotext.append("gas ");
        }
        if (model.isCursed()) {
            infotext.append("curse ");
        }
        if (model.isSticky()) {
            infotext.append("sticky ");
        }
        infoPanel.add(new JLabel(infotext.toString()), gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 3;
        infoPanel.add(new JLabel("Characters: "), gbcInfo);

        gbcInfo.gridx = 1;
        gbcInfo.gridy = 3;

        infotext = new StringBuilder();
        for (Character ch : model.getCharacters())
            infotext.append(ch.name).append(" ");
        infoPanel.add(new JLabel(infotext.toString()), gbcInfo);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Item i : model.getItems()) {
            ItemView view = new ItemView(i, controller);
            view.setAlignmentY(Component.TOP_ALIGNMENT);
            panel.add(view);
        }
        itemPanel.setViewportView(panel);


        for (Room rm : model.getNeighbours()) {
            JButton button = new JButton(rm.name);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.RoomClicked(rm);
                }
            });
            neighbourPanel.add(button);
        }

        repaint();
        revalidate();
    }


    @Override
    public void onModelUpdate() {
        Draw();
    }

}
