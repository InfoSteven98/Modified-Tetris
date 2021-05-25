/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;
enum Shape {
	 	ZShape(new int[][]{{1, 0}, {1, -1}, {0, 2}, {0, 1},{0,0}}),//done
	    SShape(new int[][]{{1, 2}, {1, 1}, {1, 0}, {0, 0},{0,-1}}),//done
	    IShape(new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2},{0,0}}),
	    TShape(new int[][] { { 1, 0 }, { 0, 0}, { 0, -1 }, { -1, 0 },{0,0}}),
	    Square(new int[][]{{1, 1}, {1, 0}, {0, 1}, {0, 0},{0,0}}),//square shape done
	    LShape(new int[][] { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 },{-1,0}}),
	    JShape(new int[][] { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 },{1,0}});
	    
    private Shape(int[][] shape) {
        this.shape = shape;
        pos = new int[5][2];
        reset();
    }
 
    void reset() {
        for (int i = 0; i < pos.length; i++) {
            pos[i] = shape[i].clone();
        }
    }
 
    final int[][] pos, shape;
}