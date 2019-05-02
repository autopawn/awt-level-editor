package leveled;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class Level {

    public static final char[] validCharset = {'.','#','x','b','B'};
    public static final int CELL_SIZE = 48;
    public static final int BOX_OFFSET = 8;
    public static final String[] playerDirText = {">","^","<","v"};

    private char[][] cells;
    private int sizeX;
    private int sizeY;

    public int playerX,playerY,playerD;


    public int getSizeX(){
        return sizeX;
    }
    public int getSizeY(){
        return sizeY;
    }

    public Level(int sizeX, int sizeY){
        cells = new char[sizeY][sizeX];
        // Set every cell to the first valid char
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for(int y=0;y<sizeY;y++){
            for(int x=0;x<sizeX;x++){
                cells[y][x] = validCharset[0];
            }
        }
    }

    public char getAt(int x, int y){
        if(x<0) return '?';
        if(x>=sizeX) return '?';
        if(y<0) return '?';
        if(y>=sizeY) return '?';
        return cells[y][x];
    }

    public boolean setAt(int x, int y, char v){
        if(x<0) return false;
        if(x>=sizeX) return false;
        if(y<0) return false;
        if(y>=sizeY) return false;
        cells[y][x] = v;
        return true;
    }

    public void resize(int sizeX, int sizeY){
        if(sizeX<1) sizeX=1;
        if(sizeY<1) sizeY=1;
        char[][] temp = cells;
        // Replace cells matrix:
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        cells = new char[sizeY][sizeX];
        // Put old values when available:
        for(int y=0;y<sizeY;y++){
            for(int x=0;x<sizeX;x++){
                if(y<temp.length && x<temp[y].length){
                    cells[y][x] = temp[y][x];
                }else{
                    cells[y][x] = validCharset[0];
                }
            }
        }
        if(playerX>=sizeX) playerX = sizeX-1;
        if(playerY>=sizeY) playerY = sizeY-1;
    }

    public void paintOnGraphics(Graphics graphics){
        Graphics2D gra = (Graphics2D) graphics;
        for(int y=0;y<sizeY;y++){
            for(int x=0;x<sizeX;x++){
                switch(cells[y][x]){
                    case '.':
                        gra.setColor(Color.LIGHT_GRAY);
                        gra.fillRect(CELL_SIZE*x,CELL_SIZE*y,CELL_SIZE,CELL_SIZE);
                        break;
                    case '#':
                        gra.setColor(Color.BLACK);
                        gra.fillRect(CELL_SIZE*x,CELL_SIZE*y,CELL_SIZE,CELL_SIZE);
                        break;
                    case 'x':
                        gra.setColor(Color.YELLOW);
                        gra.fillRect(CELL_SIZE*x,CELL_SIZE*y,CELL_SIZE,CELL_SIZE);
                        break;
                    case 'b':
                        gra.setColor(Color.LIGHT_GRAY);
                        gra.fillRect(CELL_SIZE*x,CELL_SIZE*y,CELL_SIZE,CELL_SIZE);
                        gra.setColor(Color.ORANGE);
                        gra.fillRect(
                            CELL_SIZE*x+BOX_OFFSET,CELL_SIZE*y+BOX_OFFSET,
                            CELL_SIZE-2*BOX_OFFSET,CELL_SIZE-2*BOX_OFFSET);
                        break;
                    case 'B':
                        gra.setColor(Color.YELLOW);
                        gra.fillRect(CELL_SIZE*x,CELL_SIZE*y,CELL_SIZE,CELL_SIZE);
                        gra.setColor(Color.ORANGE);
                        gra.fillRect(
                            CELL_SIZE*x+BOX_OFFSET,CELL_SIZE*y+BOX_OFFSET,
                            CELL_SIZE-2*BOX_OFFSET,CELL_SIZE-2*BOX_OFFSET);
                        break;
                    default:
                        gra.drawString("'"+cells[y][x]+"' ??? ",CELL_SIZE*x,CELL_SIZE*y);
                        break;
                }
                gra.setColor(Color.GRAY);
                gra.drawRect(CELL_SIZE*x,CELL_SIZE*y,CELL_SIZE,CELL_SIZE);
            }
        }
        // Draw player
        gra.setColor(Color.RED);
        gra.fillOval(
            CELL_SIZE*playerX+BOX_OFFSET,CELL_SIZE*playerY+BOX_OFFSET,
            CELL_SIZE-2*BOX_OFFSET,CELL_SIZE-2*BOX_OFFSET);
        gra.setColor(Color.BLACK);
        gra.drawString(playerDirText[playerD],
            CELL_SIZE*playerX+CELL_SIZE/2-4,
            CELL_SIZE*playerY+CELL_SIZE/2+4);
    }

    public void saveToFile(String fname) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fname));
        writer.write(sizeX+" "+sizeY+"\n");
        writer.write(playerX+" "+playerY+" "+playerD+"\n");
        for(int y=0;y<sizeY;y++){
            for(int x=0;x<sizeX;x++){
                writer.write(""+cells[y][x]);
                if(x<sizeX-1){
                    writer.write(" ");
                }
            }
            writer.write("\n");
        }
        writer.close();
    }

    public void loadFromFile(String fname) throws IOException {
        FileReader freader = new FileReader(fname);
        Scanner scanner = new Scanner(freader);
        // Read size and resize
        int sx = scanner.nextInt();
        int sy = scanner.nextInt();
        resize(sx,sy);
        // Read player data
        playerX = scanner.nextInt();
        playerY = scanner.nextInt();
        playerD = scanner.nextInt();
        // Read cells
        for(int y=0;y<sizeY;y++){
            for(int x=0;x<sizeX;x++){
                String s = scanner.next();
                cells[y][x] = s.charAt(0);
            }
        }
        freader.close();
    }
}
