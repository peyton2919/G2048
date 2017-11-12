package cn.peyton.game.g2048.common;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Config {
	
	public Config() {}
	/** 这个应用程序保存在SP文件名称 */
	public static final String SP_SAVE_NAME = "cn.peyton.game.APP_FileName";
	/** 保存在SP 第一次运行名称 */
	public static final String SP_FIRST_RUN_NAME = "firstRunName";
	/** 当前屏幕的宽度 */
	public static final String SP_SCREEN_WIDTH = "screenWidth";
	/** 当前屏幕的高度 */
	public static final String SP_SCREEN_HEIGHT = "screenHeight";
	/** 最高分的记录 */
	public static final String SP_SUPREME_SCORE = "supremeScore";
	/** 保存在SP中的难度名称 0表示简单,1表示一般,2表示困难*/
	public static final String SP_DIFFICULTY_NAME = "difficultyName";
	
	/** 难度选项值 简单0 */
	public static final int DIFFICULTY_VALUE_SIMPLE = 0;
	/** 难度选项值 一般 1 */
	public static final int DIFFICULTY_VALUE_COMMLNLY = 1;
	/** 难度选项值 困难 2 */
	public static final int DIFFICULTY_VALUE_HARD = 2;
	
	
	/** 跳转 难度名称  */
	public static final String ACTION_DIFFICULTY_NAME = "difficulty_name";
	
	/**
	 * 获取当前屏幕的宽度
	 * @param context  上下文
	 * @return 屏幕的宽度
	 */
	public static int getScreenWidth(Context context){
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		float density = dm.density;
		return (int)(dm.widthPixels);
	}
	/**
	 * 获取当前屏幕的高度
	 * @param context  上下文
	 * @return 屏幕的高度
	 */
	public static int getScreenHeight(Context context){
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		float density = dm.density;
		return (int)(dm.heightPixels);

	}
	/**
	 * 获取当前屏幕的密度
	 * @param context   上下文
	 * @return 屏幕的密度
	 */
	public static float getScreenDensity(Context context){
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}
	/**
	 * 用来判断应用程序是不是第一次运行
	 * @param context 上下文
	 * @return true表示第一次,否则就是false
	 */
	public static boolean isFirstRun(Context context){
		//
		SharedPreferences setting = context.getSharedPreferences(SP_SAVE_NAME, Activity.MODE_PRIVATE);
		Boolean user_first = setting.getBoolean("FIRST",true);
		if(user_first){//第一次
		      setting.edit().putBoolean("FIRST", false).commit();		      
		 }
		return user_first;
	}

}
