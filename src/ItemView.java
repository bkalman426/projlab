import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemView extends JButton implements ModelObserver{
    private GameController controller;
    private Item model;

    private JLabel info;

    public ItemView(Item i, GameController c){
        super(i.name);
        this.model = i;
        controller = c;
        info = new JLabel(i.GetInfo());
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(100,100));
        this.setMaximumSize(new Dimension(100, 100));
        this.setMinimumSize(new Dimension(100, 100));

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ItemClicked(model);
            }
        });
        this.add(info, BorderLayout.PAGE_END);
    }


    public void Draw(){
        this.remove(info);
        info.setText(model.GetInfo());
        this.add(info, BorderLayout.PAGE_END);
    }

    @Override
    public void onModelUpdate() {
        Draw();
    }

}
