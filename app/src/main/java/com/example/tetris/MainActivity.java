package com.example.tetris;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int doublercount = 0;
    MediaPlayer mySong;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    BoardView bv;
    TextView score, scoreheader, highscore, tetris;
    RelativeLayout rl;
    Tetromino current;
    int activity = 0;
    Grid grid;
    int x, y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        mySong = MediaPlayer.create(this, R.raw.theme);
        final Button button = findViewById(R.id.restart);
        final Button sound = findViewById(R.id.sound);
        final Button darkmode = findViewById(R.id.darkmode);
        final Button refresh = findViewById(R.id.refresh);

        if (sharedPref.getInt("Sound", 1) == 1) {
            mySong.start();
            mySong.setLooping(true);
            mySong.setVolume(1, 1);
            sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.soundon, 0, 0, 0);
        } else {
            mySong.start();
            mySong.setLooping(true);
            mySong.setVolume(0, 0);
            sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
        }
        bv = findViewById(R.id.board);
        score = findViewById(R.id.score);
        tetris = findViewById(R.id.textView);
        scoreheader = findViewById(R.id.scoreheader);
        highscore = findViewById(R.id.highscore);
        rl = findViewById(R.id.rel);
        if (sharedPref.getInt("Dark", 0) == 1) {
            getWindow().setStatusBarColor(Color.BLACK);
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            rl.setBackgroundColor(Color.BLACK);
            bv.setBackgroundColor(Color.BLACK);
            score.setTextColor(Color.WHITE);
            scoreheader.setTextColor(Color.WHITE);
            tetris.setTextColor(Color.WHITE);
            highscore.setTextColor(Color.WHITE);
            bv.SetPaint(1);

        } else if (sharedPref.getInt("Dark", 0) == 0) {
            getWindow().setStatusBarColor(Color.WHITE);
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            bv.setBackgroundColor(Color.WHITE);
            rl.setBackgroundColor(Color.WHITE);
            score.setTextColor(Color.BLACK);
            scoreheader.setTextColor(Color.BLACK);
            tetris.setTextColor(Color.rgb(3, 169, 244));
            highscore.setTextColor(Color.BLACK);
            bv.SetPaint(0);

        }
        sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sharedPref.getInt("Sound", 1) == 1) {
                    editor.putInt("Sound", 0);
                    editor.apply();
                    sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mute, 0, 0, 0);
                    mySong.setVolume(0, 0);
                } else {
                    editor.putInt("Sound", 1);
                    editor.apply();
                    sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.soundon, 0, 0, 0);
                    mySong.setVolume(1, 1);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doublercount = 0;
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
        refresh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (doublercount == 0) {
                    Toast secretmode = Toast.makeText(bv.getContext(), "You have unlocked the secret score booster!", Toast.LENGTH_SHORT);
                    TextView score = findViewById(R.id.score);
                    String s = (String) score.getText();
                    int sc = Integer.parseInt(s);
                    if(sc == 0)
                    {
                        sc+= 10;
                    }
                    else
                    {
                        sc += (new Random().nextInt(sc * 3));
                    }
                    BoardView bv = findViewById(R.id.board);
                    bv.currentscore = sc;
                    String newscore = Integer.toString(sc);
                    score.setText(newscore);
                    secretmode.show();
                    doublercount++;
                    return true;
                } else {
                    Toast t = Toast.makeText(bv.getContext(), "Nice try. You think we'd let you get away with using the secret score booster more than once?", Toast.LENGTH_SHORT);
                    t.show();
                    return true;
                }

            }
        });
        darkmode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sharedPref.getInt("Dark", 0) == 0) {
                    editor.putInt("Dark", 1);
                    editor.apply();
                    getWindow().setStatusBarColor(Color.BLACK);
                    View view = getWindow().getDecorView();
                    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    rl.setBackgroundColor(Color.BLACK);
                    bv.setBackgroundColor(Color.BLACK);
                    score.setTextColor(Color.WHITE);
                    scoreheader.setTextColor(Color.WHITE);
                    tetris.setTextColor(Color.WHITE);
                    highscore.setTextColor(Color.WHITE);
                    bv.SetPaint(1);

                } else if (sharedPref.getInt("Dark", 0) == 1) {
                    editor.putInt("Dark", 0);
                    editor.apply();
                    getWindow().setStatusBarColor(Color.WHITE);
                    View view = getWindow().getDecorView();
                    view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    bv.setBackgroundColor(Color.WHITE);
                    rl.setBackgroundColor(Color.WHITE);
                    score.setTextColor(Color.BLACK);
                    scoreheader.setTextColor(Color.BLACK);
                    tetris.setTextColor(Color.rgb(3, 169, 244));
                    highscore.setTextColor(Color.BLACK);
                    bv.SetPaint(0);

                }
            }
        });

    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(bv != null)
        {
            bv.currentscore = 0;
            bv.UpdateScore(0);
        }
        mySong.pause();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mySong.start();

    }
}