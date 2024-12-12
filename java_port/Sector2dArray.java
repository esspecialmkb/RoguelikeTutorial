/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import mygame.rouge.TileSpaceConversion;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 *
 * @author michael
 */
public class Sector2dArray extends Array2D{
    protected int x,y,id;
    protected int[] tileArray;
    protected ColorRGBA[] tileColor;
    protected int[] sectorNeighbor;
    //*protected int width,height; - Represented in base class
    
    // Tile Data
    private float tileSize = 0.25f; // for quick rendering
    // Class to convert texture index to texture coords
    TileSpaceConversion tileConvert = new rouge.TileSpaceConversion();
    
    // Mesh Data
    // ------------------------------------------
    private Vector3f[] vertices;
    private Vector3f[] normals;
    private Vector2f[] textureCoords;
    private short[] indices;
    private float[] color;
    
    // Mesh Object
    // --------------------------------------------
    private Geometry tileGeo;
    private Mesh tileMesh;
    private Node sectorNode = new Node();
    
    // Mesh Status Flags
    // -----------------------------------------
    private int flag = 0;
    private boolean meshReady = false;
    private boolean meshDirty = true;
    
    // CONSTRUCTORS
    // ------------------------------------------
    public Sector2dArray(int size){
        super(size,size);
        tileArray = new int[width * height];
        sectorNeighbor = new int[4];
        tileColor = new ColorRGBA[width * height];
    }
    
    public Sector2dArray(int width, int height){
        super(width,height);
        tileArray = new int[width * height];
        sectorNeighbor = new int[4];
        tileColor = new ColorRGBA[width * height];
    }
    
    // DATA SETTERS
    // ------------------------------------------
    public void setTileColor(int x, int y, ColorRGBA color){
        tileColor[toIndex(x,y)] = color;
    }
    public void setX(int x){ this.x = x;}
    public void setY(int y){ this.y = y;}
    //public void setWidth(int w){ width = w;}
    //public void setHeight(int h){ height = h;}
    public void setId(int id){this.id = id;}
    public void setFlag(int nFlag){this.flag = nFlag;}
    public void setTileSize(float size){ this.tileSize = size;}
    public void setSectorNeighbor(int neighbor, int value){sectorNeighbor[neighbor] = value;}
    //public void setSectorTypeFlag(int f){ sectorTypeFlag = f;}
    
    // DATA GETTERS
    // -----------------------------------------
    public ColorRGBA getTileColor(int x, int y){
        return tileColor[toIndex(x,y)];
    }
    public int getX(){ return x;}
    public int getY(){ return y;}
    //*public int getWidth(){ return width;}
    //*public int getHeight(){ return height;}
    public int getLeft(){ return x;}
    public int getRight(){ return x + width;}
    public int getTop(){ return y + height;}
    public int getBottom(){ return y;}
    public int getId(){return id;}
    public int getFlag(){return this.flag;}
    public int getSectorNeighbor(int neighbor){return sectorNeighbor[neighbor];}
    //public int getSectorTypeFlag(){ return sectorTypeFlag;}
    
    // Tile Check Members
    // -----------------------------------------
    public boolean isLocalIndexValid(int x, int y){
        return super.isIndexValid(x, y);
    }
    
    public boolean isWorldIndexValid(int x, int y){
        boolean xValid = (x >= this.x) && (x <= (this.x + this.width - 1));
        boolean yValid = (y >= this.y) && (y <= (this.y + this.height - 1));
        
        return xValid && yValid;
    }
    
    public int toLocalIndex(int x, int y){
        return (this.x - x) + ((this.y - y)* this.width);
    }
    
    public int getLocalTile(int x, int y){
        //System.out.println("Sector2dArray::getLocalTile( "+x+", "+y+" )");
        return tileArray[toIndex(x,y)];
    }
    
    public int getWorldTile(int x, int y){
        //System.out.println("Sector2dArray::getWorldTile( "+x+", "+y+" )");
        return getLocalTile(x - this.x, y - this.y);
    }
    
    public void setLocalTile(int x, int y, int value){
        tileArray[toIndex(x,y)] = value;
    }
    
    public void setWorldTile(int x, int y, int value){
        setLocalTile(x - this.x, y - this.y, value);
    }
    
    public void setAllTiles(int value){
        for(int i = 0; i < tileArray.length;i++){
            tileArray[i] = value;
        }
    }
    
    public void setBorderTiles(int value){
        setLocalTile(0,0,value);
        setLocalTile(0,height-1,value);
        setLocalTile(width-1,0,value);
        setLocalTile(width-1,height-1,value);
        
        for(int lx = 1; lx < width;lx++){
            setLocalTile(lx,0,value);
            setLocalTile(lx,height-1,value);
        }
        for(int ly = 1; ly < height;ly++){
            setLocalTile(0,ly,value);
            setLocalTile(width-1,ly,value);
        }
    }
    
    public void setSmartBorders(int value){
        int toX = tileConvert.toX(value);
        int toY = tileConvert.toY(value);
        
        setLocalTile(0,0,tileConvert.getTextureIndex(toX-1, toY-1));
        setLocalTile(0,height-1,tileConvert.getTextureIndex(toX-1, toY+1));
        setLocalTile(width-1,0,tileConvert.getTextureIndex(toX+1, toY-1));
        setLocalTile(width-1,height-1,tileConvert.getTextureIndex(toX+1, toY+1));
        
        for(int lx = 1; lx < width-1;lx++){
            setLocalTile(lx,0,tileConvert.getTextureIndex(toX, toY-1));
            setLocalTile(lx,height-1,tileConvert.getTextureIndex(toX, toY+1));
        }
        for(int ly = 1; ly < height-1;ly++){
            setLocalTile(0,ly,tileConvert.getTextureIndex(toX-1, toY));
            setLocalTile(width-1,ly,tileConvert.getTextureIndex(toX+1, toY));
        }
    }
    
    public void setSmartHorizBorders(int value){
        int toX = tileConvert.toX(value);
        int toY = tileConvert.toY(value);
        
        /*setLocalTile(0,0,tileConvert.getTextureIndex(toX-1, toY-1));
        setLocalTile(0,height-1,tileConvert.getTextureIndex(toX-1, toY+1));
        setLocalTile(width-1,0,tileConvert.getTextureIndex(toX+1, toY-1));
        setLocalTile(width-1,height-1,tileConvert.getTextureIndex(toX+1, toY+1));*/
        
        for(int lx = 0; lx < width;lx++){
            setLocalTile(lx,0,tileConvert.getTextureIndex(toX, toY-1));
            setLocalTile(lx,height-1,tileConvert.getTextureIndex(toX, toY+1));
        }
    }
    
    public void setSmartVertBorders(int value){
        int toX = tileConvert.toX(value);
        int toY = tileConvert.toY(value);
        
        /*setLocalTile(0,0,tileConvert.getTextureIndex(toX-1, toY-1));
        setLocalTile(0,height-1,tileConvert.getTextureIndex(toX-1, toY+1));
        setLocalTile(width-1,0,tileConvert.getTextureIndex(toX+1, toY-1));
        setLocalTile(width-1,height-1,tileConvert.getTextureIndex(toX+1, toY+1));*/
        
        for(int ly = 0; ly < height;ly++){
            setLocalTile(0,ly,tileConvert.getTextureIndex(toX-1, toY));
            setLocalTile(width-1,ly,tileConvert.getTextureIndex(toX+1, toY));
        }
    }
    
    public void setHorizBorderTiles(int value){
        for(int lx = 0; lx < width;lx++){
            setLocalTile(lx,0,value);
            setLocalTile(lx,height-1,value);
        }
    }
    
    public void setVertBorderTiles(int value){
        for(int ly = 0; ly < height;ly++){
            setLocalTile(0,ly,value);
            setLocalTile(width-1,ly,value);
        }
    }
    
    // Mesh building
    // ------------------------------------------
    public Mesh buildSectorMesh(){
        // Allocate arrays large enough for the used tiles
        vertices = new Vector3f[4 * width * height];
        normals = new Vector3f[4 * width * height];
        textureCoords = new Vector2f[4 * width * height];
        indices = new short[6 * width * height];
        color = new float[4 * width * height];
        updateMeshBuffers();
        
        return tileMesh;
    }
    
    public void updateMeshBuffers(){
        // Iterate through the array of tiles and build chunk mesh
        for(int yt = 0; yt < height; yt++){
            for(int xt = 0; xt < width; xt++){
                // Generate verts based on position
                int tileIndex = ((yt * width) + xt);
                int  vertIndex = ((yt * width) + xt) * 4;
                int triIndex = ((yt * width) + xt) * 6;
                vertices[ vertIndex + 0] = new Vector3f((xt * tileSize) + 0          ,(yt * tileSize) + 0,0);
                vertices[ vertIndex + 1] = new Vector3f((xt * tileSize) + tileSize   ,(yt * tileSize) + 0,0);
                vertices[ vertIndex + 2] = new Vector3f((xt * tileSize) + tileSize   ,(yt * tileSize) + tileSize,0);
                vertices[ vertIndex + 3] = new Vector3f((xt * tileSize) + 0          ,(yt * tileSize) + tileSize,0);

                // Generate tcoords based on the tile index at our tile position
                Vector2f[] tileTextureCoords = tileConvert.getTextureCoords(this.getLocalTile(xt, yt));

                textureCoords[ vertIndex + 0] = tileTextureCoords[0];
                textureCoords[ vertIndex + 1] = tileTextureCoords[1];
                textureCoords[ vertIndex + 2] = tileTextureCoords[2];
                textureCoords[ vertIndex + 3] = tileTextureCoords[3];

                // Generate triagnle indexes
                indices[triIndex + 0] = (short) ( vertIndex + 0);
                indices[triIndex + 1] = (short) ( vertIndex + 1);
                indices[triIndex + 2] = (short) ( vertIndex + 2);

                indices[triIndex + 3] = (short) ( vertIndex + 2);
                indices[triIndex + 4] = (short) ( vertIndex + 3);
                indices[triIndex + 5] = (short) ( vertIndex + 0);
                
                
                // TODO: build a separate mesh for collision checking

            }
        }
        
        if(tileMesh == null){
            meshReady = true;
            tileMesh = new Mesh();
            tileMesh.setDynamic();
            
            tileGeo = new Geometry("Tile Geo");
            //tileGeo.setLocalTranslation(this.x * this.tileSize, this.y * this.tileSize, 0);
            
            sectorNode = new Node();
            sectorNode.setLocalTranslation(this.x * this.tileSize, this.y * this.tileSize, 0);
            sectorNode.attachChild(tileGeo);
        }
        
        tileMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        tileMesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(textureCoords));
        tileMesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createShortBuffer(indices));
        tileMesh.updateBound();
        
        tileGeo.setMesh(tileMesh);
        
        meshDirty = false;
    }
    
    public Mesh getSectorMesh(){return this.tileMesh;}
    public Geometry getSectorGeometry(){return this.tileGeo;}
    public Node getSectorNode(){return this.sectorNode;}
}
