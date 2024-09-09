import javax.swing.*;
import java.awt.*;

public class CharacterView extends JPanel implements ModelObserver {
    private Character model;
    private JLabel nameLabel;
    private JLabel statusLabel;
    private JLabel actionLabel;
    private JPanel infoView;
    private JPanel inventoryView;

    private GameController controller;

    public CharacterView(Character c, GameController gc){
        this.model = c;
        this.controller = gc;
        model.addObserver(this);
        this.nameLabel = new JLabel(c.name);
        this.statusLabel = new JLabel(String.valueOf(c.isStunned()));
        this.actionLabel = new JLabel(String.valueOf(c.getActions()));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(700, 150));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        inventoryView = new JPanel();
        inventoryView.setLayout(new BoxLayout(inventoryView, BoxLayout.X_AXIS));
        inventoryView.setPreferredSize(new Dimension(500, 150));
        for(Item i: model.getItems()){
            ItemView view = new ItemView(i, controller);
            view.setAlignmentX(Component.LEFT_ALIGNMENT);
            inventoryView.add(view);
        }
        this.add(inventoryView);

        infoView = new JPanel(new GridLayout(3,2));
        infoView.setPreferredSize(new Dimension(200, 150));
        infoView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoView.add(new JLabel("Name: "));
        infoView.add(nameLabel);
        infoView.add(new JLabel("Knocked out: "));
        infoView.add(statusLabel);
        infoView.add(new JLabel("Actions: "));
        infoView.add(actionLabel);
        this.add(infoView);
    }

    @Override
    public void onModelUpdate() {
        this.statusLabel.setText(String.valueOf(model.isStunned()));
        this.actionLabel.setText(String.valueOf(model.getActions()));
        inventoryView.removeAll();
        for(Item i: model.getItems()){
            ItemView view = new ItemView(i, controller);
            view.setAlignmentX(Component.LEFT_ALIGNMENT);
            inventoryView.add(view);
        }

        repaint();
        revalidate();
    }
}
