import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGameFrame extends JDialog {
    public EndGameFrame(String text){
        JLabel label = new JLabel(text);
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new GridLayout(2,1));
        label.setFont(new Font("Arial", Font.PLAIN, 100));
        this.add(label);
        JButton button = new JButton("Ok");
        button.setPreferredSize(new Dimension(50,50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(button);

        this.pack();
        this.setVisible(true);
    }
}
