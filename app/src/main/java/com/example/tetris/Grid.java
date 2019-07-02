package com.example.tetris;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

class Grid
{
    BitmapPackage[][] bmp;
    BitmapPackage[][] temp;
    int cols, rows;
    boolean emptyrows[];
    Grid(int screenWidth, int screenHeight)
    {
        cols = ((screenWidth-200)/100);
        rows = (screenHeight/100);
        bmp = new BitmapPackage[cols][rows];
        temp = new BitmapPackage[cols][rows];
    }
    public int getX(int x, int startX) // each block is 100px on each side
    {
        int location = x-startX;
        return location/100;
    }
    public int getY(int y)
    {
        int location = y-300;
        return location/100;
    }
    public int getNormalizedX(int x)
    {
        int normalizedX = x/100;
        normalizedX *= 100;
        return normalizedX;
    }
    public int getNormalizedY(int y)
    {
        int normalizedY = y/100;
        normalizedY *= 100;
        return normalizedY;
    }
    public BitmapPackage elementAt(int x, int y, int startX)
    {
        if(bmp[getX(x, startX)][getY(y)] != null)
        {
            return bmp[getX(x, startX)][getY(y)];
        }
        return null;
    }
    public int checkRowsandGetScore()
    {
        for(int i = 0; i < cols; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                temp[i][j] = bmp[i][j];
            }
        }
        int counter[] = new int[rows];
        emptyrows = new boolean [rows];
        int numrows = 0;
        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                if(bmp[col][row] != null)
                {
                    counter[row]++;
                }
            }
        }
        for(int i = 0; i < rows; i++)
        {
            if(counter[i] == cols)
            {
                emptyrows[i] = true;
                numrows++;
                for(int j = 0; j < cols; j++)
                {
                    bmp[j][i] = null;
                }
            }
        }
        return numrows;
    }
    public void setChangedGrid()
    {
        for(int i = 0; i < rows; i++)
        {
            if(emptyrows[i] == true)
            {
                for(int c = 0; c < cols; c++)
                {
                    for(int n = 1; n <= i; n++)
                    {
                        bmp[c][n] = temp[c][n-1];
                        if(bmp[c][n] != null)
                        {
                            bmp[c][n].y = (temp[c][n-1].y)+100;
                        }
                    }
                }
            }
            for(int a = 0; a < cols; a++)
            {
                for(int b = 0; b < rows; b++)
                {
                    temp[a][b] = bmp[a][b];
                }
            }
        }
    }
    public void drawGrid(Canvas canvas) // draw updated one too
    {
        Rect r = new Rect();
        for(int i = 0; i < cols; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                if(bmp[i][j] != null)
                {
                    r.set(bmp[i][j].x, bmp[i][j].y, bmp[i][j].x + 100, bmp[i][j].y + 100);
                    canvas.drawBitmap(bmp[i][j].bitmap, null, r, null);
                }
            }
        }
    }
    public int GetNumSquares()
    {
        int counter = 0;
        int s = 0;
        for(int i = 0; i < cols; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                if(bmp[i][j] != null)
                {
                    counter++;
                    s++;
                }
            }
            if(s >= 8)
            {
                counter+= 40;
                s = 0;
            }
        }
        return counter;
    }
}
class BitmapPackage
{
    Bitmap bitmap;
    int x;
    int y;
    BitmapPackage(Bitmap bitmap, int x, int y)
    {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }
}