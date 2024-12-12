/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector2f;
import java.util.ArrayList;

/**
 *  Base class of Rooms and Pathways.
 * @author michael
 */
public class Sector {
    private int x,y,width,height, id;
    
    private Sector[] connectedSectors;
    private ArrayList<Sector> connectedSectorList;
    private Pathway[] connectedPaths;
    private int sectorTypeFlag;
    
    public Sector(){
        connectedSectors = new Sector[4];
        connectedSectorList = new ArrayList<>();
        connectedPaths = new Pathway[3];
        sectorTypeFlag = 0;
    }
    
    // Interface
    public void setX(int x){ this.x = x;}
    public void setY(int y){ this.y = y;}
    public void setWidth(int w){ width = w;}
    public void setHeight(int h){ height = h;}
    public void setSectorTypeFlag(int f){ sectorTypeFlag = f;}
    
    public void setConnectedSector(Sector other, int index){ connectedSectors[index] = other;}
    public Sector getConnectedSector(int index){return connectedSectors[index];}
    public void addConnectedSector(Sector other){connectedSectorList.add(other);}
    public ArrayList getConnectedSectors(){ return connectedSectorList;}
    
    public void setPathway(Pathway path, int index){ connectedPaths[index] = path;}
    public Pathway getPathway(int index){return connectedPaths[index];}
    
    public void setId(int id){this.id = id;}
    public int getId(){return id;}
    
    public int getX(){ return x;}
    public int getY(){ return y;}
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}
    public int getLeft(){ return x;}
    public int getRight(){ return x + width;}
    public int getTop(){ return y + height;}
    public int getBottom(){ return y;}
    public int getSectorTypeFlag(){ return sectorTypeFlag;}
    
    public Vector2f getSectorCenter(){
        return new Vector2f(getLeft() + (getWidth()/2), getBottom() + (getHeight()/2));
    }
    public Sector getSectorConnection(int flag){
        return connectedSectors[flag];
    }
    public void setSectorConnection(Sector newSector, int flag){
        connectedSectors[flag] = newSector;
    }
    
    public boolean isPointInSector(int x, int y){
        if(x > getLeft() && x < getRight()){
            if(y > getBottom() && y < getTop()){
                return true;
            }
        }
        return false;
    }
}
