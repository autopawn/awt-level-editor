package leveled;

import java.awt.*;
import java.awt.event.*;

public class LevelEditor extends Frame{

    LevelPane levelp;

    public LevelEditor(){
        super("Soko level editor");
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
                System.exit(0);
            }
        });

        Level level = new Level(10,8);
        levelp = new LevelPane(level);
        add(levelp,BorderLayout.CENTER);

        // Set size and visible
        setSize(640,480);
        setVisible(true);
    }

    public static void main(String[] args){
        LevelEditor editor = new LevelEditor();
    }
}
