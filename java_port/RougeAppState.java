/*
 * An AbstractAppState for my Rouge-Like
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import mygame.rouge.Entity;

/**
 *
 * @author michael
 */
public class RougeAppState extends AbstractAppState{
    
    // AppState members
    Application app;
    Node rootNode = new Node("Rouge Root Node");
    Node tileMapNode = new Node("TileMap Node");
    
    // Game Lib
    rouge rouge;
    
    // TileMap member
    TileMap tileMap;
    
    // Tile-space conversion utility
    rouge.TileSpaceConversion tileConvert = new rouge.TileSpaceConversion();
    
    // List of Sectors
    private ArrayList<Sector> sectorList;
    
    // Tile size info
    private float tileScale = 1;
    private float tileSize = 0.25f;
    
    // Tile indices
    // 0 - floor
    // 1 - wall
    private int floorTile = tileConvert.getTextureIndex(1,14);
    private int wallTile = tileConvert.getTextureIndex(0,18);
    
    // Player member
    Entity player;
    
    @Override
    public void initialize(AppStateManager asm, Application app) {
        super.initialize(asm, app);
        this.app = app;
        
        // Game library setup
        this.rouge = new rouge();
        this.rouge.app = app;
        
        // Texture setup
        Texture tileTex = app.getAssetManager().loadTexture("Textures/Tilesheet/colored.png"); 
        this.rouge.tilesetMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"); 
        Image tileImage = convertTileMap(tileTex.getImage(), Image.Format.ARGB8);
        
        // APPLY THE CONVERTED IMAGE TO THE TEXTURE
        tileTex = new Texture2D(tileImage);
        
        // TEXTURE SETTINGS
        tileTex.setMagFilter(Texture.MagFilter.Nearest);
        tileTex.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        
        // APPLY THE TEXTURE TO THE TILEMAT MATERIAL
        this.rouge.tilesetMaterial.setTexture("ColorMap", tileTex); 
        
        // Create the tilemap and set it's material
        this.tileMap = new TileMap();
        this.tileMap.setTileMaterial(this.rouge.tilesetMaterial);
        
        // BUILD THE MAP
        // -------------------------------------------
        // Create first sector
        Sector2dArray sector = new Sector2dArray(15,15);
        sector.setAllTiles(floorTile);
        sector.setBorderTiles(wallTile);
        sector.setX(0);
        sector.setY(0);
        tileMap.addSector(sector);
        
        // Create second sector
        Sector2dArray sector2 = new Sector2dArray(13,5);
        sector2.setAllTiles(floorTile);
        sector2.setBorderTiles(wallTile);
        sector2.setX(sector.getWidth());
        sector2.setY(5);
        tileMap.addSector(sector2);
        
        // Create an opening between sectors
        tileMap.setTile(14, 6, floorTile);
        tileMap.setTile(14, 7, floorTile);
        tileMap.setTile(14, 8, floorTile);
        tileMap.setTile(15, 6, floorTile);
        tileMap.setTile(15, 7, floorTile);
        tileMap.setTile(15, 8, floorTile);
        
        
        // -------------------------------------------
        // Build the tile sectors
        tileMap.buildAllSectors();
        tileMap.addAllSectorsToNode();
        
        // Add the TileMap to the root node
        tileMapNode.attachChild(tileMap.getTileMapNode());
        rootNode.attachChild(tileMapNode);
        
        // Create Player
        Vector2f start_pos = new Vector2f(10,20);
        player = rouge.createPlayer(start_pos, new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"));
        rootNode.attachChild(player.obj.node);
        
        // Update Camera position and zoom
        setCameraMapPosition(15/2, 15/2, 15 * 50f);
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);
        // Update the appState
        
        // Update the rootNode
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
    }
    
    // Camera methods
    //------------------------------------------------------
    public void setCameraMapPosition(float x, float y, float zoom){
        //app.getCamera().setLocation(new Vector3f(x * tileMap.getTileSize() * tileScale, y * tileMap.getTileSize() * tileScale, 10));
        app.getCamera().setLocation(new Vector3f(x * tileMap.getTileSize() * tileScale, y * tileMap.getTileSize() * tileScale, 10));
        zoomCamera(zoom);
        //app.getCamera().setFrustumPerspective(zoom, app.getCamera().getWidth()/app.getCamera().getHeight(), app.getCamera().getFrustumNear(), app.getCamera().getFrustumFar());
        //app.getCamera().update();
    }
    
    protected void zoomCamera(float value){
        // derive fovY value
        float h = app.getCamera().getFrustumTop();
        float w = app.getCamera().getFrustumRight();
        float aspect = w / h;
        float zoomSpeed = 1;

        float near = app.getCamera().getFrustumNear();

        float fovY = FastMath.atan(h / near)
                / (FastMath.DEG_TO_RAD * .5f);
        float newFovY = fovY + value * 0.1f * zoomSpeed;
        if (newFovY > 0f) {
            // Don't let the FOV go zero or negative.
            fovY = newFovY;
        }

        h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;
        w = h * aspect;

        app.getCamera().setFrustumTop(h);
        app.getCamera().setFrustumBottom(-h);
        app.getCamera().setFrustumLeft(-w);
        app.getCamera().setFrustumRight(w);
    }
    
    // Converting World location to tile position - and back
    //------------------------------------------------------
    public Vector2f getTileToWorldPosition(int x, int y){
        return new Vector2f((x * tileMap.getTileSize())+(tileMap.getTileSize()/2),(y * tileMap.getTileSize())/* +(tileSize/2)*/);
    }
    
    public Vector2f getWorldToTilePosition(float x, float y){
        float tileX = (int)Math.floor(x / tileMap.getTileSize());
        float tileY = (int)Math.floor(y / tileMap.getTileSize());
        return new Vector2f(tileX,tileY);
    }
    
    public Node getRootNode(){ return this.rootNode; }
    
    // Remove 1px border from tilemap
    private Image convertTileMap(Image tilesheet, Image.Format newFormat){
        int width = 32 * 16;
        int height = width;
        
        ByteBuffer data = BufferUtils.createByteBuffer( (int)Math.ceil(newFormat.getBitsPerPixel() / 8.0) * width * height);
        Image convertedImage = new Image(newFormat, width, height, data,null, tilesheet.getColorSpace());
        
        ImageRaster sourceReader = ImageRaster.create(tilesheet);
        ImageRaster targetWriter = ImageRaster.create(convertedImage);
        
        // Copy each 16 x 16 tile without the 1px border
        for(int x = 0; x < 32; x++){
            for(int y = 0; y < 32; y++){
                // Source tile
                int sx = x * 17;
                int sy = y * 17;
                // Target tile
                int tx = x * 16;
                int ty = y * 16;
                
                // Copy source pixels to target pixels
                for(int cx = 0; cx < 16; cx++){
                    for(int cy = 0; cy < 16;cy++){
                        ColorRGBA color = sourceReader.getPixel(sx + cx, sy + cy);
                        targetWriter.setPixel(tx + cx, ty + cy, color);
                    }
                }
            }
        }
        
        return convertedImage;
    }
    
}
