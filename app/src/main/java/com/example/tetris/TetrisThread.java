package com.example.tetris;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class TetrisThread extends Thread
{
    BoardView bv;
    private Canvas c;
    public TetrisThread(BoardView bv)
    {
        this.bv = bv;
    }
    public void run()
    {
        SurfaceHolder sh = bv.getHolder();
        while(!Thread.interrupted())
        {
            c = sh.lockCanvas(null);
            try
            {
                synchronized(sh)
                {
                    bv.draw(c);
                }

            }
            catch(Exception e)
            {

            }
            finally
            {
               if(c != null)
               {
                   sh.unlockCanvasAndPost(c);
               }
            }
            try
            {
                Thread.sleep(5);

            }
            catch(InterruptedException e)
            {
               return;
            }
        }
    }

}
