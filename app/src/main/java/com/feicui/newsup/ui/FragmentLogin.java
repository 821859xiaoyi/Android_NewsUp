package com.feicui.newsup.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feicui.newsup.R;
import com.feicui.newsup.common.CommonUtil;
import com.feicui.newsup.model.biz.parser.ParserUser;
import com.feicui.newsup.model.biz.parser.UserManager;
import com.feicui.newsup.model.entity.BaseEntity;
import com.feicui.newsup.model.entity.Register;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FragmentLogin extends Fragment {

    private View view;
    private EditText et_name;
    private EditText et_pwd;
    private Button btn_register,btn_forget,btn_login;
    //用户网络连接管理类(登录，注册)
    private UserManager userManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.login_fragment,container,false);
        et_name= (EditText) view.findViewById(R.id.editText_nickname);
        et_pwd= (EditText) view.findViewById(R.id.editText_pwd);
        btn_register= (Button) view.findViewById(R.id.button_register);
        btn_forget= (Button) view.findViewById(R.id.button_forgetPass);
        btn_login= (Button) view.findViewById(R.id.button_login);
        btn_register.setOnClickListener(clickListnener);
        btn_forget.setOnClickListener(clickListnener);
        btn_login.setOnClickListener(clickListnener);
        return view;

    }
    private View.OnClickListener clickListnener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_register://注册
                    ((MainActivity)getActivity()).showFragmentRegister();
                    break;
                case R.id.button_forgetPass://忘记密码
                    ((MainActivity)getActivity()).showFramentForget();
                    break;
                case R.id.button_login://登录
                    String name=et_name.getText().toString().trim();
                    String pwd=et_pwd.getText().toString().trim();
                    if (name==null||name.equals("")){
                        Toast.makeText(getActivity(), "用户名为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (pwd.length()<6||pwd.length()>16){
                        Toast.makeText(getActivity(), "密码长度为6~16之间", Toast.LENGTH_SHORT).show();
                    }
                    if (userManager==null){
                        userManager=UserManager.getInstance(getActivity());
                    }
                    userManager.login(getActivity(),listener,errorListener, CommonUtil.VERSION_CODE+"",name,pwd,0+"");

                    break;
            }
        }
    };
    private Response.Listener listener=new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            BaseEntity<Register> entity= ParserUser.parserRegister(response);//将获得的响应数据解析成对应的java对象存储起来
            int status=Integer.parseInt(entity.getStatus());//获得状态码，把状态码字符串变成int类型
            String result="";
            if (status==0){
                result="登录成功";
                startActivity(new Intent(getActivity(),UserActivity.class));//登录成功跳转到用户界面
            }else if (status==-1){
                result="用户名或密码错误";
            }else{
                result="登录失败";
            }
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    };
    private Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();

        }
    };
}
