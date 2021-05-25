/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.*;

final class Config {
    final static Color[] colors = {Color.green, Color.red, Color.blue,
        Color.pink, Color.orange, Color.cyan, Color.magenta};
 
    final static Font mainFont = new Font("Monospaced", Font.BOLD, 48);
    final static Font smallFont = mainFont.deriveFont(Font.BOLD, 18);
    final static Font circle = mainFont.deriveFont(Font.ITALIC, 11);
    final static Font title = mainFont.deriveFont(Font.BOLD, 16);
    final static Font stat = new Font("Monospaced", Font.BOLD, 24);
 
    final static Dimension dim = new Dimension(1000, 1000);
 
   
   final static Rectangle previewRect = new Rectangle(805, 47, 180, 220);
   // box
   final static Rectangle boxgridRect = new Rectangle(190, 23, 140, 700);
   final static Rectangle boxgridRectr = new Rectangle(640, 23, 140, 700);
   final static Rectangle boxbot = new Rectangle(190 , 563, 590, 230);
   final static Rectangle boxtop = new Rectangle(190 , 0, 590, 50);
  
   
 // block size and column
    final static int blockSize = 30;
    final static int nRows = 18;
    final static int nCols = 12;
    // drop position
    final static Rectangle gridRect = new Rectangle(332, 47, 306, 517);

   
    
    final static int topMargin = 50;
    final static int leftMargin = 305;
    
    
    final static int scoreX = 800;
    final static int scoreY = 330;
    final static int titleX = 420;
    final static int titleY = 30;
    
    final static int clickX = 420;
    final static int clickY = 400;
    //
    final static int previewCenterX = 600;
    final static int previewCenterY = 97;
 
    final static Stroke largeStroke = new BasicStroke(5);
    final static Stroke smallStroke = new BasicStroke(2);
    final static Stroke smallStrokes = new BasicStroke(5);
  
 
    final static Color squareBorder = Color.white;
    final static Color titlebgColor = Color.white;
    final static Color textColor = Color.black;
    final static Color bgColor = new Color(0x009788);
    final static Color gridColor = new Color(0x000000);
    final static Color gridBorderColor = new Color(0xfff30);
    final static Color boxright = new Color(0xdbd223);
    final static Color buttons = new Color(0x2d78bd);
    final static Color back = new Color (0xe0dd2f);
    
    

    
    
}