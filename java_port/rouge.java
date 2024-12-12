/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author michael
 */
public class rouge {
    // Reference to the application is needed for asset loading
    public Application app;
    
    public float tileSize = 0.25f;
    public Material playerMaterial;
    public Material tilesetMaterial;
    
    public final static String NEWLINE = "\n";
    public final static String CARRIAGE_RETURN = "\r";
    public final static String FORMFEED = "";
    public final static String BACKSPACE = "\b";
    public final static String SPACE = "\\s";
    public final static String TAB = "\t";
    public final static String DBL_QUOTE = "\"";

    public static final String textReset = "\u001B[0m";
    public static final String textBlack = "\u001B[30m";
    public static final String textRed = "\u001B[31m";
    public static final String textGreen = "\u001B[32m";
    public static final String textYellow = "\u001B[33m";
    public static final String textBlue = "\u001B[34m";
    public static final String textPurple = "\u001B[35m";
    public static final String textCyan = "\u001B[36m";
    public static final String textWhite = "\u001B[37m";

    public static final String textBackBlack = "\u001B[40m";
    public static final String textBackRed = "\u001B[41m";
    public static final String textBackGreen = "\u001B[42m";
    public static final String textBackYellow = "\u001B[43m";
    public static final String textBackBlue = "\u001B[44m";
    public static final String textBackPurple = "\u001B[45m";
    public static final String textBackCyan = "\u001B[46m";
    public static final String textBackWhite = "\u001B[47m";
    
    public class GameObject{
        public float posX, posY;
        public float sizeX, sizeY;
        public float velX, velY;
        public float moveSpeed;
        
        public int id;
        public float timer;
        public float health;
        
        public int type;
        
        public Node node;
    }
    
    public class Entity {
        // Location
        public Vector2f position;
        // Display
        public Box pQuad;
        public Geometry pGeo;
        public Node pNode;
        
        public GameObject obj;
    }
    
    public static class TileSpaceConversion {
        public float textureIndex = 1 / 32f;
        public int textureTileIndexWidth = 32;

        public Vector2f[] getTextureCoords(int x, int y){
            Vector2f[] textureUVs = new Vector2f[4];

            float indexX = x * textureIndex;
            float indexY = y * textureIndex;

            textureUVs[0] = new Vector2f(indexX,indexY);
            textureUVs[1] = new Vector2f(indexX + textureIndex,indexY);
            textureUVs[2] = new Vector2f(indexX + textureIndex,indexY + textureIndex);
            textureUVs[3] = new Vector2f(indexX,indexY + textureIndex);

            return textureUVs;
        }

        public Vector2f[] getTextureCoords(int index){
            return getTextureCoords(toX(index),toY(index));
        }

        public int getTextureIndex(int x, int y){
            return (y * textureTileIndexWidth) + x;
        }

        public int toX(int index){
            return index % textureTileIndexWidth;
        }

        public int toY(int index){
            return index / textureTileIndexWidth;
        }
    }
    
    public Entity createPlayer(Vector2f startPos, Material pMat) {
        Entity newPlayer = new Entity();
        
        // Set start position
        newPlayer.position = startPos;
        
        // Create graphical 'display'
        newPlayer.pQuad = new Box(tileSize * 0.5f, tileSize * 0.75f, 0.25f);
        newPlayer.pGeo = new Geometry("Player Geometry", newPlayer.pQuad);
        
        // Material
        pMat.setColor("Color", ColorRGBA.Blue);
        newPlayer.pGeo.setMaterial(pMat);
        newPlayer.pGeo.setLocalTranslation(0,newPlayer.pQuad.getYExtent(),0);
        
        // Player Control
        newPlayer.obj = new GameObject();
        newPlayer.obj.node = new Node();
        newPlayer.obj.node.attachChild(newPlayer.pGeo);
        newPlayer.obj.node.setName("Player Node");
        newPlayer.obj.moveSpeed = 0.95f;
        newPlayer.obj.sizeX = tileSize * 0.5f;
        newPlayer.obj.sizeY = tileSize * 0.75f;
        newPlayer.obj.type = 1; // Player type
        
        return newPlayer;
    }
}
