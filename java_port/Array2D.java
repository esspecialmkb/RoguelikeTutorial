/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 * Base class for containers that wrap around a single 1d array, treating it as a 2D array.
 *
 * @author michael - copied from MJ
 */
public abstract class Array2D {
    protected int width;
    protected int height;
    
    public Array2D(int size){
        this(size,size);
    }
    
    public Array2D( int width,  int height){
        this.width = width;
        this.height = height;
    }
    
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}
    
    public boolean isIndexValid(final int x, final int y){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
    
    public int toIndex(final int x, final int y){ 
        return x + (y * width);
    }
    public int toX(final int index){ return index % width;}
    public int toY(final int index){ return index / width;}
}
