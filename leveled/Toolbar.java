package leveled;

import java.awt.*;
import java.awt.event.*;

public class Toolbar extends Panel {

    private Button[] buttons;
    private int paletteSelected;

    private Panel palettePicker;
    private Label paletteSelectedDisplay;

    public class PalettePickListener implements ActionListener{
        int paletteIndex;
        public PalettePickListener(int index){
            paletteIndex = index;
        }
        public void actionPerformed(ActionEvent event){
            // I can call setPallete from Toolbar...
            // because this is an inner class!
            setPalette(paletteIndex);
        }
    }

    public Toolbar(){
        // Initialize the buttons, and add their action listener
        buttons = new Button[Level.validCharset.length];
        for(int i=0;i<buttons.length;i++){
            buttons[i] = new Button(""+Level.validCharset[i]);
            buttons[i].addActionListener(new PalettePickListener(i));
        }

        // Add the buttons to the palette picker
        palettePicker = new Panel();
        palettePicker.setLayout(new GridLayout(1,buttons.length));
        for(int i=0;i<buttons.length;i++){
            palettePicker.add(buttons[i]);
        }
        add(palettePicker);

        // Add a text field that indicates selected char from palette.
        paletteSelectedDisplay = new Label();
        add(paletteSelectedDisplay);
        setPalette(0);

    }

    public char getPalette(){
        return Level.validCharset[paletteSelected];
    }

    public void setPalette(int index){
        paletteSelected = index;
        paletteSelectedDisplay.setText(
            "Selected: [ "+Level.validCharset[paletteSelected]+" ]");
    }

}
