package leveled;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

// This code was based on:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java

public class IOLevelButtonPanel extends Panel {
    Button openButton, saveButton;
    JFileChooser fc;
    Canvas canvas;

    class LoadActionListener implements ActionListener{
        Level level;
        public LoadActionListener(Level level){
            this.level = level;
        }
        public void actionPerformed(ActionEvent event){
            int returnVal = fc.showOpenDialog(IOLevelButtonPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try{
                    level.loadFromFile(file.getAbsolutePath());
                    System.out.println("\""+file.getAbsolutePath()+"\" loaded.");
                    if(canvas!=null) canvas.repaint();
                }catch(IOException ex){
                    System.out.println("Couldn't load: \""+file.getAbsolutePath()+"\".");
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Open cancelled.");
            }
        }

    }

    class SaveActionListener implements ActionListener{
        Level level;
        public SaveActionListener(Level level){
            this.level = level;
        }
        public void actionPerformed(ActionEvent event){
            int returnVal = fc.showSaveDialog(IOLevelButtonPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try{
                    level.saveToFile(file.getAbsolutePath());
                    System.out.println("\""+file.getAbsolutePath()+"\" saved.");
                }catch(IOException ex){
                    System.out.println("Couldn't save: \""+file.getAbsolutePath()+"\".");
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Save cancelled.");
            }
        }
    }

    public IOLevelButtonPanel(Level level, Canvas canvas){
        super(new FlowLayout());

        // Save the canvas, to call the repaint function once a new level is loaded
        // It can be null
        this.canvas = canvas;

        //Create a file chooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //Create the open button.
        openButton = new Button("Open");
        openButton.addActionListener(new LoadActionListener(level));
        add(openButton);

        //Create the save button.
        saveButton = new Button("Save");
        saveButton.addActionListener(new SaveActionListener(level));
        add(saveButton);
    }

}
