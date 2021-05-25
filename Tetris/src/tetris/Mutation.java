/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;
enum Mutation {
    ZShape(new int[][]{{0, 0}, {0, -1}, {0, -2}, {-1, 1},{-1,0}}),//done
    SShape(new int[][]{{1, 2}, {1, 1}, {1, 0}, {0, 0},{0,-1}}),//done
    IShape(new int[][]{{0, 3}, {0, 2}, {0, 1}, {0, 0},{0,-1}}),//I shape done
    TShape(new int[][]{{1, 0}, {0, 1}, {0, 0}, {0, -1},{-1,0}}),//T shape done
    Square(new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0},{0,0}}),//square shape done
    LShape(new int[][]{{1, 1}, {1, 0}, {0, 1}, {0, 0},{0,-1}}),//done
    JShape(new int[][]{{0, 1}, {0, 0}, {0, -1}, {-1, 1},{-1,0}});//J shape done
    
 
    private Mutation(int[][] shape) {
        this.shape = shape;
        pos = new int[5][3];
        reset();
    }
 
    void reset() {
        for (int i = 0; i < pos.length; i++) {
            pos[i] = shape[i].clone();
        }
    }
 
    final int[][] pos, shape;
}
