//
//  rouge.h
//  Raylib_Sandbox
//
//  Created by Michael on 10/10/24.
//  Based on RogueLite Tutorial from:
//  https://dev.to/ignaoya/the-c-roguelike-tutorial
//
//  Copyright © 2024 Michael. All rights reserved.
//

#ifndef rouge_h
#define rouge_h

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "raylib.h"

//------------------------------------------------------------------------------------
typedef struct
{
    int y;
    int x;
    float realX;
    float realY;
} Position;

typedef struct
{
    char ch;
    int tileType;
    bool walkable;
} Tile;

typedef struct
{
    int height;
    int width;
    Position pos;
    Position center;
} Room;

typedef struct
{
    bool down[8];
    bool pressed[8];
    bool up[8];
    bool released[8];
    KeyboardKey mapping[8];
}InputState;

typedef struct
{
    Position pos;
    float width,height;
    char ch;
    int entityType;
    float movementSpeed;
} Entity;

//------------------------------------------------------------------------------------
//draw.c functions
//------------------------------------------------------------------------------------
Camera2D create2DCamera(void);
void update2DCamera(Camera2D camera);
void drawTile(int x, int y, int tileType);
void drawMap(void);
void drawEntity(Entity* entity);
void clearBeforeDraw(void);
void drawEverything(void);
void drawEverythingWithCamera(Camera2D camera);

//------------------------------------------------------------------------------------
//engine.c functions
//------------------------------------------------------------------------------------
void cursesSetup(void);
InputState* createPlayerControl(void);
void raylibSetup(void);
void gameLoop(void);
void getInput(void);
void closeGame(void);

//------------------------------------------------------------------------------------
//map.c functions
//------------------------------------------------------------------------------------
Tile** createMapTiles(void);
void freeMap(void);
Position setupMap(void);
Position setupRndMap(void);

//------------------------------------------------------------------------------------
// player.c functions
//------------------------------------------------------------------------------------
Entity* createPlayer(Position start_pos);
void handleInput(int input);
void movePlayer(Position newPos);

//------------------------------------------------------------------------------------
// room.c functions
//------------------------------------------------------------------------------------
Room createRoom(int y, int x, int height, int width);
void addRoomToMap(Room room);
void connectRoomCenters(Position centerOne, Position centerTwo);
bool roomOverlaps(Room* rooms, int rooms_counter, int y, int x, int height, int width);

//------------------------------------------------------------------------------------
// externs
//------------------------------------------------------------------------------------
extern const int MAP_HEIGHT;
extern const int MAP_WIDTH;
extern Entity* player;
extern Tile** map;
extern Camera2D camera;

extern int tileSize;
extern const int screenWidth;
extern const int screenHeight;

extern InputState* playerInput;

#endif /* rouge_h */
