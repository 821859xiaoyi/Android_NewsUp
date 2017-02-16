package com.feicui.newsup.model.biz.parser;

import com.feicui.newsup.model.entity.BaseEntity;
import com.feicui.newsup.model.entity.Register;
import com.feicui.newsup.model.entity.Toemail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2017/1/10.
 */

public class ParserUser {
    //解析登录成功或注册成功返回的json数据
    public static BaseEntity<Register> parserRegister(String json){
        return new Gson().fromJson(json,new TypeToken<BaseEntity<Register>>(){}.getType());//返回的是一个BaseEntity类型
    }
    //解析点击忘记密码按钮后返回的json数据
    public static BaseEntity<Toemail> parserGetPwd(String json){
        //返回BaseEntity对象
        return new Gson().fromJson(json,new TypeToken<BaseEntity<Toemail>>(){}.getType());
    }
}
