package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Tetromino
{
    Bitmap b;
    Context context;
    Tetromino current;
    int num;
    int blockwidth = 3;
    int blockheight = 2;
    int rotation = 1;
    public Tetromino()
    {

    }
    public Tetromino(Context context)
    {
        this.context = context;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {

    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {

    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        return false;
    }
    public int GetRotation()
    {
        return rotation;
    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        return false;
    }
    public Bitmap GetRandomTetromino()
    {
        Random r = new Random();
        num = r.nextInt(7);
        switch(num)
        {
            case 0:
                TetrominoI i = new TetrominoI();
                current = i;
                current.num = num;
                return i.GetTetromino(context);
            case 1:
                TetrominoJ j = new TetrominoJ();
                current = j;
                current.num = num;
                return j.GetTetromino(context);
            case 2:
                TetrominoL l = new TetrominoL();
                current = l;
                current.num = num;
                return l.GetTetromino(context);
            case 3:
                TetrominoO o = new TetrominoO();
                current = o;
                current.num = num;
                return o.GetTetromino(context);
            case 4:
                TetrominoS s = new TetrominoS();
                current = s;
                current.num = num;
                return s.GetTetromino(context);
            case 5:
                TetrominoT t = new TetrominoT();
                current = t;
                current.num = num;
                return t.GetTetromino(context);
            case 6:
                TetrominoZ z = new TetrominoZ();
                current = z;
                current.num = num;
                return z.GetTetromino(context);
            default:
                TetrominoZ def = new TetrominoZ();
                current = def;
                current.num = 6;
                return def.GetTetromino(context);
        }
    }
    public int GetBlockWidth()
    {
        return blockwidth;
    }
    public int GetBlockHeight()
    {
        return blockheight;
    }
    public Tetromino GetCurrentTetromino()
    {
        return current;
    }
}
