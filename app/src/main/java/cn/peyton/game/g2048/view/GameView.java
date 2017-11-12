package cn.peyton.game.g2048.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

import cn.peyton.game.g2048.activity.Game2048Activity;
import cn.peyton.game.g2048.bean.Card;
import cn.peyton.game.g2048.common.Config;

/**
 * <span style="color:red;font: 16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;">
 * </span>
 * <pre>
 * 作者: <a href="http://www.peyton.cn">peyton</a>
 * 邮箱: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * 创建时间: 2017-11-12 11:23
 * </pre>
 */

public class GameView extends GridLayout {
    /**构造*/
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gameView = this;
        initGameView();
        createJudge();
    }
    /**构造*/
    public GameView(Context context) {
        super(context);
        gameView = this;
        initGameView();
        createJudge();
    }
    /**构造*/
    public GameView(Context context,AttributeSet attrs) {
        super(context,attrs);
        gameView = this;
        initGameView();
        createJudge();
    }



    @SuppressLint("ClickableViewAccessibility")
    private void initGameView() {
        setColumnCount(4);//指明有多少列
        setBackgroundColor(0xffbbada0); //设置背景颜色
        setOnTouchListener(new InsideTouchListener()); //设置屏幕监听事件
    }

    @SuppressLint("ClickableViewAccessibility")
    private class InsideTouchListener implements View.OnTouchListener{

        private float startX,startY,offsetX,offsetY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    offsetX = event.getX() - startX;
                    offsetY = event.getY() - startY;
                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        if (offsetX < -5) {
                            System.out.println("left");
                            swipeLeft();
                        }else if (offsetX > 5) {
                            System.out.println("right");
                            swipeRight();
                        }
                    }else {
                        if (offsetY < -5) {
                            System.out.println("up");
                            swipeUp();
                        }else if (offsetY > 5) {
                            System.out.println("down");
                            swipeDown();
                        }
                    }
                    break;
            }
            return true;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = w / 4 - 2;
        addCards(cardWidth,cardWidth);
        startGame();
    }

    //添加卡片到屏幕
    private void addCards(int cardWidth,int cardHeight){
        Card card;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                card = new Card(getContext());
                card.setNum(0);
                addView(card,cardWidth,cardHeight);
                cardMap[x][y] = card;
            }
        }
    }

    //向左动作
    private void swipeLeft(){
        eval();//先把赋值给临时数组用来判断卡片有动过
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x+1; x1 < 4; x1++) {
                    if (cardMap[x1][y].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x--;

                        }else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());

                        }
                        break;
                    }
                }
            }
        }
        if (eq()){
            Game2048Activity.getGame2048Activity().addStep(++ step);
            addRandomNum();
            checkComplete();
        }

    }

    //向右动作
    private void swipeRight(){
        eval();//先把赋值给临时数组用来判断卡片有动过
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {

                for (int x1 = x-1; x1 >= 0; x1--) {
                    if (cardMap[x1][y].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x++;

                        }else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
                        }
                        break;
                    }
                }

            }
        }
        if (eq()){
            Game2048Activity.getGame2048Activity().addStep(++ step);
            addRandomNum();
            checkComplete();
        }
    }

    //向上动作
    private void swipeUp(){
        eval();//先把赋值给临时数组用来判断卡片有动过
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardMap[x][y1].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y--;

                        }else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (!eq()){
            return;
        }
        if (eq()){
            Game2048Activity.getGame2048Activity().addStep(++ step);
            addRandomNum();
            checkComplete();
        }
    }

    //向下动作
    private void swipeDown(){
        eval();//先把赋值给临时数组用来判断卡片有动过
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (int y1 = y-1; y1 >= 0; y1--) {
                    if (cardMap[x][y1].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;

                        }else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (eq()){
            Game2048Activity.getGame2048Activity().addStep(++ step);
            addRandomNum();
            checkComplete();
        }
    }

    /**
     * 开始游戏
     */
    public void startGame(){
        Game2048Activity.getGame2048Activity().clearScore();//清空积分
        Game2048Activity.getGame2048Activity().clearStep();//清空步骤
        step = 0;
        //创建卡片
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardMap[x][y].setNum(0);
            }
        }
        //添加两个随机数
        addRandomNum();
        addRandomNum();
    }

    /**
     * 游戏完成
     */
    private void checkComplete(){
        boolean complete = true;
        ALL://跳出所有
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum()==0||
                        (x>0&&cardMap[x][y].equals(cardMap[x-1][y]))||
                        (x<3&&cardMap[x][y].equals(cardMap[x+1][y]))||
                        (y>0&&cardMap[x][y].equals(cardMap[x][y-1]))||
                        (y<3&&cardMap[x][y].equals(cardMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete) {

            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                    step =  0;
                }
            }).show();
        }

    }

    //添加随机数
    private void addRandomNum(){
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point point = emptyPoints.remove((int)(Math.random() * emptyPoints.size()));
        //通过难度值 生成相应的值
        double _random = Math.random();
        switch (Game2048Activity.getGame2048Activity().difficultyNum) {

            case Config.DIFFICULTY_VALUE_COMMLNLY:
                if (_random > 0.9) {
                    cardMap[point.x][point.y].setNum(8);
                }else if (_random > 0.6) {
                    cardMap[point.x][point.y].setNum(4);
                }else {
                    cardMap[point.x][point.y].setNum(2);
                }
                break;
            case Config.DIFFICULTY_VALUE_HARD:
                if (_random > 0.9) {
                    cardMap[point.x][point.y].setNum(16);
                }else if (_random > 0.7) {
                    cardMap[point.x][point.y].setNum(8);
                }else if (_random > 0.4) {
                    cardMap[point.x][point.y].setNum(4);
                }else {
                    cardMap[point.x][point.y].setNum(2);
                }
                break;

            default:
                cardMap[point.x][point.y].setNum( _random > 0.2 ? 2 : 4);
                break;
        }


    }



    private Card[][] cardMap = new Card[4][4];
    private Card[][] judge = new Card[4][4];

    private List<Point> emptyPoints = new ArrayList<Point>();
    //定义步骤
    private int step = 0 ;
    private static GameView gameView = null;
    public static GameView getGameView(){
        return gameView;
    }



    /**
     * 表示有动过返回 true 生成卡片，否则不生成卡片
     * @return
     */
    private boolean eq(){
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (judge[x][y].getNum() != cardMap[x][y].getNum()){
                    return true;
                }
            }
        }
        return  false;
    }

    /**
     * 赋值,先把值存起来，用来判断是否有移动
     */
    private void eval(){
        clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                judge[x][y].setNum(cardMap[x][y].getNum());
            }
        }
    }

    /**
     * 清空临时的数组
     */
    private void clear(){
        //创建卡片
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                judge[x][y].setNum(0);
            }
        }
    }

    /**
     * 创建临时卡片
     */
    private void createJudge(){
        //创建卡片
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                judge[x][y] = new Card(getContext());
                judge[x][y].setNum(0);
            }
        }
    }

}
