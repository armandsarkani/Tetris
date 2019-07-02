package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

class TetrominoI extends Tetromino
{
    final String color = "ltblue";
    int size;
    int rotation = 1;
    int blockwidth = 4;
    int blockheight = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.isquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        int oldrotation = this.rotation;
        if(y < 400 || x < 200)
        {
            return false;
        }
        this.rotation = rotation;
        if(rotation == 1 || rotation == 3)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth())-100)
            {
                Log.e("Heree", "here");
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 1; i < 4; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            blockheight = 1;
            blockwidth = 4;
            return true;
        }
        else if(rotation == 2 || rotation == 4)
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 400))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 1; i < 4; i++)
            {
                if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            blockheight = 4;
            blockwidth = 1;
            return true;
        }
        return false;
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.itetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {
        Bitmap square = GetSquare(context);
        if(rotation == 1 || rotation == 3)
        {
            for(int i = 0; i < 4; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y, 100+x, 100+y);
                canvas.drawBitmap(square, null, dst, null);
                x+=100;
            }
        }
        else if(rotation == 2 || rotation == 4)
        {
            for(int i = 0; i < 4; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y, 100+x, 100+y);
                canvas.drawBitmap(square, null, dst, null);
                y+=100;
            }
        }
    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        if(rotation == 1 || rotation == 3)
        {
            for(int i = 0; i < 4; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x, y); // left most is reference square
                x+=100;
            }
        }
        else if(rotation == 2 || rotation == 4)
        {
            for(int i = 0; i < 4; i++)
            {
                grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x, y+(100*i));
            }
        }
    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        // default startX = 100 for simplicity
        if(rotation == 1 || rotation == 3)
        {
            for(int i = 0; i < 4; i++)
            {
                if(grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] != null)
                {
                    return false;
                }
                x+=100;
            }
            return true;
        }
        else if(rotation == 2 || rotation == 4)
        {
            if(grid.bmp[grid.getX(x, 100)][grid.getY(y+400)] != null)
            {
                return false;
            }
            return true;
        }
        return true;
    }
    public int GetBlockWidth()
    {
        return blockwidth;
    }
    public int GetBlockHeight()
    {
        return blockheight;
    }
}
class TetrominoJ extends Tetromino
{
    final String color = "blue";
    int size;
    int blockwidth = 3;
    int blockheight = 2;
    int rotation = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jsquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        int oldrotation = this.rotation;
        if(y < 400 || x < 200)
        {
            this.rotation = oldrotation;
            return false;
        }
        this.rotation = rotation;
        if(rotation == 1)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x+200, 100)][grid.getY(y+100)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 2;
            blockwidth = 3;
            return true;
        }
        else if(rotation == 2)
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 3;
            blockwidth = 2;
            return true;
        }
        else if(rotation == 3)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            if(grid.bmp[grid.getX(x+200, 100)][grid.getY(y+100)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            blockheight = 2;
            blockwidth = 3;
            return true;
        }
        else
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x, 100)][grid.getY(y+200)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 3;
            blockwidth = 2;
            return true;
        }
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jtetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {
        Bitmap square = GetSquare(context);
        if(rotation == 1)
        {
            Rect org = new Rect();
            org.set(x, y, x+100, 100+y);
            y+= 100;
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y, 100+x, 100+y);
                canvas.drawBitmap(square, null, dst, null);
                x+=100;
            }
        }
        else if(rotation == 2)
        {
            Rect org = new Rect();
            org.set(x, y, x+100, 100+y);
            canvas.drawBitmap(square, null, org, null);
            org.set(x+100, y, x+200, 100+y);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 1; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y+(100*i), 100+x, 100+y+(100*i));
                canvas.drawBitmap(square, null, dst, null);
            }
        }
        else if(rotation == 3)
        {
            Rect org = new Rect();
            org.set(x+200, y+100, x+300, 200+y);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x+(100*i), y, 100+x+(100*i), 100+y);
                canvas.drawBitmap(square, null, dst, null);
            }
        }
        else
        {
            Rect org = new Rect();
            org.set(x, y+200, x+100, 300+y);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x+100, y+(100*i), x+200, 100+y+(100*i));
                canvas.drawBitmap(square, null, dst, null);
            }
        }
    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        if(rotation == 1)
        {
            grid.bmp[grid.getX(x, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x, y); // top is reference square
            y+=100;
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x, y);
                x+=100;
            }
        }
        else if(rotation == 2)
        {
            grid.bmp[grid.getX(x+100, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+100, y); // top is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x, y+(100*i));
            }
        }
        else if(rotation == 3)
        {
            grid.bmp[grid.getX(x+200, startX)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x+200, y+100); // top is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x+(100*i), startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+(100*i), y);
            }
        }
        else
        {
            grid.bmp[grid.getX(x, startX)][grid.getY(y+200)] = new BitmapPackage(GetSquare(context), x, y+200); // top is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x+100, startX)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x+100, y+(100*i));
            }
        }
    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        if(rotation == 1)
        {
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x, startX)][grid.getY(y+200)] != null)
                {
                    return false;
                }
                x+=100;
            }
            return true;
        }
        else if(rotation == 2)
        {
            if(grid.bmp[grid.getX(x, startX)][grid.getY(y+300)] != null)
            {
                return false;
            }
            if(grid.bmp[grid.getX(x+100, startX)][grid.getY(y+100)] != null)
            {
                return false;
            }
            return true;
        }
        else if(rotation == 3)
        {
            for(int i = 0; i < 2; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), startX)][grid.getY(y+100)] != null)
                {
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x+200, startX)][grid.getY(y+200)] != null)
            {
                return false;
            }
            return true;
        }
        else
        {
            for(int i = 0; i < 2; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), startX)][grid.getY(y+300)] != null)
                {
                    return false;
                }
            }
            return true;
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
}
class TetrominoL extends Tetromino
{
    final String color = "orange";
    int size;
    int blockwidth = 3;
    int blockheight = 2;
    int rotation = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lsquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        int oldrotation = this.rotation;
        if(y < 400 || x < 200)
        {
            this.rotation = oldrotation;
            return false;
        }
        this.rotation = rotation;
        if(rotation == 1)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y+100)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x+200, 100)][grid.getY(y)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 2;
            blockwidth = 3;
            return true;
        }
        else if(rotation == 2)
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+200)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 3;
            blockwidth = 2;
            return true;
        }
        else if(rotation == 3)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x, 100)][grid.getY(y+100)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 2;
            blockwidth = 3;
            return true;
        }
        else
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x, 100)][grid.getY(y)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 3;
            blockwidth = 2;
            return true;
        }
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ltetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {
        Bitmap square = GetSquare(context);
        if(rotation == 1)
        {
            Rect org = new Rect();
            org.set(x+200, y, x+300, y+100);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y + 100, x + 100, y + 200);
                canvas.drawBitmap(square, null, dst, null);
                x += 100;
            }
        }
        else if(rotation == 2)
        {
            Rect org = new Rect();
            org.set(x+100, y+200, x+200, y+300);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y + (100*i), x + 100, y + 100 + (100*i));
                canvas.drawBitmap(square, null, dst, null);
            }
        }
        else if(rotation == 3)
        {
            Rect org = new Rect();
            org.set(x, y+100, x+100, y+200);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x+(100*i), y, x + 100 + (100*i), y + 100);
                canvas.drawBitmap(square, null, dst, null);
            }
        }
        else
        {
            Rect org = new Rect();
            org.set(x, y, x+100, y+100);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x+100, y + (100*i), x + 200, y + 100 + (100*i));
                canvas.drawBitmap(square, null, dst, null);
            }
        }
    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        if(rotation == 1)
        {
            grid.bmp[grid.getX(x+200, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+200, y); // top empty space is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x, y+100);
                x+=100;
            }
        }
        else if(rotation == 2)
        {
            grid.bmp[grid.getX(x+100, startX)][grid.getY(y+200)] = new BitmapPackage(GetSquare(context), x+100, y+200); // top empty space is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x, y+(100*i));
            }
        }
        else if(rotation == 3)
        {
            grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x, y+100); // top empty space is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x+(100*i), startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+(100*i), y);
            }
        }
        else
        {
            grid.bmp[grid.getX(x, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x, y); // top empty space is reference square
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x+100, startX)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x+100, y+(100*i));
            }
        }
    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        if(rotation == 1)
        {
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x, startX)][grid.getY(y+200)] != null)
                {
                    return false;
                }
                x+=100;
            }
            return true;
        }
        else if(rotation == 2)
        {
            if(grid.bmp[grid.getX(x, startX)][grid.getY(y+300)] != null)
            {
                return false;
            }
            if(grid.bmp[grid.getX(x+100, startX)][grid.getY(y+300)] != null)
            {
                return false;
            }
            return true;
        }
        else if(rotation == 3)
        {
            for(int i = 1; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), startX)][grid.getY(y+100)] != null)
                {
                    return false;
                }
            }
            if(grid.bmp[grid.getX(x, startX)][grid.getY(y+200)] != null)
            {
                return false;
            }
            return true;
        }
        else
        {
            if(grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] != null)
            {
                return false;
            }
            if(grid.bmp[grid.getX(x+100, startX)][grid.getY(y+300)] != null)
            {
                return false;
            }
            return true;
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
}
class TetrominoO extends Tetromino
{
    final String color = "yellow";
    int size;
    int blockwidth = 2;
    int blockheight = 2;
    int rotation = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.osquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        this.rotation = rotation;
        return false;
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.otetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {
        Bitmap square = GetSquare(context);
        for(int i = 0; i < 2; i++)
        {
            Rect dst = new Rect();
            dst.set(x, y, 100+x, 100+y);
            canvas.drawBitmap(square, null, dst, null);
            x+=100;
        }
        x-=200;
        for(int i = 0; i < 2; i++)
        {
            Rect dst2 = new Rect();
            dst2.set(x, y+100, 100+x, 200+y);
            canvas.drawBitmap(square, null, dst2, null);
            x+=100;
        }
        canvas.save();
        canvas.restore();
    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        for(int i = 0; i < 2; i++)
        {
            grid.bmp[grid.getX(x, startX)][grid.getY(y)] =  new BitmapPackage(GetSquare(context), x, y); // top left is reference square
            x+=100;
        }
        x-=200;
        for(int i = 0; i < 2; i++)
        {
            grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] =  new BitmapPackage(GetSquare(context), x, y+100);
            x+=100;
        }
    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        for(int i = 0; i < 2; i++)
        {
            if(grid.bmp[grid.getX(x, startX)][grid.getY(y+200)]  != null)
            {
                return false;
            }
            x+=100;
        }
        return true;
    }
    public int GetBlockWidth()
    {
        return blockwidth;
    }
    public int GetBlockHeight()
    {
        return blockheight;
    }
}
class TetrominoS extends Tetromino
{
    final String color = "green";
    int size;
    int blockwidth = 3;
    int blockheight = 2;
    int rotation = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ssquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        int oldrotation = this.rotation;
        if(y < 400 || x < 200)
        {
            return false;
        }
        this.rotation = rotation;
        if(rotation == 1 || rotation == 3) // must be coming from rotation 4
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 1; i<3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            for(int i = 0; i<2; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y+100)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            blockheight = 2;
            blockwidth = 3;
            return true;
        }
        else if(rotation == 2 || rotation == 4) // must be coming from rotation 1
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 1; i<3; i++)
            {
                if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            for(int i = 0; i<2; i++)
            {
                if(grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            blockheight = 3;
            blockwidth = 2;
            return true;
        }
        return false;
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.stetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {
        Bitmap square = GetSquare(context);
        if(rotation == 1 || rotation == 3)
        {
            for(int i = 0; i < 2; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y+100, 100+x, y+200);
                canvas.drawBitmap(square, null, dst, null);
                x+=100;
            }
            x-=200;
            for(int i = 0; i < 2; i++)
            {
                Rect dst2 = new Rect();
                dst2.set(x+100, y, x+200, y+100);
                canvas.drawBitmap(square, null, dst2, null);
                x+=100;
            }
        }
        else if(rotation == 2 || rotation == 4)
        {
            for(int i = 0; i < 2; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y+(100*i), 100+x, y+100+(100*i));
                canvas.drawBitmap(square, null, dst, null);
            }
            for(int i = 1; i < 3; i++)
            {
                Rect dst2 = new Rect();
                dst2.set(x+100, y+(100*i), x+200, y+100+(100*i));
                canvas.drawBitmap(square, null, dst2, null);
            }
        }
    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        if(rotation == 1 || rotation == 3)
        {
            for(int i = 0; i < 2; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x, y+100);
                x+=100;
            }
            x-=200;
            for(int i = 0; i < 2; i++)
            {
                grid.bmp[grid.getX(x+100, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+100, y); // top empty space is reference square
                x+=100;
            }
        }
        else if(rotation == 2 || rotation == 4)
        {
            for(int i = 1; i<3; i++)
            {
                grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x+100, y+(100*i)); // do not modify x and y

            }

            for(int i = 0; i<2; i++)
            {
                grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x,y + (100*i));// do not modify x and y

            }
        }
    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        if(rotation == 1 || rotation == 3)
        {
            if(grid.bmp[grid.getX(x+200, startX)][grid.getY(y+100)] != null)
            {
                return false;
            }
            for(int i = 0; i < 2; i++)
            {
                if(grid.bmp[grid.getX(x, startX)][grid.getY(y+200)] != null)
                {
                    return false;
                }
                x+=100;
            }
            return true;
        }
        else if(rotation == 2 || rotation == 4)
        {
            if (grid.bmp[grid.getX(x, startX)][grid.getY(y + 200)] != null) {
                return false;
            }

            if (grid.bmp[grid.getX(x+100, startX)][grid.getY(y + 300)] != null) {
                return false;
            }

            return true;
        }
        return true;
    }
    public int GetBlockWidth()
    {
        return blockwidth;
    }
    public int GetBlockHeight()
    {
        return blockheight;
    }
}
class TetrominoT extends Tetromino
{
    final String color = "pink";
    int size;
    int blockwidth = 3;
    int blockheight = 2;
    int rotation = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tsquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        int oldrotation = this.rotation;
        if(y < 400 || x < 200)
        {
            return false;
        }
        this.rotation = rotation;
        if(rotation == 1)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y+100)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 2;
            blockwidth = 3;
        }
        else if(rotation == 2)
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 3;
            blockwidth = 2;
        }
        else if(rotation == 3)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+100)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 2;
            blockwidth = 3;
        }
        else
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            if(grid.bmp[grid.getX(x, 100)][grid.getY(y+100)] != null) // do not modify x and y
            {
                this.rotation = oldrotation;
                return false;
            }
            blockheight = 3;
            blockwidth = 2;
        }
        return false;
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ttetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y)
    {
        Bitmap square = GetSquare(context);
        if(rotation == 1)
        {
            Rect org = new Rect();
            org.set(x+100, y, x+200, y+100);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y+100, x+100, y+200);
                canvas.drawBitmap(square, null, dst, null);
                x+=100;
            }
        }
        else if(rotation == 2)
        {
            Rect org = new Rect();
            org.set(x+100, y+100, x+200, y+200);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x, y+(100*i), x+100, y+(100*i)+100);
                canvas.drawBitmap(square, null, dst, null);
            }
        }
        else if(rotation == 3)
        {
            Rect org = new Rect();
            org.set(x+100, y+100, x+200, y+200);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x+(100*i), y, x+100+(100*i), y+100);
                canvas.drawBitmap(square, null, dst, null);
            }
        }
        else
        {
            Rect org = new Rect();
            org.set(x, y+100, x+100, y+200);
            canvas.drawBitmap(square, null, org, null);
            for(int i = 0; i < 3; i++)
            {
                Rect dst = new Rect();
                dst.set(x+100, y+(100*i), x+200, y+(100*i)+100);
                canvas.drawBitmap(square, null, dst, null);
            }
        }

    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        if(rotation == 1)
        {
            grid.bmp[grid.getX(x+100, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+100, y);
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x, y+100); // top empty space is reference square
                x+=100;
            }
        }
        else if(rotation == 2)
        {
            for(int i = 0; i< 3; i++)
            {
                grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x, y+(100*i));// do not modify x and y

            }
            grid.bmp[grid.getX(x+100, 100)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x+100, y+100); // do not modify x and y
        }
        else if(rotation == 3)
        {
            for(int i = 0; i< 3; i++)
            {
                grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x+(100*i), y);// do not modify x and y

            }
            grid.bmp[grid.getX(x+100, 100)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context), x+100, y+100); // do not modify x and y
        }
        else
        {
            for(int i = 0; i < 3; i++)
            {
                grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x+100, y+(100*i));// do not modify x and y
            }

            grid.bmp[grid.getX(x, 100)][grid.getY(y+100)] = new BitmapPackage(GetSquare(context),x,y+100); // do not modify x and y
        }

    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        if(rotation == 1)
        {
            for(int i = 0; i < 3; i++)
            {
                if(grid.bmp[grid.getX(x, startX)][grid.getY(y+200)] != null)
                {
                    return false;
                }
                x+=100;
            }
            return true;
        }
        else if(rotation == 2)
        {
            if (grid.bmp[grid.getX(x, startX)][grid.getY(y + 300)] != null) {
                return false;
            }
            if (grid.bmp[grid.getX(x+100, startX)][grid.getY(y + 200)] != null) {
                return false;
            }
            return true;
        }
        else if(rotation == 3)
        {
            if(grid.bmp[grid.getX(x, startX)][grid.getY(y+100)] != null)
            {
                return false;
            }
            if(grid.bmp[grid.getX(x+100, startX)][grid.getY(y+200)] != null)
            {
                return false;
            }
            if(grid.bmp[grid.getX(x+200, startX)][grid.getY(y+100)] != null)
            {
                return false;
            }
            return true;
        }
        else
        {
            if (grid.bmp[grid.getX(x, startX)][grid.getY(y + 200)] != null) {
                return false;
            }

            if (grid.bmp[grid.getX(x+100, startX)][grid.getY(y + 300)] != null) {
                return false;
            }
            return true;
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
}
class TetrominoZ extends Tetromino
{
    final String color = "red";
    int size;
    int blockwidth = 3;
    int blockheight = 2;
    int rotation = 1;
    public Bitmap GetSquare(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.zsquare);
        return bitmap;
    }
    public boolean TryRotation(int rotation, int x, int y, Canvas canvas, Context context, Grid grid)
    {
        int oldrotation = this.rotation;
        if(y < 400 || x < 200)
        {
            return false;
        }
        this.rotation = rotation;
        if(rotation == 1 || rotation == 3)
        {
            if(x+300 >= grid.getNormalizedX(canvas.getWidth()-100))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i<2; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }
            for(int i = 1; i<3; i++)
            {
                if(grid.bmp[grid.getX(x+(100*i), 100)][grid.getY(y+100)] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            blockheight = 2;
            blockwidth = 3;
            return true;
        }
        else if(rotation == 2 || rotation == 4)
        {
            if(y+300 >= grid.getNormalizedY(canvas.getHeight() - 300))
            {
                this.rotation = oldrotation;
                return false;
            }
            for(int i = 0; i<2; i++)
            {
                if(grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            for(int i = 1; i<3; i++)
            {
                if(grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] != null) // do not modify x and y
                {
                    this.rotation = oldrotation;
                    return false;
                }
            }

            blockheight = 3;
            blockwidth = 2;
            return true;
        }
        return false;
    }
    public int GetRotation()
    {
        return rotation;
    }
    public Bitmap GetTetromino(Context context)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ztetromino);
        return bitmap;
    }
    public void DrawTetromino(Canvas canvas, Context context, int x, int y) {
        Bitmap square = GetSquare(context);
        if(rotation == 1 || rotation == 3)
        {
            for (int i = 0; i < 2; i++) {
                Rect dst = new Rect();
                dst.set(x, y, x + 100, y + 100);
                canvas.drawBitmap(square, null, dst, null);
                x += 100;
            }

            x -= 200;
            for (int i = 0; i < 2; i++) {
                Rect dst2 = new Rect();
                dst2.set(x + 100, y + 100, x + 200, y + 200);
                canvas.drawBitmap(square, null, dst2, null);
                x += 100;
            }
        }

        else if(rotation == 2 || rotation == 4)
        {
            for (int i = 0; i < 2; i++) {
                Rect dst = new Rect();
                dst.set(x+100, y+(100*i), x + 200, y + (100*i) + 100);
                canvas.drawBitmap(square, null, dst, null);
            }

            for (int i = 1; i < 3; i++) {
                Rect dst2 = new Rect();
                dst2.set(x, y + (100*i), x + 100, y + (100*i)+100);
                canvas.drawBitmap(square, null, dst2, null);

            }
        }

    }
    public void set(Grid grid, Context context, int x, int y, int startX)
    {
        if(rotation == 1 || rotation == 3)
        {
            for (int i = 0; i < 2; i++) {
                grid.bmp[grid.getX(x, startX)][grid.getY(y)] = new BitmapPackage(GetSquare(context), x, y); // top is empty square
                x += 100;
            }
            x -= 200;
            for (int i = 0; i < 2; i++) {
                grid.bmp[grid.getX(x + 100, startX)][grid.getY(y + 100)] = new BitmapPackage(GetSquare(context), x + 100, y + 100);
                x += 100;
            }
        }

        else if(rotation == 2 || rotation == 4)
        {
            for(int i = 0; i<2; i++)
            {
                grid.bmp[grid.getX(x+100, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x+100, y+(100*i)); // do not modify x and y

            }

            for(int i = 1; i<3; i++)
            {
                grid.bmp[grid.getX(x, 100)][grid.getY(y+(100*i))] = new BitmapPackage(GetSquare(context), x,y + (100*i));// do not modify x and y

            }
        }

    }
    public boolean DetermineSafe(Grid grid, int x, int y, int startX)
    {
        if(rotation == 1 || rotation == 3) {

            if (grid.bmp[grid.getX(x, startX)][grid.getY(y + 100)] != null) {
                return false;
            }
            for (int i = 0; i < 2; i++) {
                if (grid.bmp[grid.getX(x + 100, startX)][grid.getY(y + 200)] != null) {
                    return false;
                }
                x += 100;
            }
            return true;
        }

        if(rotation == 2 || rotation == 4) {

            if (grid.bmp[grid.getX(x, startX)][grid.getY(y + 300)] != null) {
                return false;
            }

            if (grid.bmp[grid.getX(x+100, startX)][grid.getY(y + 200)] != null) {
                return false;
            }

            return true;
        }

        return true;
    }
    public int GetBlockWidth()
    {
        return blockwidth;
    }
    public int GetBlockHeight()
    {
        return blockheight;
    }
}