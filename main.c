//
//  main.c
//  Rouge_Sandbox
//
//  Created by Michael on 10/10/24.
//  Copyright © 2024 Michael. All rights reserved.
//

#include <stdio.h>
#include "rouge.h"

// Let's declare (init) our EXTERN variables here
const int MAP_HEIGHT = 25;
const int MAP_WIDTH = 100;

// Screen Size
const int screenWidth = 800;
const int screenHeight = 450;

Entity* player;
Tile** map;
InputState* playerInput;
Camera2D camera = {0};
int tileSize = 32;

int example_main(void)
{
    Position start_pos;
    
    // Set up the engine
    raylibSetup();
    srand(time(NULL));
    playerInput = createPlayerControl();
    
    // Set up the map
    map = createMapTiles();
    start_pos = setupMap();
    
    // Set up the player
    player = createPlayer(start_pos);

    
    // Start the game loop
    gameLoop();
    
    closeGame();
    
    return 0;
}

//------------------------------------------------------------------------------------
// Program main entry point
//------------------------------------------------------------------------------------
int main(void)
{
    Position start_pos;
    
    // Initialization
    raylibSetup();
    
    // Set up the map
    map = createMapTiles();
    start_pos = setupMap();
    
    // Set up the player
    player = createPlayer(start_pos);
    
    // Main game loop
    gameLoop();
    
    // De-Initialization
    //--------------------------------------------------------------------------------------
           
    //--------------------------------------------------------------------------------------
    closeGame();
    return 0;
}

void drawch(int x, int y, int drawType)
{
    
}