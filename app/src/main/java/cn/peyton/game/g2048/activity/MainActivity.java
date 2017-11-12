package cn.peyton.game.g2048.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.peyton.game.R;
import cn.peyton.game.g2048.common.Config;
import cn.peyton.game.g2048.common.OperationSP;

/**
 * <span style="color:red;font: 16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;">
 * </span>
 * <pre>
 * 作者: <a href="http://www.peyton.cn">peyton</a>
 * 邮箱: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * 创建时间: 2017-11-12 11:41
 * </pre>
 */

public class MainActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_main);
        ivStartGame2048 = (ImageView) findViewById(R.id.ivStartGame2048);
        rgDifficulty = (RadioGroup) findViewById(R.id.rgDifficulty);
        rbDifficulty1 = (RadioButton) findViewById(R.id.rbDifficulty1);
        rbDifficulty2 = (RadioButton) findViewById(R.id.rbDifficulty2);
        rbDifficulty3 = (RadioButton) findViewById(R.id.rbDifficulty3);
        ivStartGame2048.setOnClickListener(new StartGame2048OnClickOnlistener());
        rgDifficulty.setOnCheckedChangeListener(new DifficultyOnClickOnlistener()); //单选按钮事件
        mExplain = (TextView) findViewById(R.id.tv_game2048_explain);
        mExplain.setText(addExplain());

        Object[] _objects = OperationSP.getSp(this, Config.SP_SAVE_NAME, Config.SP_DIFFICULTY_NAME);
        if ("".equals(_objects[0])) {
            OperationSP.saveSp(this, Config.SP_SAVE_NAME, Config.SP_DIFFICULTY_NAME,Config.DIFFICULTY_VALUE_SIMPLE + "");
            checked(Config.DIFFICULTY_VALUE_SIMPLE);
        }else {
            checked(Integer.parseInt(_objects[0].toString()));
        }

    }


    private void checked(int d){
        switch (d) {
            case Config.DIFFICULTY_VALUE_COMMLNLY:
                rbDifficulty2.setChecked(true);
                break;
            case Config.DIFFICULTY_VALUE_HARD:
                rbDifficulty3.setChecked(true);
                break;

            default:
                rbDifficulty1.setChecked(true);
                break;
        }
    }

    private class StartGame2048OnClickOnlistener implements View.OnClickListener{

        @Override
        public void onClick(View arg0) {
            OperationSP.saveSp(MainActivity.this, Config.SP_SAVE_NAME, Config.SP_DIFFICULTY_NAME,difficultyNum + "");
            Intent intent = new Intent(MainActivity.this, Game2048Activity.class);
            intent.putExtra(Config.ACTION_DIFFICULTY_NAME, difficultyNum+"");
            startActivity(intent);
            finish();
        }
    }

    private class DifficultyOnClickOnlistener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup arg0, int checkId) {
            switch (checkId) {
                case R.id.rbDifficulty1:
                    difficultyNum = Config.DIFFICULTY_VALUE_SIMPLE;
                    break;
                case R.id.rbDifficulty2:
                    difficultyNum = Config.DIFFICULTY_VALUE_COMMLNLY;
                    break;
                case R.id.rbDifficulty3:
                    difficultyNum = Config.DIFFICULTY_VALUE_HARD;
                    break;

            }

        }

    }

    private String addExplain(){
        StringBuilder sb = new StringBuilder("简单说明:\n");
        sb.append("1.简单: 出现数字有2与4两种;\n");
        sb.append("2.一般: 出现数字有2、4与8三种;\n");
        sb.append("3.困难: 出现数字有2、4、8与16四种;\n");
        return sb.toString();
    }
    private int difficultyNum = 0;
    private ImageView ivStartGame2048;
    private RadioGroup rgDifficulty;
    private RadioButton rbDifficulty1,rbDifficulty2,rbDifficulty3;
    private TextView mExplain;
}
