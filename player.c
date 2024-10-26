//
//  player.c
//  Raylib_Sandbox
//
//  Created by Michael on 10/10/24.
//  Copyright © 2024 Michael. All rights reserved.
//

#include "rouge.h"

Entity* createPlayer(Position start_pos)
{
    Entity* newPlayer = calloc(1, sizeof(Entity));
    
    newPlayer->pos.y = start_pos.y;
    newPlayer->pos.x = start_pos.x;
    newPlayer->pos.realX = start_pos.x * tileSize;
    newPlayer->pos.realY = start_pos.y * tileSize;
    newPlayer->movementSpeed = tileSize * 0.15f;
    newPlayer->width = tileSize;
    newPlayer->height = tileSize;
    newPlayer->ch = '@';
    newPlayer->entityType = 1;
    
    return newPlayer;
}

void updatePlayerPosition()
{
    player->pos.x = (int)(player->pos.realX / tileSize);
    player->pos.y = (int)(player->pos.realY / tileSize);
}

void handleInput(int input)
{
    Position newPos = { player->pos.y, player->pos.x, player->pos.realX, player->pos.realY };
    
    // Up Vector
    if(IsKeyDown(KEY_W) || IsKeyPressed(KEY_W))
    {
        //newPos.y--;
        newPos.realY -= player->movementSpeed;
    }
    
    // Down Vector
    if(IsKeyDown(KEY_S) || IsKeyPressed(KEY_S))
    {
        //newPos.y++;
        newPos.realY += player->movementSpeed;
    }
    
    // Left Vector
    if(IsKeyDown(KEY_A) || IsKeyPressed(KEY_A))
    {
        //newPos.x--;
        newPos.realX -= player->movementSpeed;
    }
    
    // Right Vector
    if(IsKeyDown(KEY_D) || IsKeyPressed(KEY_D))
    {
        //newPos.x++;
        newPos.realX += player->movementSpeed;
    }
    
    switch(input)
    {
            //move up
        case 'k':
            newPos.y--;
            break;
            //move down
        case 'j':
            newPos.y++;
            break;
            //move left
        case 'h':
            newPos.x--;
            break;
            //move right
        case 'l':
            newPos.x++;
            break;
        default:
            break;
    }
    
    movePlayer(newPos);
}

void movePlayer(Position newPos)
{
    
    // Update the tile position based on "real" movement
    int playerXOffset = tileSize * 0.5f;
    int playerYOffset = tileSize * 0.25f;
    newPos.x = (int)((newPos.realX + playerXOffset) / tileSize);
    newPos.y = (int)((newPos.realY - playerYOffset)/ tileSize) + 1;
    
    //updatePlayerPosition();
    if (map[newPos.y][newPos.x].walkable)
    {
        player->pos.y = newPos.y;
        player->pos.x = newPos.x;
        
        player->pos.realY = newPos.realY;
        player->pos.realX = newPos.realX;
    }
}