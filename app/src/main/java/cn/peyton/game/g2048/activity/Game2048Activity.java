package cn.peyton.game.g2048.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.peyton.game.R;
import cn.peyton.game.g2048.common.Config;
import cn.peyton.game.g2048.common.OperationSP;
import cn.peyton.game.g2048.view.GameView;

/**
 * <span style="color:red;font: 16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;">
 * </span>
 * <pre>
 * 作者: <a href="http://www.peyton.cn">peyton</a>
 * 邮箱: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * 创建时间: 2017-11-12 11:28
 * </pre>
 */

public class Game2048Activity extends Activity implements View.OnClickListener {
    /**
     * 构造
     */
    public Game2048Activity() {
        game2048Activity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_game_2048);
        Intent intent = getIntent();
        difficultyNum = Integer.parseInt(intent.getStringExtra(Config.ACTION_DIFFICULTY_NAME));//获取难度值

        tvScore = (TextView) findViewById(R.id.tvScroe);//分数控件
        tvStep = (TextView) findViewById(R.id.tvStep);//步骤控件
        tvStep.setText("0");//
        tvSupremeScore = (TextView) findViewById(R.id.tvSupremeScore);//最高分控件
        llScroeTitle = (LinearLayout) findViewById(R.id.llScroeTitle);//分数计算框架
        llTopAdvertisement = (LinearLayout) findViewById(R.id.llTopAdvertisement); //顶部广告框架
        ivRefurbish = (ImageView) findViewById(R.id.ivRefurbish);//刷新按钮
        ivGoBack = (ImageView) findViewById(R.id .ivGoBack); //返回按钮
        //获取屏幕的宽与高
        screenWidth = Config.getScreenWidth(this);
        screenHeight = Config.getScreenHeight(this);
        //从SharedPreferences中获取游戏最高分,如为空则表示没玩过用0表示,否则就给赋值
        Object[] _ss = OperationSP.getSp(this, Config.SP_SAVE_NAME, Config.SP_SUPREME_SCORE + difficultyNum);
        if ("".equals(_ss[0])) {
            addSupremeScore(0);
        }else {
            addSupremeScore(Integer.parseInt(_ss[0].toString()));//添加最高分
        }


        int _scoreTileHeight = 130; //设置得分的框的高度
        _topAdvertisementHeight = screenHeight - screenWidth - _scoreTileHeight - 50; //设置顶部广告框的高度
        //设置顶部广告框的宽与高
        llTopAdvertisement.setLayoutParams(
                new LinearLayout.LayoutParams(llTopAdvertisement.getLayoutParams().width , _topAdvertisementHeight));
        //设置得分框的宽与高
        llScroeTitle.setLayoutParams(
                new LinearLayout.LayoutParams(llScroeTitle.getLayoutParams().width , _scoreTileHeight));
        settingAd(_topAdvertisementHeight);
        initOnClickListener();

    }
    //按钮监听事件
    private void initOnClickListener() {
        ivGoBack.setOnClickListener(this);
        ivRefurbish.setOnClickListener(this);
    }

    private void settingAd(int width){
        System.out.println("===========================" + Config.getScreenHeight(this));
        System.out.println("===========================" + llTopAdvertisement.getLayoutParams().height);
        int tWidth = screenWidth / 3;
        llTopAdvertisement.removeAllViews();
        ImageView imageView1 = new ImageView(this);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(tWidth,_topAdvertisementHeight);
        lp1.setMargins(10,10,10,10);
        imageView1.setLayoutParams(lp1);
        imageView1.setImageResource(R.drawable.ad_20);

        llTopAdvertisement.addView(imageView1);
        ImageView imageView2 = new ImageView(this);
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(tWidth,_topAdvertisementHeight));
        imageView2.setImageResource(R.drawable.ad_21);
        llTopAdvertisement.addView(imageView2);

        ImageView imageView3 = new ImageView(this);
        imageView3.setLayoutParams(new LinearLayout.LayoutParams(tWidth,_topAdvertisementHeight));

        imageView3.setImageResource(R.drawable.ad_22);
        llTopAdvertisement.addView(imageView3);
        llTopAdvertisement.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击返回事件
            case R.id.ivGoBack:
                startActivity(new Intent(Game2048Activity.this, MainActivity.class));
                finish();
                break;
            //刷新事件
            case R.id.ivRefurbish:
                GameView.getGameView().startGame();
                break;
        }
    }

    /**
     * 清空分数
     */
    public void clearScore(){
        score = 0;
        showScore();
    }

    /**
     * 显示分数
     */
    public void showScore(){
        tvScore.setText(score + "");
    }

    /**
     * 添加分数
     * @param s 分数
     */
    public void addScore(int s){
        if (s > score) {
            score = s;
            showScore();
            if (s > supremeScore) {
                addSupremeScore(s);
                //把最高分保存到SharedPreferences
                OperationSP.saveSp(this, Config.SP_SAVE_NAME, Config.SP_SUPREME_SCORE + difficultyNum ,s+"");
            }
        }
    }
    /**
     * 清空步骤
     */
    public void clearStep(){
        step = 0;
        showStep();
    }
    /**
     * 显示步骤
     */
    public void showStep(){
        tvStep.setText(step + "");
    }
    /**
     * 添加 步骤
     * @param s 步骤数
     */
    public void addStep(int s) {
        step = s;
        showStep();
    }
    /**
     * 清空最高分
     */
    public void clearSupremeScore() {
        supremeScore = 0;
        showSupremeScore();
    }
    /**
     * 显示最高分
     */
    public void showSupremeScore() {
        tvSupremeScore.setText(supremeScore + "");
    }
    /**
     * 添加最高分
     * @param s 最高分
     */
    public void addSupremeScore(int s){
        supremeScore = s;
        showSupremeScore();
    }


    private int score = 0;//分数
    private int supremeScore = 0; //最高分
    public static int step = 1;//最少的步骤
    private int screenWidth,screenHeight; //当前屏幕的宽,高
    private TextView tvScore,tvStep,tvSupremeScore; //分数控件
    public int difficultyNum = 0;//难度值
    private LinearLayout llTopAdvertisement,llScroeTitle; //顶部广告布局,分数标题布局
    private ImageView ivRefurbish,ivGoBack; //刷新,返回
    private int _topAdvertisementHeight;

    /**
     * 申明对象
     */
    private static Game2048Activity game2048Activity = null;
    /**
     * 外部可访问的对象
     * @return
     */
    public static Game2048Activity getGame2048Activity() {
        return game2048Activity;
    }
}
