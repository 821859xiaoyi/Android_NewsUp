package com.feicui.newsup.ui;


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
import com.feicui.newsup.model.entity.Toemail;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FragmentForget extends Fragment implements View.OnClickListener{
    private View view;
    private EditText et_email;
    private Button btn_commit;
    private UserManager userManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_forget,container,false);
        et_email= (EditText) view.findViewById(R.id.edit_email);
        btn_commit= (Button) view.findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_commit){
            String email=et_email.getText().toString().trim();
            if (!CommonUtil.verifyEmail(email)){
                Toast.makeText(getActivity(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userManager==null){
                userManager=UserManager.getInstance(getActivity());
            }
            userManager.forget(getActivity(),listener,errorListener,CommonUtil.VERSION_CODE+"",email);

        }
    }
    private Response.Listener<String> listener=new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            BaseEntity<Toemail> entity= ParserUser.parserGetPwd(response);
            int status=Integer.parseInt(entity.getStatus());
            Toemail data=entity.getData();
            if (status==0){
                if (data.getResult()==0){
                    ((MainActivity)getActivity()).showFramentMain();
                }else if (data.getResult()==-2){
                    et_email.requestFocus();//让编辑框聚焦
                }else if (data.getResult()==-1){
                    et_email.requestFocus();//让编辑框聚焦
                }
                Toast.makeText(getActivity(), data.getExplain(), Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(), "忘记密码网络连接异常", Toast.LENGTH_SHORT).show();
        }
    };
}
