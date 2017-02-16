package com.feicui.newsup.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.feicui.newsup.R;
import com.feicui.newsup.common.CommonUtil;
import com.feicui.newsup.common.SharedPreferenceUtils;
import com.feicui.newsup.model.biz.parser.ParserUser;
import com.feicui.newsup.model.biz.parser.UserManager;
import com.feicui.newsup.model.entity.BaseEntity;
import com.feicui.newsup.model.entity.Register;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FragmentRegister extends Fragment {

    private View view;
    private EditText et_email;//邮箱
    private EditText et_name;//昵称
    private EditText et_pwd;//密码
    private CheckBox checkBox;//选择
    private UserManager userManager;//发送网络请求
    private Button button;//立即注册按钮
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_register,container,false);
        button= (Button) view.findViewById(R.id.button_register);
        et_email= (EditText) view.findViewById(R.id.editText_email);
        et_name= (EditText) view.findViewById(R.id.editText_name);
        et_pwd= (EditText) view.findViewById(R.id.editText_pwd);
        checkBox= (CheckBox) view.findViewById(R.id.checkBox1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()){//若没同意条款
                    Toast.makeText(getActivity(), "请同意条款", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email=et_email.getText().toString().trim();
                String name=et_name.getText().toString().trim();
                String pwd=et_pwd.getText().toString().trim();
                if (!CommonUtil.verifyEmail(email)){//邮箱格式是否正确，不正确吐司
                    Toast.makeText(getActivity(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)){//判断用户名是否为空,为空就吐司
                    Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)){//判断密码是否为空,为空就吐司
                    Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommonUtil.verifyPassword(pwd)){//密码格式是否正确，不正确吐司
                    Toast.makeText(getActivity(), "请输入6~16位为数字和字母的密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userManager==null){
                    userManager=UserManager.getInstance(getActivity());
                }
                userManager.register(getActivity(),listener,errorListener,CommonUtil.VERSION_CODE+",name,email,pwd");
            }
        });
        return view;
    }

    private Response.Listener listener=new Response.Listener<String>(){
        /**
         *
         * @param response {"message":"OK","status":0,"data":{"result":0,"token":"7bc91914ba765fe664bb6d1477115987","explain":"注册成功"}}
         */
        @Override
        public void onResponse(String response) {//注册成功回调
            BaseEntity<Register> entity= ParserUser.parserRegister(response);
            Register data=entity.getData();
            int status=Integer.parseInt(entity.getStatus());
            if (status==0){
                String result=data.getResult();//"result":0
                String explain=data.getExplain();//"explain":"注册成功"
                if (result.equals("0")){
                    Toast.makeText(getActivity(), "注册成功，进入用户界面", Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtils.saveRegister(getActivity(),entity);//注册成功后得到的数据
                    startActivity(new Intent(getActivity(),UserActivity.class));
                    getActivity().overridePendingTransition(R.anim.screen_right_in,R.anim.screen_down_out);
                }
                Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(), "用户已存在", Toast.LENGTH_SHORT).show();
        }
    };
}
