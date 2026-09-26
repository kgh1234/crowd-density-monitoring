package com.example.crowd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    private Button btn_join, btn_vali, btn_b;
    private EditText et_email, et_name, et_pnum, et_password, et_rpw;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_pnum = findViewById(R.id.et_pnum);
        et_password = findViewById(R.id.et_password);
        et_rpw = findViewById(R.id.et_rpw);

        btn_b = findViewById(R.id.btn_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_vali = findViewById(R.id.btn_vali);
        btn_vali.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String UserEmail = et_email.getText().toString();
                if(validate){
                    return;
                }

                if(UserEmail.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                       try {
                           JSONObject jsonResponse = new JSONObject(response);
                           boolean success = jsonResponse.getBoolean("success");

                           if (success) {
                               AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                               dialog = builder.setMessage("사용 가능한 이메일입니다.").setPositiveButton("확인", null).create();
                               dialog.show();
                               et_email.setEnabled(false);
                               validate = true;
                               btn_vali.setBackgroundResource(R.drawable.vali);

                           }
                           else{
                               AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                               dialog = builder.setMessage("이미 사용 중인 이메일입니다.").setNegativeButton("확인", null).create();
                               dialog.show();
                           }
                       } catch(JSONException e){
                           e.printStackTrace();
                       }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(UserEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(validateRequest);
            }
        });


        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserEmail = et_email.getText().toString();
                final String UserName = et_name.getText().toString();
                final String UserPhoneNum = et_pnum.getText().toString();
                final String UserPwd = et_password.getText().toString();
                final String UserPwc = et_rpw.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("중복된 이메일이 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(UserEmail.equals("") || UserName.equals("") || UserPhoneNum.equals("") || UserPwd.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean("success");

                            if(UserPwd.equals(UserPwc)){
                                if(success){

                                    Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                JoinRequest joinrequest = new JoinRequest(UserEmail, UserName, UserPhoneNum, UserPwd, UserPwc, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(joinrequest);

                }


        });


    }




}