package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author michaelbradford
 * 
 * code based off of:
 * https://dev.to/ignaoya/the-c-roguelike-tutorial-part-1-the-player-3291
 */
public class Main extends SimpleApplication {
    
    
    RougeAppState rougeState;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        cam.setParallelProjection(true);
        flyCam.setDragToRotate(true);
        
        rougeState = new RougeAppState();
        //testState.setPhysicsSpace(bulletAppState);
        stateManager.attach(rougeState);
        viewPort.attachScene(rougeState.getRootNode());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
