import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameFrame extends JFrame implements ModelObserver {
    private GameController control;
    private RoomView currentRoomView;
    private CharacterView currentCharacterView;

    private JButton endTurnButton;

    public GameFrame(GameController gameController){
        this.setTitle("Logarlec");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setResizable(false);
        GridBagConstraints c = new GridBagConstraints();


        control = gameController;
        endTurnButton = new JButton("End turn");
        endTurnButton.setPreferredSize(new Dimension(150,100));
        currentCharacterView = new CharacterView(control.getCurrentCharacter(), control);
        currentRoomView = new RoomView(control.getCurrentCharacter().getRoom(), control);

        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.nextCharacter();
            }
        });

        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        this.add(currentRoomView, c);

        JButton changeModeButton = new JButton();
        changeModeButton.setText(control.getMode() ? "Current mode: Use" : "Current mode: Drop");
        changeModeButton.setPreferredSize(new Dimension(150,100));
        changeModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.changeModeClicked();
                changeModeButton.setText(control.getMode() ? "Current mode: Use" : "Current mode: Drop");
            }
        });

        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;

        this.add(changeModeButton, c);

        c.gridy = 1;
        c.gridx = 1;
        this.add(currentCharacterView, c);

        c.gridy = 1;
        c.gridx = 2;
        this.add(endTurnButton, c);

        this.pack();
        this.setVisible(true);
    }

    public void Draw(){
        this.remove(currentCharacterView);
        this.remove(currentRoomView);
        this.currentCharacterView = new CharacterView(control.getCurrentCharacter(), control);
        if(control.getCurrentCharacter().getRoom() != null)
            currentRoomView = new RoomView(control.getCurrentCharacter().getRoom(), control);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 3;
        this.add(currentRoomView, c);
        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 1;
        this.add(currentCharacterView, c);


        repaint();
        revalidate();
    }

    @Override
    public void onModelUpdate() {
        Draw();
    }
}
