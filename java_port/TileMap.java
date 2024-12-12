/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class TileMap {
    // Mesh Data
    private Node tileMapNode;
    private Material tileMapMaterial;
    
    // List of sectors
    ArrayList<Sector2dArray> sectorList;
    
    // Camera tile position
    float camX,camY;
    float tileSize = 0.25f;
    
    public TileMap(){
        // Just initialize the sectors, they maintain tile array data
        sectorList = new ArrayList<>();
        tileMapNode = new Node();
    }
    
    public int getSectorCount(){
        return sectorList.size();
    }
    
    public ArrayList<Sector2dArray> getSectorList(){
        return sectorList;
    }
    
    public void setTileSize(float size){
        this.tileSize = size;
    }
    
    public void buildAllSectors(){
        for(Sector2dArray sector : sectorList){
            sector.setTileSize(tileSize);
            sector.buildSectorMesh();
            sector.getSectorGeometry().setMaterial(tileMapMaterial);
        }
    }
    
    public void addAllSectorsToNode(){
        for(Sector2dArray sector : sectorList){
            tileMapNode.attachChild(sector.getSectorNode());
        }
    }
    
    public void removeAllSectorsFromNode(){
        for(Sector2dArray sector : sectorList){
            sector.getSectorNode().removeFromParent();
        }
    }
    
    public Node getTileMapNode(){
        return this.tileMapNode;
    }
    
    public void setTileMaterial(Material mat){
        tileMapMaterial = mat;
    }
    
    public boolean addSector(Sector2dArray sector){
        if(sectorList.isEmpty() == true){
            sectorList.add(sector);
            return true;
        }
        
        boolean result = false;
        for(Sector2dArray test : sectorList){
            if(test.getRight() > sector.getLeft() && test.getLeft() < sector.getRight()){
                if(test.getTop() > sector.getBottom() && test.getBottom() < sector.getTop()){
                    result = true;
                }
            }
        }
        
        if(result == false){
            sectorList.add(sector);
        }
        
        return !result;
    }
    
    public Sector2dArray getSectorFromId(int id){
        for(Sector2dArray test : sectorList){
            if(test.getId() == id){
                return test;
            }
        }
        
        return null;
    }
    
    public Sector2dArray getSectorFromPosition(int x, int y){
        for(Sector2dArray sector : sectorList){
            if(sector.isWorldIndexValid(x, y)==true){
                return sector;
            }
        }
        return null;
    }
    
    public int getTile(int x, int y){
        //System.out.println("TileMap::getTile( "+x+", "+y+" )");
        for(Sector2dArray sector : sectorList){
            if(sector.isWorldIndexValid(x, y)==true){
                return sector.getWorldTile(x,y);
            }
        }
        return -1;
    }
    
    public void setTile(int x, int y, int value){
        for(Sector2dArray sector : sectorList){
            if(sector.isWorldIndexValid(x, y) == true){
                sector.setWorldTile(x, y, value);
            }
        }
    }
    
    public void updateSectors(float tpf, Vector3f camPosition){
        // --------------------------------------------------
        // Background processing
        // updateAsyncChunker()
        
        // --------------------------------------------------
        // Check if any chunks need to be loaded
        // updateLoadList()
        
        // --------------------------------------------------
        // Check if any chunks have been loaded, but need to be setup
        // i.e. - voxel config, set active blocks, etc...
        // updateSetupList()
        
        // --------------------------------------------------
        // Check if any chunks need to be rebuilt
        // i.e. - modifications from last frame that needs mesh rebuild
        // updateRebuildList()
        
        // --------------------------------------------------
        // updateFlagsList()
        
        // --------------------------------------------------
        // Check if any chunks need to be unloaded
        // updateUnloadList()
        
        // --------------------------------------------------
        // Update the visibility list (render potential)
        // updateVisibilityList();
        
        
        // --------------------------------------------------
        // Update the render list
        // updateRenderList();
    }
    
    public float getTileSize(){
        return tileSize;
    }
}
