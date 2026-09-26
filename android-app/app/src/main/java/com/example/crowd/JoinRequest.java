package com.example.crowd;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class JoinRequest extends StringRequest{

    private Map<String, String> map;

    public JoinRequest(String UserEmail, String UserName, String UserPhoneNum, String UserPwd, String UserPwc, Response.Listener<String> listener){
        super(Method.POST, ApiConfig.JOIN_URL, listener, null);

        map = new HashMap<>();
        map.put("UserEmail", UserEmail);
        map.put("UserName", UserName);
        map.put("UserPhoneNum", UserPhoneNum);
        map.put("UserPwd", UserPwd);
        map.put("UserPwc", UserPwc);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
