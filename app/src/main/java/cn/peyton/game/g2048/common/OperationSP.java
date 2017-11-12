package cn.peyton.game.g2048.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * <span style="color:red;font: 16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;">
 *     操作SharedPreferences数据类
 * </span>
 * <pre>
 * 作者: <a href="http://www.peyton.cn">peyton</a>
 * 邮箱: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * 创建时间: 2017-11-12 11:22
 * </pre>
 */

public class OperationSP {
    /**
     * 保存数据到SharedPreferences
     * @param context 上下文
     * @param spName 保存名称
     * @param kvs Key与Value对应;如K,V,K,V...格式
     */
    public static void saveSp(Context context , String spName, String ... kvs){
        saveSp(context, Activity.MODE_PRIVATE , spName, kvs);
    }
    /**
     * 保存数据到SharedPreferences
     * @param context 上下文
     * @param mode 保存的模式
     * @param spName 保存名称
     * @param kvs Key与Value对应;如K,V,K,V...格式
     */
    public static void saveSp(Context context , int mode , String spName,String ... kvs){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, mode);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int l = kvs.length;
        if (l>0 && l%2 == 0) {
            for (int i = 0; i < kvs.length; i = i+2) {
                editor.putString(kvs[i], kvs[i+1]);
            }
            editor.commit();
        }
    }
    /**
     * 从SharedPreferences读取数据
     * @param context  上下文
     * @param spName  保存名称
     * @param ks KEY的集合;如K,K,K...格式
     * @return VALUE的数组集合
     */
    public static Object[] getSp(Context context , String spName ,String ... ks){
        return getSp(context, Activity.MODE_PRIVATE, spName, ks);
    }
    /**
     * 从SharedPreferences读取数据
     * @param context 上下文
     * @param mode 保存的模式
     * @param spName  保存名称
     * @param ks KEY的集合;如K,K,K...格式
     * @return VALUE的数组集合
     */
    public static Object[] getSp(Context context , int mode , String spName ,String ... ks){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName,  mode);
        if (ks.length > 0) {
            Object[] object = new Object[ks.length];
            for (int i = 0; i < ks.length; i++) {
                object[i] = sharedPreferences.getString(ks[i], "");
            }
            return object;
        }
        return null;
    }
}
