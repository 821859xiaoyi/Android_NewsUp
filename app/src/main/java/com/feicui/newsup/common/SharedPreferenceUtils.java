package com.feicui.newsup.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.feicui.newsup.model.entity.BaseEntity;
import com.feicui.newsup.model.entity.Register;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SharedPreferenceUtils {
    /**
     * 保存注册信息
     * @param context
     * @param entity 例如：{"message":"OK","status":0,"data":{"result":0,"token":"c22cf91b0f40be4850cf2ac4c25664d2","explain":"登录成功"}}
     */
    public static void saveRegister(Context context, BaseEntity<Register> entity){
        Register data=entity.getData();//"data":{"result":0,"token":"c22cf91b0f40be4850cf2ac4c25664d2","explain":"登录成功"}
        SharedPreferences sp=context.getSharedPreferences("register",Context.MODE_APPEND);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("message",entity.getMessage());//"message":"OK"
        editor.putInt("status",Integer.parseInt(entity.getStatus()));//"status":0
        editor.putString("result",data.getResult());//"result":0
        editor.putString("token",data.getToken());//"token":"c22cf91b0f40be4850cf2ac4c25664d2"
        editor.putString("explain",data.getExplain());//"explain":"登录成功"
        editor.commit();
    }
}
