package cn.peyton.game.g2048.bean;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * <span style="color:red;font: 16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;">
 *     卡片实体类
 * </span>
 * <pre>
 * 作者: <a href="http://www.peyton.cn">peyton</a>
 * 邮箱: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * 创建时间: 2017-11-12 11:16
 * </pre>
 */
public class Card extends FrameLayout{
    /** 卡片Label*/
    private TextView label;
    /** 卡片数字*/
    private int num = 0;

    public Card(Context context) {
        super(context);
        label = new TextView(context);
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0, 0);
        addView(label,lp);
        setNum(0);
    }

    public boolean equals(Card card){
        return  getNum() == card.getNum();
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            label.setText("");
            label.setBackgroundColor(0x33ffffff);
        }else{
            if (num == 2) {
                label.setBackgroundColor(0x33ff6600);
            }else if (num == 4) {
                label.setBackgroundColor(0x33ffcc33);
            }else if (num == 8) {
                label.setBackgroundColor(0x33ffff33);
            }else if (num == 16) {
                label.setBackgroundColor(0x33ff9933);
            }else if (num == 32) {
                label.setBackgroundColor(0x33cccc00);
            }else if (num == 64) {
                label.setBackgroundColor(0x3399cc00);
            }else if (num == 128) {
                label.setBackgroundColor(0x33cc3333);
            }else if (num == 256) {
                label.setBackgroundColor(0x33ff6666);
            }else if (num == 512) {
                label.setBackgroundColor(0x33ffcc99);
            }else if (num == 1024) {
                label.setBackgroundColor(0x33ff0033);
            }else if (num == 2048) {
                label.setBackgroundColor(0x33ffcc00);
            }else if (num > 2048) {
                label.setBackgroundColor(0x33990066);
            }else {
                label.setBackgroundColor(0x33ffffff);
            }
            label.setText(num + "");
        }
    }
}
