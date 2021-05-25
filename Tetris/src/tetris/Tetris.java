/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import static java.lang.Math.*;
import static java.lang.String.format;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;



import static tetris.Config.*;

 class Tetris extends JPanel implements Runnable {
    enum Dir {
        right(1, 0), down(0, 1), left(-1, 0), slow(0,1);
 
        Dir(int x, int y) {
            this.x = x;
            this.y = y;
        }
        final int x, y;
    };
   
  
    public static final int EMPTY = -1;
    public static final int BORDER = -2;
 
    Shape fallingShape;
    Shape nextShape;
    Mutation f;
    Mutation n;
    
 
    // position of falling shape
    int fallingShapeRow;
    int fallingShapeCol;
    int mutationShapeRow;
    
    final int[][] grid = new int[nRows][nCols];
 
    Thread fallingThread;
    final Scoreboard scoreboard = new Scoreboard();
    static final Random rand = new Random();
 
    public Tetris() {
        setPreferredSize(dim);
        setBackground(bgColor);
        setFocusable(true);
 
        initGrid();
        selectShape();
 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (scoreboard.isGameOver()) {
                    startNewGame();
                    repaint();
                }
            }
           
        });
        
     
        addKeyListener(new KeyAdapter() {
            boolean fastDown;
 
            @Override
            public void keyPressed(KeyEvent e) {
 
                if (scoreboard.isGameOver())
                    return;
 
                switch (e.getKeyCode()) {
 
                    case KeyEvent.VK_UP:
                        if (canRotate(fallingShape))
                        
                            rotate(fallingShape);
                       
                        break;
 
                    case KeyEvent.VK_LEFT:
                    	
                        if (canMove(fallingShape, Dir.left))
                        	
                            move(Dir.left);
                       
                        break;
 
                    case KeyEvent.VK_RIGHT:
                    	
                        if (canMove(fallingShape, Dir.right))
                        	
                            move(Dir.right);
                        
                        break;
                    case KeyEvent.VK_SPACE:
                        if (canMove(fallingShape, Dir.slow))
                            move(Dir.slow);
                        break;
                    case KeyEvent.VK_R://reset
                    	startNewGame();
                    	break;
                    	
                    case KeyEvent.VK_S://skip
                    	selectShape();
                    

                        break;
                        case KeyEvent.VK_A:
                        
                        	selectMutation();
                        	
                            break;
 
                    case KeyEvent.VK_DOWN:
                        if (!fastDown) {
                            fastDown = true;
                            while (canMove(fallingShape, Dir.down)) {
                                move(Dir.down);
                                repaint();
                            }
                            shapeHasLanded();
                        }
                       
 	                }
                repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                fastDown = false;
            }
        });
    }
    void selectShape() {
        fallingShapeRow = 1;
        fallingShapeCol = 5;
        fallingShape = nextShape;
        Shape[] shapes = Shape.values();
        nextShape = shapes[rand.nextInt(shapes.length)];
        if (fallingShape != null)
            fallingShape.reset();
    }
    void mutationselectShape() {
        fallingShapeRow = 1;
        fallingShapeCol = 5;
        fallingShape = nextShape;
        Shape[] shapes = Shape.values();
        nextShape = shapes[rand.nextInt(shapes.length)];
        if (fallingShape != null)
            fallingShape.reset();}
    
    void selectMutation() {
    	fallingShapeRow =1;
    	fallingShapeCol= 5;
    	f=n;
    	Mutation []Shapes = Mutation.values();
    	n=Shapes [rand.nextInt(Shapes.length)];
    	if (f!=null)
    		f.reset();
    }
 
    void startNewGame() {
        stop();
        
        initGrid();
        selectShape();
        scoreboard.reset();
        (fallingThread = new Thread(this)).start();
       
    }
 
    void stop() {
        if (fallingThread != null) {
            Thread tmp = fallingThread;
            fallingThread = null;
            tmp.interrupt();
            }
        }
    
 
    void initGrid() {
        for (int r = 0; r < nRows; r++) {
            Arrays.fill(grid[r], EMPTY);
            for (int c = 0; c < nCols; c++) {
                if (c == 0 || c == nCols - 1 || r == nRows - 1)
                    grid[r][c] = BORDER;
            }
        }
    }
 
    @Override
    public void run() {
 
        while (Thread.currentThread() == fallingThread) {
 
            try {
                Thread.sleep(scoreboard.getSpeed());
            } catch (InterruptedException e) {
                return;
            }
 
            if (!scoreboard.isGameOver()) {
                if (canMove(fallingShape, Dir.down)) {
                    move(Dir.down);
                } else {
                    shapeHasLanded();
                }
                repaint();
            }
        }}
   
 
    void drawStartScreen(Graphics2D g) {
        g.setFont(mainFont);
 
        g.setColor(titlebgColor);
        //g.fill(titleRect);
       // g.fill(clickRect);
 
        g.setColor(textColor);
        g.setFont(title);
        g.drawString("Modified Tetris", titleX, titleY);
 
        g.setFont(smallFont);
        g.setColor(Color.white);
        g.drawString("Click if yes (click)", clickX, clickY);
        
    }
 
    void drawSquare(Graphics2D g, int colorIndex, int r, int c) {
        g.setColor(colors[colorIndex]);
        g.fillRect(leftMargin + c * blockSize, topMargin + r * blockSize,
                blockSize, blockSize);
 
        g.setStroke(smallStroke);
        g.setColor(squareBorder);
        g.drawRect(leftMargin + c * blockSize, topMargin + r * blockSize,
                blockSize, blockSize);
    }
    void drawSquaret(Graphics2D g, int colorIndex, int r, int c) {
        g.setColor(colors[colorIndex]);
        g.fillRect(leftMargin + c * blockSize, topMargin + r * blockSize,
                blockSize, blockSize);
 
        g.setStroke(smallStroke);
        g.setColor(squareBorder);
        g.drawRect(leftMargin + c * blockSize, topMargin + r * blockSize,
                blockSize, blockSize);
    }
 
    void drawUI(Graphics2D g) {
        // grid background
        g.setColor(Color.red);
        g.fill(gridRect);
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(2));
        g.fill(previewRect);
        
        //
        g.setColor(back);
        g.fill(boxgridRect);
        g.fill(boxgridRectr);
        g.fill(boxbot);
        g.fill(boxtop);
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(5));
        g.drawLine(300, 30, 350, 30); //left upper
        g.drawLine(297, 30,297,560);// down left
        g.drawLine(670, 30, 670, 560); //down right
        g.drawLine(615, 30, 670, 30); //right upper
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.blue);
        g.drawRect(410, 17, 165, 20);
        g.setColor(Color.red);
        g.drawRect(407, 14, 170, 25);
        // start button
        
        g.setColor(new Color(14, 179, 72));
        g.fillOval(280, 580, 40, 40);
        g.setColor(Color.black);
        g.drawOval(280, 580, 40, 40);
        //sound button
        g.setColor(new Color(14, 179, 72));
        g.fillOval(350, 580, 40, 40);
        g.setColor(Color.black);
        g.drawOval(350, 580, 40, 40);
        // reset button
        g.setColor(Color.red);
        g.fillOval(430, 580, 40, 40);
        g.setColor(Color.black);
        g.drawOval(430, 580, 40, 40);
        // drop button
        g.setColor(buttons);
        g.fillOval(280, 660, 100, 100);
        g.setColor(Color.black);
        g.drawOval(280, 660, 100, 100);
      
        //rotate
        g.setColor(buttons);
        g.fillOval(550, 590, 60, 60);
        g.setColor(Color.black);
        g.drawOval(550, 590, 60, 60);
        //down
        g.setColor(buttons);
        g.fillOval(550, 720, 60, 60);
        g.setColor(Color.black);
        g.drawOval(550, 720, 60, 60);
        //keft
        g.setColor(buttons);
        g.fillOval(480, 655, 60, 60);
        g.setColor(Color.black);
        g.drawOval(480, 655, 60, 60);
        //ri
        g.setColor(buttons);
        g.fillOval(615, 655, 60, 60);
        g.setColor(Color.black);
        g.drawOval(615, 655, 60, 60);
        
        
        
        
        
 
        // the blocks dropped in the grid
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                int idx = grid[r][c];
                if (idx > EMPTY)
                    drawSquare(g, idx, r, c);
            }
        }
 
        // the borders of grid and preview panel
       
        g.setStroke(largeStroke);
        g.setColor(Color.white);
        g.draw(gridRect);
       
   
        // box grid

 
        // scoreboard
        int x = scoreX;
        int y = scoreY;
        g.setColor(Color.white);
        g.setFont(stat);
        g.drawString(format("Statistics", scoreboard.getTopscore()), 30, 30);
        g.drawString(format("Previews", scoreboard.getTopscore()), 850, 30);
        g.setColor(Color.black);
        g.setFont(smallFont);
      
        g.drawString(format("Top Score  %1d", scoreboard.getTopscore()), 35, 50);
        g.drawString(format("level    %3d", scoreboard.getLevel()), 35, 50 + 30);
        g.drawString(format("lines    %3d", scoreboard.getLines()), 35, 50 + 60);
        g.drawString(format("score    %3d", scoreboard.getScore()), 35, 50 + 90);
        g.setColor(new Color(255, 0, 187, 95));
        g.fillRect(25, 35, 150, 200);
        g.setFont(smallFont);
        g.setColor(Color.black);

        g.setFont(circle);
        g.drawString(format("   UP(M)  ", scoreboard.getTopscore()), 260, 640);
        g.drawString(format("Sound(S)  ", scoreboard.getTopscore()), 340, 640);
        g.drawString(format("Reset(R)  ", scoreboard.getTopscore()), 420, 640);
        
        g.drawString(format("Slow Drop(SPACE)  ", scoreboard.getTopscore()), 285, 775);
        g.drawString(format("Rotate  ", scoreboard.getTopscore()), 610, 600);
        g.drawString(format("Right  ", scoreboard.getTopscore()), 630, 730);
        g.drawString(format("Left  ", scoreboard.getTopscore()), 495, 730);
        g.drawString(format("Drop  ", scoreboard.getTopscore()), 563, 790);
     
        g.setColor(textColor);
        g.setFont(title);
        g.drawString("Modified Tetris", titleX, titleY);
        // preview
        int minX = 5, minY = 5, maxX = 0, maxY = 0;
        for (int[] p : nextShape.pos) {
            minX = min(minX, p[1]);
            minY = min(minY, p[1]);
            maxX = max(maxX, p[0]);
            maxY = max(maxY, p[1]);
        }
        double cx = previewCenterX - ((minX + maxX + 1) / 2.0 * blockSize);
        double cy = previewCenterY - ((minY + maxY + 1) / 2.0 * blockSize);
 
        g.translate(cx, cy);
        for (int[] p : nextShape.shape)
            drawSquare(g, nextShape.ordinal(), p[1], p[0]);
        g.translate(-cx, -cy);
    }
 
    void drawFallingShape(Graphics2D g) {
        int idx = fallingShape.ordinal();
        for (int[] p : fallingShape.pos)
            drawSquare(g, idx, fallingShapeRow + p[1], fallingShapeCol + p[0]);
    }
 
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
 
        drawUI(g);
 
        if (scoreboard.isGameOver()) {
            drawStartScreen(g);
        } else {
            drawFallingShape(g);
        }
    }
 
    boolean canRotate(Shape s) {
        if (s == Shape.Square)
            return false;
 
        int[][] pos = new int[4][2];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = s.pos[i].clone();
        }
 
        for (int[] row : pos) {
            int tmp = row[0];
            row[0] = row[1];
            row[1] = -tmp;
        }
 
        for (int[] p : pos) {
            int newCol = fallingShapeCol + p[0];
            int newRow = fallingShapeRow + p[1];
            if (grid[newRow][newCol] != EMPTY) {
                return false;
            }
        }
        return true;
    }
 
    void rotate(Shape s) {
        if (s == Shape.Square)
            return;
 
        for (int[] row : s.pos) {
            int tmp = row[0];
            row[0] = row[1];
            row[1] = -tmp;
        }
    }
 
    void move(Dir dir) {
        fallingShapeRow += dir.y;
        fallingShapeCol += dir.x;
    }
 
    boolean canMove(Shape s, Dir dir) {
        for (int[] p : s.pos) {
            int newCol = fallingShapeCol + dir.x + p[0];
            int newRow = fallingShapeRow + dir.y + p[1];
            if (grid[newRow][newCol] != EMPTY)
                return false;
        }
        return true;
    }
 
    void shapeHasLanded() {
        addShape(fallingShape);
        if (fallingShapeRow < 2) {
            scoreboard.setGameOver();
            scoreboard.setTopscore();
            stop();
         
        } else {
            scoreboard.addLines(removeLines());
        }
        selectShape();
    }
 
    int removeLines() {
        int count = 0;
        for (int r = 0; r < nRows - 1; r++) {
            for (int c = 1; c < nCols - 1; c++) {
                if (grid[r][c] == EMPTY)
                    break;
                if (c == nCols - 2) {
                    count++;
                    removeLine(r);
                }
            }
        }
        return count;
    }
 
    void removeLine(int line) {
        for (int c = 0; c < nCols; c++)
            grid[line][c] = EMPTY;
 
        for (int c = 0; c < nCols; c++) {
            for (int r = line; r > 0; r--)
                grid[r][c] = grid[r - 1][c];
        }
    }
 
    void addShape(Shape s) {
        for (int[] p : s.pos)
            grid[fallingShapeRow + p[1]][fallingShapeCol + p[0]] = s.ordinal();
    }/*
    public static void playMusic(String filepath) {
    	InputStream music;
    	try {
    		music = new FileInputStream (new File(filepath));
    		AudioStream audios = new AudioStream(music);
    		AudioPlayer.player.start(audios);
    	}catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null,"Error");
    	}*/


public void paintComponent(Graphics2D r) {
	super.paintComponent(r);
	ImageIcon i = new ImageIcon("C:\\Users\\Miguel\\Desktop\\Finalgame\\tetris\\src\\tetris\\uc.png");
	i.paintIcon(this, r, 129, 80);
	
}
    
    
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Tetris");
            f.setResizable(false);
            f.add(new Tetris(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
          
            String filepath = "Tuesday.wav";
    
            MusicStuff musicObject = new MusicStuff();
            musicObject.playmusic(filepath);
            
        });
      
        	
        
    }
}

class MusicStuff {
    
    void playmusic(String musicLoc)
    {
        try
        {
            File musicPath = new File(musicLoc);
            
            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                
                
              
            }
            else
            {
                System.out.println("Cant find file");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}