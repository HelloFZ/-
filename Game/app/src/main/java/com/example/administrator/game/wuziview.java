package com.example.administrator.game;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/2 0007.
 */

public class wuziview extends View {
    private int mPanelWidth ;       //棋盘宽度
    private float mLineHeight;      //棋盘单行间距
    private int MAX_LINE = 11;//棋盘行列数
    private int MAX_COUNT_IN_LINE = 5;    //多少颗棋子相邻时赢棋

    private Bitmap mWhitePiece;     //白棋的图片
    private Bitmap mBlackPiece;     //黑棋的图片

   // private int mPanelLineColor;    //棋盘线的颜色
    private Paint mPaint = new Paint();

    private ArrayList<Point> mWhitePieceArray = new ArrayList<>();
    //已下的黑棋的列表
    private ArrayList<Point> mBlackPieceArray = new ArrayList<>();

    //棋子占行距的比例
    private final float RATIO_PIECE_OF_LINE_HEIGHT = 3 * 1.0f / 4;

    //是否将要下白棋
    private boolean mIsWhite = true;
    //游戏是否结束
    private boolean mIsGameOver = false;

    private final int INIT_WIN = -1;            //游戏开始时的状态
    public static final int WHITE_WIN = 0;      //白棋赢
    public static final int BLACK_WIN = 1;      //黑棋赢
    public static final int NO_WIN = 2;         //和棋

    private int mGameWinResult = INIT_WIN;      //初始化游戏结果

    //private OnGameStatusChangeListener listener;//游戏状态监听器
    public wuziview(Context context) {
        this(context,null);
    }

    public wuziview(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public wuziview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);


        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        setMeasuredDimension(width, width);
    }

    //初始化游戏数据
    private void init() {
        //mPaint.setColor(mPanelLineColor);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setStyle(Paint.Style.FILL);
        if (mWhitePiece == null) {
            mWhitePiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_w2);
        }
        if (mBlackPiece == null) {
            mBlackPiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_b1);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * RATIO_PIECE_OF_LINE_HEIGHT);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);

    }


    //绘制棋盘
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0;i < MAX_LINE; i ++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);//画横线
            canvas.drawLine(y, startX, y, endX, mPaint);//画竖线
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getValidPoint(x, y);
            if (mWhitePieceArray.contains(p) || mBlackPieceArray.contains(p)) {
                return false;
            }

            if (mIsWhite) {
                mWhitePieceArray.add(p);
            } else {
                mBlackPieceArray.add(p);
            }
            invalidate();
            mIsWhite = !mIsWhite;
            return true;
        }
        return true;
    }

    //根据触摸点获取最近的格子位置
    private Point getValidPoint(int x, int y) {
        return new Point((int)(x / mLineHeight),(int)(y / mLineHeight));
    }


}



