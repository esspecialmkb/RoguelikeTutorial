/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import java.util.ArrayList;

/**
 *  A pathway connects to rooms but uniform width.
 * @author michael
 */
public class Pathway {
    int tileMapSize;
    
    // List of tiles that make up this path
    ArrayList<TileIndex> tileIndexPath;
    
    // The sectors at the ends of this path
    Sector[] connectedSectors;
    
    // Debug visualization objects
    Line lineMesh;
    Geometry lineGeometry;
    
    class TileIndex{
        int x,y,index;
        int tile;
        
        public TileIndex(int x, int y){
            this.x = x;
            this.y = y;
            this.index = getTileIndex(x,y);
        }
    }
    
    public Pathway(){
        connectedSectors = new Sector[2];
    }
    
    public Pathway(Sector a, Sector b){
        this();
        connectedSectors[0] = a;
        connectedSectors[1] = b;
    }
    
    // Test if sector is already referrenced in this path
    public boolean containsSector(Sector sector){
        if(sector.getId() == connectedSectors[0].getId()){
            return true;
        }
        return sector.getId() == connectedSectors[1].getId();
    }
    
    // Get the sector on the other side of this path
    public Sector getOtherSector(Sector sector){
        if(sector.getId() == connectedSectors[0].getId()){
            return connectedSectors[1];
        }else if(sector.getId() == connectedSectors[1].getId()){
            return connectedSectors[0];
        }
        return null;
    }
    
    // Access the Sectors contained in this path
    public Sector getSector(int index){
        return connectedSectors[index];
    }
    
    // Create debug mesh
    public void initDebug(Material mat){
        Vector2f a = connectedSectors[0].getSectorCenter();
        Vector2f b = connectedSectors[1].getSectorCenter();
        lineMesh = new Line(new Vector3f(a.x, a.y, 10), new Vector3f(b.x, b.y, 10));
        lineGeometry = new Geometry("Path",lineMesh);
        lineGeometry.setMaterial(mat);
    }
    
    // Get debug mesh
    public Geometry getDebugMesh(){
        if(lineGeometry == null){
            return null;
        }
        
        return lineGeometry;
    }
    
    public int getTileIndex(int x, int y){
        return (y * tileMapSize) + x;
    }
    
    public int toX(int index){
        return index % tileMapSize;
    }
    
    public int toY(int index){
        return index / tileMapSize;
    }
}
