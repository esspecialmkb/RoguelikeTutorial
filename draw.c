//
//  draw.c
//  Raylib_Sandbox
//
//  Created by Michael on 10/10/24.
//  Copyright © 2024 Michael. All rights reserved.
//
#include "rouge.h"

Camera2D create2DCamera(void)
{
    //camera = { 0 };
    camera.target = (Vector2){ player->pos.realX , player->pos.realY };
    camera.offset = (Vector2){ screenWidth / 2 , screenHeight / 2 };
    camera.rotation = 0.0f;
    camera.zoom = 1.0f;
    
    return camera;
}

void update2DCamera(Camera2D camera)
{
    camera.target = (Vector2){ player->pos.realX , player->pos.realY };
}

void drawTile(int x, int y, int tileType)
{
    //void DrawRectangle(int posX, int posY, int width, int height, Color color);
    switch(tileType)
    {
            // Empty Tile
        case 0:
            DrawRectangle(x * tileSize ,y * tileSize,tileSize,tileSize, LIGHTGRAY);
            DrawRectangleLines(x * tileSize, y * tileSize, tileSize, tileSize, BLACK);
            break;
            // Wall Tile
        case 1:
            DrawRectangle(x * tileSize, y * tileSize, tileSize, tileSize, DARKGRAY);
            DrawRectangleLines(x * tileSize, y * tileSize, tileSize, tileSize, BLACK);
            break;
        default:
            break;
    }
}

void drawMap(void)
{
    for (int y = 0; y < MAP_HEIGHT; y++)
    {
        for (int x = 0; x < MAP_WIDTH; x++)
        {
            //mvaddch(y, x, map[y][x].ch);
            drawTile(x, y, map[y][x].tileType);
        }
    }
}

void drawEntity(Entity* entity)
{
    //mvaddch(entity->pos.y, entity->pos.x, entity->ch);
    bool tileMovement = false;
    if(tileMovement == true)
    {
        switch(entity->entityType)
        {
                // Null Entity Type
            case 0:
                DrawRectangleLines(entity->pos.x* tileSize, entity->pos.y* tileSize, tileSize, tileSize, RED);
                break;
                // Player Entity Type
            case 1:
                DrawRectangle(entity->pos.x* tileSize, entity->pos.y* tileSize, tileSize, tileSize, GREEN);
                break;
                // Enemy Entity Type
            case 2:
                DrawRectangle(entity->pos.x* tileSize, entity->pos.y* tileSize, tileSize, tileSize, RED);
                break;
                // Projectile Entity Type
            case 3:
                //
                break;
            default:
                //
                break;
        }
    }if(tileMovement == false)
    {
        switch(entity->entityType)
        {
                // Null Entity Type
            case 0:
                DrawRectangleLines(entity->pos.realX, entity->pos.realY, tileSize, tileSize, RED);
                break;
                // Player Entity Type
            case 1:
                DrawRectangle(entity->pos.realX, entity->pos.realY, tileSize, tileSize, GREEN);
                break;
                // Enemy Entity Type
            case 2:
                DrawRectangle(entity->pos.realX, entity->pos.realY, tileSize, tileSize, RED);
                break;
                // Projectile Entity Type
            case 3:
                //
                break;
            default:
                //
                break;
            
        }
    }
    
}

void clearBeforeDraw(void)
{
    ClearBackground(RAYWHITE);
}

void drawEverything(void)
{
    BeginDrawing();
    
    clearBeforeDraw();
    drawMap();
    drawEntity(player);
    
    DrawText("Rouge, a [Raylib] prototype!", 190, 400, 20, RED);
    
    EndDrawing();
}

// Draw the screen using camera parameters
void drawEverythingWithCamera(Camera2D cam)
{
    // Drawing without camera influence
    BeginDrawing();
    
    clearBeforeDraw();
    
    // Adjust camera parameters before drawing with it
    camera.target = (Vector2){ player->pos.realX , player->pos.realY };

    // Drawing with camera influence
    BeginMode2D(camera);
    
        drawMap();
        drawEntity(player);
    EndMode2D();
    
    DrawText("Rouge, a [Raylib] prototype!", 190, 400, 20, RED);
    
    EndDrawing();
}