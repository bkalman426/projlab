import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Objects;

public class StartFrame extends JDialog {
    public StartFrame(GameController control){
        this.setLayout(new GridLayout(3,1));
        this.setResizable(false);
        JLabel label = new JLabel("How many players?");
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setParseIntegerOnly(true);
        JFormattedTextField field = new JFormattedTextField(new NumberFormatter(df));

        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!field.getText().isEmpty()){
                    control.init(Integer.parseInt(field.getText()));
                    dispose();
                }
            }
        });


        this.add(label);
        this.add(field);
        this.add(button);
        pack();
        this.setVisible(true);
    }
}
