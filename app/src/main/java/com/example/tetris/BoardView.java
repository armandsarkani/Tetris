package com.example.tetris;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("Duplicates")
public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint;
    private Paint end;
    boolean safe = true;
    int ignore;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    TetrisThread th;
    TextView score;
    Context context;
    Grid grid;
    Canvas c;
    Button refresh;
    int rx1 = 100, ry1 = 60, rx2 = 0, ry2 = 0;
    int y = 300;
    int offset;
    int startX = 0;
    Bitmap bitmap;
    Tetromino current;
    Tetromino nexttetromino;
    Bitmap next;
    int z = 0;
    int iterations = 0;
    int currentscore = 0;
    private SurfaceHolder sh;
    GestureDetectorCompat gs = null;
    public BoardView(Context context) {
        super(context);
        this.score = score;
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.rgb(234, 236, 239));
        this.setZOrderOnTop(true);
        sh = getHolder();
        sh.addCallback(this);
        setFocusable(true);
    }
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gs = new GestureDetectorCompat(getContext(), new GestureListener());
        this.score = score;
        this.context = context;
        paint = new Paint();
        end = new Paint();
        paint.setColor(Color.rgb(234, 236, 239));
        this.setZOrderOnTop(true);
        sh = getHolder();
        sh.addCallback(this);
        setFocusable(true);
    }
    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.score = score;
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.rgb(234, 236, 239));
        this.setZOrderOnTop(true);
        sh = getHolder();
        sh.addCallback(this);
        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        grid = new Grid(getWidth(), getHeight());
        sharedPref = GetActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        UpdateHighScore(sharedPref.getInt("High score", 0));
        z = grid.getNormalizedX((getWidth() / 3) + 200);
        startX = 100;
        Tetromino init = new Tetromino(context);
        bitmap = init.GetRandomTetromino();
        current = init.current;
        TetrisThread th = new TetrisThread(this);
        refresh = GetActivity().findViewById(R.id.refresh);

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        c = canvas;
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gs.onTouchEvent(event);
                return true;
            }
        });
        canvas.drawRoundRect(rx1-40, ry1-40, rx2+40, ry2+40, 40, 40, paint);
        Rect r = new Rect();
        if(current.num == 0)
        {
            offset = 100*(current.GetBlockHeight());
        }
        else
        {
            offset = 100*(current.GetBlockHeight());
        }
        r.set(rx1, ry1, rx2, ry2);
        if(iterations == 0)
        {
            SetNextTetromino(canvas, r);
        }
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Tetromino T = new Tetromino(context);
                Bitmap b = T.GetRandomTetromino();
                current = T.current;
                y = 300;
                z = grid.getNormalizedX((getWidth() / 3) + 200);
                iterations = 0;
            }
        });
        safe = current.DetermineSafe(grid, z, y, startX);
        if(y < getHeight()-offset && safe == true)
        {
            final int result = grid.checkRowsandGetScore();
            if(result > 0)
            {
                grid.setChangedGrid();
                grid.drawGrid(canvas);
                currentscore+= (result*1000);
                String text = "Score boost! You have eliminated " + result + " row(s)!";
                Toast power = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                power.show();
                UpdateScore(currentscore);
            }
            if(iterations >= 50 && iterations < 100)
            {
                y+=6;
            }
            else if(iterations >= 100 && iterations < 200)
            {
                y+=7;
            }
            else if(iterations >=200)
            {
                y+=10;
            }
            else
            {
                y+=5;
            }
        }
        else
        {
            if(grid.getNormalizedY(y) <= 400) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext(), R.style.MyAlert);
                builder1.setTitle("Game over!");
                int highscore = sharedPref.getInt("High score", 0);
                if(highscore == 0)
                {
                    editor.putInt("High score", currentscore);
                    highscore = currentscore;
                    editor.apply();
                    builder1.setMessage("Your score was " + currentscore + ". Your all-time high score is " + highscore + ". Tap restart to play again.");
                    UpdateHighScore(currentscore);
                }
                else if(currentscore > highscore)
                {
                    editor.putInt("High score", currentscore);
                    editor.apply();
                    builder1.setMessage("Your score was " + currentscore + ". You beat your previous high score of " + highscore + "! Tap restart to play again.");
                    UpdateHighScore(currentscore);
                }
                else
                {
                    builder1.setMessage("Your score was " + currentscore + ". Your all-time high score is " + highscore + ". Tap restart to play again.");
                }
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Restart",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = GetActivity().getIntent();
                                MainActivity ma = (MainActivity) GetActivity();
                                ma.mySong.stop();
                                GetActivity().overridePendingTransition(0, 0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                GetActivity().finish();
                                GetActivity().overridePendingTransition(0, 0);
                                GetActivity().startActivity(intent);
                            }
                        });
                builder1.setNegativeButton(
                        "Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GetActivity().finish();
                                System.exit(10);
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                ignore = 1;
                c.drawRect(0, 0, 500, 500, end);
            }
            else
            {
                iterations = 0;
                currentscore = currentscore + 40 + grid.GetNumSquares()/2;
                current.set(grid, getContext(), grid.getNormalizedX(z), grid.getNormalizedY(y), startX);
                UpdateScore(currentscore);
                bitmap = next;
                current = nexttetromino;
                y = 300;
                z = grid.getNormalizedX((getWidth()/3) + 200);
                draw(canvas);
            }

        }
        if(ignore == 0)
        {
            current.DrawTetromino(canvas, context, z, y);
            grid.drawGrid(canvas);
            canvas.drawBitmap(next, null, r, null);
            iterations++;
            invalidate();
        }
    }
    public void SetNextTetromino(Canvas canvas, Rect r) {
        Tetromino t = new Tetromino(getContext());
        next = t.GetRandomTetromino();
        nexttetromino = t.current;
        if (t.num == 0) {
            rx2 = 380;
            ry2 = 130;
        } else if (t.num == 3) {
            rx2 = 240;
            ry2 = 200;
        } else {
            rx2 = 300;
            ry2 = 200;
        }
        r.set(rx1, ry1, rx2, ry2);
        canvas.drawBitmap(next, null, r, null);
    }
    public Activity GetActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }
    public void UpdateScore(int score)
    {
        String sc = Integer.toString(score);
        TextView t = GetActivity().findViewById(R.id.score);
        t.setText(sc);
    }
    public void UpdateHighScore(int highscore)
    {
        String sc = Integer.toString(highscore);
        TextView t = GetActivity().findViewById(R.id.highscore);
        String text = "High " + sc;
        t.setText(text);
    }
    public boolean RotateTetromino()
    {
        if(current.GetRotation() < 4)
        {
            return current.TryRotation(current.GetRotation() + 1, z, y, c, getContext(), grid);
        }
        else if(current.GetRotation() == 4)
        {
            return current.TryRotation(1, z, y, c, getContext(), grid);
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        return true;
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }
    public Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    @Override
    public boolean onKeyUp(int k, KeyEvent event )
    {
        switch(k)
        {
            case KeyEvent.KEYCODE_DPAD_UP:
            {
                if(current.num != 3 && y < getHeight())
                {
                    boolean rotation = RotateTetromino();
                }
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT:
            {
                if(z-100 < 100)
                {
                    z-=0;
                }
                else if(current.DetermineSafe(grid, z-100, y, startX) == false)
                {
                    z-=0;
                }
                else
                {
                    for(int i = grid.getY(y); i < grid.getY(y)+current.blockheight; i++)
                    {
                        if(grid.bmp[grid.getX(z-100, 100)][i] !=null)
                        {
                            return true;
                        }
                    }
                    z -= 100;
                }
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            {
                if(z+(100*current.GetBlockWidth())+100 > grid.getNormalizedX(getWidth())-100)
                {
                    z+=0;
                }
                else if(current.DetermineSafe(grid, z+100, y, startX) == false)
                {
                    z+= 0;
                }
                else
                {
                    for(int i = grid.getY(y); i < grid.getY(y)+current.blockheight; i++)
                    {
                        if(grid.bmp[grid.getX(z+(100*current.GetBlockWidth()), 100)][i] !=null)
                        {
                            return true;
                        }
                    }
                    z += 100;
                }
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN:
            {
                if(y+400 > grid.getNormalizedY(getHeight())-offset || y+300 > grid.getNormalizedY(getHeight())-offset || y+200 > grid.getNormalizedY(getHeight())-offset || y+100 > grid.getNormalizedY(getHeight())-offset)
                {
                    y+= 0;
                }
                else if(current.DetermineSafe(grid, z, y+300, startX) == false || current.DetermineSafe(grid, z, y+200, startX) == false || current.DetermineSafe(grid, z, y+100, startX) == false )
                {
                    safe = false;
                }
                else
                {
                    y+= 400;
                    safe = current.DetermineSafe(grid, z, y, startX);
                }
                return true;
            }
        }
        return true;
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        // Minimal x and y axis swipe distance.
        private int minX = 300;
        private int minY = 300;

        // Maximal x and y axis swipe distance.
        private int maxX = 1000;
        private int maxY = 1000;

        private MainActivity activity = null;

        public MainActivity getActivity() {
            return activity;
        }

        public void setActivity(MainActivity activity) {
            this.activity = activity;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float deltaX = e1.getX() - e2.getX();

            float deltaY = e1.getY() - e2.getY();

            float deltaXAbs = Math.abs(deltaX);
            float deltaYAbs = Math.abs(deltaY);

            if((deltaXAbs >= minX) && (deltaXAbs <= maxX))
            {
                if(deltaX > 0) // swipe left
                {
                    if(z-100 < 100)
                    {
                        z-=0;
                    }
                    else if(current.DetermineSafe(grid, z-100, y, startX) == false)
                    {
                        z-=0;
                    }
                    else
                    {
                        for(int i = grid.getY(y); i < grid.getY(y)+current.blockheight; i++)
                        {
                            if(grid.bmp[grid.getX(z-100, 100)][i] !=null)
                            {
                                return true;
                            }
                        }
                        z -= 100;
                    }
                }
                else // swipe right
                {
                    if(z+(100*current.GetBlockWidth())+100 > grid.getNormalizedX(getWidth())-100)
                    {
                        z+=0;
                    }
                    else if(current.DetermineSafe(grid, z+100, y, startX) == false)
                    {
                        z+= 0;
                    }
                    else
                    {
                        for(int i = grid.getY(y); i < grid.getY(y)+current.blockheight; i++)
                        {
                            if(grid.bmp[grid.getX(z+(100*current.GetBlockWidth()), 100)][i] !=null)
                            {
                                return true;
                            }
                        }
                        z += 100;
                    }
                }
            }

            if((deltaYAbs >= minY) && (deltaYAbs <= maxY))
            {
                if(deltaY > 0) // swipe up
                {
                    if(current.num != 3 && y < getHeight())
                    {
                        boolean rotation = RotateTetromino();
                    }
                }
                else // swipe down
                {
                    if(y+400 > grid.getNormalizedY(getHeight())-offset || y+300 > grid.getNormalizedY(getHeight())-offset || y+200 > grid.getNormalizedY(getHeight())-offset || y+100 > grid.getNormalizedY(getHeight())-offset)
                    {
                        y+= 0;
                    }
                    else if(current.DetermineSafe(grid, z, y+300, startX) == false || current.DetermineSafe(grid, z, y+200, startX) == false || current.DetermineSafe(grid, z, y+100, startX) == false )
                    {
                        safe = false;
                    }
                    else
                    {
                        y+= 400;
                        safe = current.DetermineSafe(grid, z, y, startX);
                    }
                }
            }
            return true;

        }
    }
    public void SetPaint(int dark)
    {
        if(dark == 0)
        {
            paint.setColor(Color.rgb(234, 236, 239));
            end.setColor(Color.WHITE);
        }
        if(dark == 1)
        {
            paint.setColor(Color.rgb(239, 244, 252));
            end.setColor(Color.BLACK);

        }
    }
}