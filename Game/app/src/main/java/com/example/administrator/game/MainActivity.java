package com.example.administrator.game;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private wuziview mGamePanel;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //游戏结束时弹出对话框
        alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGamePanel.restartGame();
            }
        });
        alertBuilder.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle("此局结束");

        mGamePanel = (wuziview) findViewById(R.id.wuziview);
        mGamePanel.setOnGameStatusChangeListener(new OnGameStatusChangeListener() {
            @Override
            public void onGameOver(int gameWinResult) {
                switch (gameWinResult) {
                    case wuziview.WHITE_WIN:
                        alertBuilder.setMessage("白棋胜利!");
                        break;
                    case wuziview.BLACK_WIN:
                        alertBuilder.setMessage("黑棋胜利!");
                        break;
                    case wuziview.NO_WIN:
                        alertBuilder.setMessage("和棋!");
                        break;
                }
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });


    }
}
