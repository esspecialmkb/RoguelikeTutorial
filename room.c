//
//  room.c
//  Raylib_Sandbox
//
//  Created by Michael on 10/10/24.
//  Copyright © 2024 Michael. All rights reserved.
//

#include "rouge.h"

Room createRoom(int y, int x, int height, int width)
{
    Room newRoom;
    
    newRoom.pos.y = y;
    newRoom.pos.x = x;
    newRoom.height = height;
    newRoom.width = width;
    newRoom.center.y = y + (int)(height / 2);
    newRoom.center.x = x + (int)(width / 2);
    
    return newRoom;
}

void addRoomToMap(Room room)
{
    for (int y = room.pos.y; y < room.pos.y + room.height; y++)
    {
        for (int x = room.pos.x; x < room.pos.x + room.width; x++)
        {
            map[y][x].ch = '.';
            map[y][x].tileType = 0;
            map[y][x].walkable = true;
        }
    }
}

void connectRoomCenters(Position centerOne, Position centerTwo)
{
    Position temp;
    temp.x = centerOne.x;
    temp.y = centerOne.y;
    
    while (true)
    {
        if (abs((temp.x - 1) - centerTwo.x) < abs(temp.x - centerTwo.x))
            temp.x--;
        else if (abs((temp.x + 1) - centerTwo.x) < abs(temp.x - centerTwo.x))
            temp.x++;
        else if (abs((temp.y + 1) - centerTwo.y) < abs(temp.y - centerTwo.y))
            temp.y++;
        else if (abs((temp.y - 1) - centerTwo.y) < abs(temp.y - centerTwo.y))
            temp.y--;
        else
            break;
        
        map[temp.y][temp.x].ch = '.';
        map[temp.y][temp.x].tileType = 0;
        map[temp.y][temp.x].walkable = true;
    }
}