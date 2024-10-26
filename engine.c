//
//  engine.c
//  Raylib_Sandbox
//
//  Created by Michael on 10/10/24.
//  Copyright © 2024 Michael. All rights reserved.
//


#include "rouge.h"

void cursesSetup(void)
{
    //initscr();
    //noecho();
    //curs_set(0);
}

InputState* createPlayerControl(void)
{
    InputState* newPlayerInput = calloc(1, sizeof(InputState));
    
    newPlayerInput->up[0] = false;
    newPlayerInput->up[1] = false;
    newPlayerInput->up[2] = false;
    newPlayerInput->up[3] = false;
    newPlayerInput->up[4] = false;
    newPlayerInput->up[5] = false;
    newPlayerInput->up[6] = false;
    newPlayerInput->up[7] = false;
    
    newPlayerInput->down[0] = false;
    newPlayerInput->down[1] = false;
    newPlayerInput->down[2] = false;
    newPlayerInput->down[3] = false;
    newPlayerInput->down[4] = false;
    newPlayerInput->down[5] = false;
    newPlayerInput->down[6] = false;
    newPlayerInput->down[7] = false;
    
    newPlayerInput->released[0] = false;
    newPlayerInput->released[1] = false;
    newPlayerInput->released[2] = false;
    newPlayerInput->released[3] = false;
    newPlayerInput->released[4] = false;
    newPlayerInput->released[5] = false;
    newPlayerInput->released[6] = false;
    newPlayerInput->released[7] = false;
    
    newPlayerInput->pressed[0] = false;
    newPlayerInput->pressed[1] = false;
    newPlayerInput->pressed[2] = false;
    newPlayerInput->pressed[3] = false;
    newPlayerInput->pressed[4] = false;
    newPlayerInput->pressed[5] = false;
    newPlayerInput->pressed[6] = false;
    newPlayerInput->pressed[7] = false;
    
    newPlayerInput->mapping[0] = KEY_W;
    newPlayerInput->mapping[1] = KEY_S;
    newPlayerInput->mapping[2] = KEY_A;
    newPlayerInput->mapping[3] = KEY_D;
    newPlayerInput->mapping[4] = KEY_SPACE;
    newPlayerInput->mapping[5] = KEY_J;
    newPlayerInput->mapping[6] = KEY_K;
    newPlayerInput->mapping[7] = KEY_L;
    
    return newPlayerInput;
}

void raylibSetup(void)
{
    // Initialization
    //--------------------------------------------------------------------------------------
    
    // Start Window
    InitWindow(screenWidth, screenHeight, "Rouge [Raylib] example - basic window");
    
    SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
    //--------------------------------------------------------------------------------------
}

void gameLoop(void)
{
    int ch;
    camera = create2DCamera();
    
    drawEverythingWithCamera(camera);
    
    while(/*ch = getch()*/ !WindowShouldClose())
    {
        if (ch == 'q')
        {
            break;
        }
        // Update
        //----------------------------------------------------------------------------------
        // TODO: Update your game variables here
        //----------------------------------------------------------------------------------
        
        // Player Input
        //----------------------------------------------------------------------------------
        //getInput();
        handleInput(ch);
        
        // Draw
        //----------------------------------------------------------------------------------
        drawEverythingWithCamera(camera);
    } 
}

void getInput(void)
{
    
}

void closeGame(void)
{
    //endwin();
    free(player);
    free(playerInput);
    freeMap();
    
    CloseWindow(); // Close window and OpenGL context
}