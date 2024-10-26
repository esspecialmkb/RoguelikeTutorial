//
//  map.c
//  Raylib_Sandbox
//
//  Created by Michael on 10/10/24.
//  Copyright © 2024 Michael. All rights reserved.
//
#include "rouge.h"

Tile** createMapTiles(void)
{
    Tile** tiles = calloc(MAP_HEIGHT, sizeof(Tile*));
    
    for (int y = 0; y < MAP_HEIGHT; y++)
    {
        tiles[y] = calloc(MAP_WIDTH, sizeof(Tile));
        for (int x = 0; x < MAP_WIDTH; x++)
        {
            tiles[y][x].ch = '#';
            tiles[y][x].tileType = 1;
            tiles[y][x].walkable = false;
        }
    }
    
    return tiles;
}

Position setupMap(void)
{
    return setupRndMap();
    
    // Example of a rudimentary map
    Position start_pos = { 10, 50 };
    
    for (int y = 5; y < 15; y++)
    {
        for (int x = 40; x < 60; x++)
        {
            map[y][x].ch = '.';
            map[y][x].tileType = 0;
            map[y][x].walkable = true;
        }
    }
    
    return start_pos;
}

Position setupRndMap(void)
{
    // Create random rooms
    int y, x, height, width, n_rooms;
    // 5 to 15 rooms
    // rand() % 11 -> random int from 0 to 10
    n_rooms =  (rand() % 11) + 5;
    Room* rooms = calloc(n_rooms, sizeof(Room));
    Position start_pos;
    
    int rooms_counter = 0;
    
    // Create our rooms
    for (int i = 0; i < n_rooms; i++)
    {
        // height = 25
        y = (rand() % (MAP_HEIGHT - 10)) + 1;
        // height = 100
        x = (rand() % (MAP_WIDTH - 20)) + 1;
        height = (rand() % 7) + 3;
        width = (rand() % 15) + 5;
        
        if (!roomOverlaps(rooms, rooms_counter, y, x, height, width))
        {
            rooms[rooms_counter] = createRoom(y, x, height, width);
            addRoomToMap(rooms[rooms_counter]);
            rooms_counter++;
        }
        //rooms[i] = createRoom(y, x, height, width);
        //addRoomToMap(rooms[i]);
        if (i > 0)
        {
            connectRoomCenters(rooms[i-1].center, rooms[i].center);
        }
    }
    
    start_pos.y = rooms[0].center.y;
    start_pos.x = rooms[0].center.x;
    
    free(rooms);
    
    return start_pos;
}

bool roomOverlaps(Room* rooms, int rooms_counter, int y, int x, int height, int width)
{
    for (int i = 0; i < rooms_counter; i++)
    {
        if (x >= rooms[i].pos.x + rooms[i].width || rooms[i].pos.x >= x + width)
        {
            continue;
        }
        if (y + height <= rooms[i].pos.y || rooms[i].pos.y + rooms[i].height <= y)
        {
            continue;
        }
        
        return true;
    }
    
    return false;
}

void freeMap(void)
{
    for (int y = 0; y < MAP_HEIGHT; y++)
    { 
        free(map[y]);
    } 
    free(map);
}