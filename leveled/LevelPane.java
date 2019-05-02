package leveled;

import java.awt.*;
import java.awt.event.*;

class LevelPane extends Panel  {

    private ScrollPane scroll;
    private LevelCanvas canvas;
    private Level level;
    private Panel top;
    private Toolbar toolbar;
    private Button[] resizeButtons;
    private IOLevelButtonPanel iopanel;

    class LevelCanvas extends Canvas{
        public LevelCanvas(){
            setBackground(Color.GRAY);
            setSize(Level.CELL_SIZE*level.getSizeX(),Level.CELL_SIZE*level.getSizeY());
        }
        public void paint(Graphics graphics){
            level.paintOnGraphics(graphics);
        }
    }

    class LevelMouseListener implements MouseListener{
        public void mouseExited(MouseEvent event){}
        public void mouseEntered(MouseEvent event){}
        public void mouseReleased(MouseEvent event){}
        public void mousePressed(MouseEvent event){
            int px = event.getX()/Level.CELL_SIZE;
            int py = event.getY()/Level.CELL_SIZE;
            if(event.getButton()==MouseEvent.BUTTON1){
                boolean done = level.setAt(px,py,toolbar.getPalette());
                canvas.repaint();
            } else if(event.getButton()==MouseEvent.BUTTON3){
                if(px>=0 && px<level.getSizeX() && py>=0 && py<level.getSizeY()){
                    if(px==level.playerX && py==level.playerY){
                        level.playerD = (level.playerD+1)%4;
                    }
                    level.playerX = px;
                    level.playerY = py;
                }
                canvas.repaint();
            }
        }
        public void mouseClicked(MouseEvent event){}
    }

    public class ResizeListener implements ActionListener{
        int changeX;
        int changeY;
        public ResizeListener(int changeX, int changeY){
            this.changeX = changeX;
            this.changeY = changeY;
        }
        public void actionPerformed(ActionEvent event){
            level.resize(level.getSizeX()+changeX,level.getSizeY()+changeY);
            canvas.repaint();
        }
    }

    public LevelPane(Level level){
        // Store level reference
        this.level = level;

        // Set the layout
        setLayout(new BorderLayout());

        // Add the scrollpane
        scroll = new ScrollPane();
        add(scroll,BorderLayout.CENTER);

        // Create canvas:
        canvas = new LevelCanvas();
        // Add action listener
        canvas.addMouseListener(new LevelMouseListener());
        scroll.add(canvas);

        // Add the top panel
        top = new Panel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(top,BorderLayout.NORTH);

        // Add IOLevelButtonPanel
        iopanel = new IOLevelButtonPanel(level,canvas);
        top.add(iopanel);

        // Add Toolbar
        toolbar = new Toolbar();
        top.add(toolbar);

        // Add Resize controls:
        resizeButtons = new Button[4];

        top.add(new Label("SizeX:"));
        resizeButtons[0] = new Button("-");
        resizeButtons[0].addActionListener(new ResizeListener(-1,0));
        top.add(resizeButtons[0]);
        resizeButtons[1] = new Button("+");
        resizeButtons[1].addActionListener(new ResizeListener(+1,0));
        top.add(resizeButtons[1]);
        top.add(new Label("SizeY:"));
        resizeButtons[2] = new Button("-");
        resizeButtons[2].addActionListener(new ResizeListener(0,-1));
        top.add(resizeButtons[2]);
        resizeButtons[3] = new Button("+");
        resizeButtons[3].addActionListener(new ResizeListener(0,+1));
        top.add(resizeButtons[3]);
    }
}
