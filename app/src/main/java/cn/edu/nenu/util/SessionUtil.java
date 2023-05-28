package cn.edu.nenu.util;

import android.util.Log;

import cn.edu.nenu.MyApplication;
import cn.edu.nenu.entity.User;

public class SessionUtil {

    public void SessionSetUser(User user) {
        Log.d("cn.edu.nenu", "SessionUtil类：SessionSetUser(User user)被执行。");
        Log.d("cn.edu.nenu", user.toString());

        MyApplication myApp = MyApplication.getInstance();

        myApp.infoMap.put("cur_id", String.valueOf(user.getId()));
        myApp.infoMap.put("cur_account", user.getAccount());
        myApp.infoMap.put("cur_password", user.getPassword());
        myApp.infoMap.put("cur_name", user.getName());
        myApp.infoMap.put("cur_regTime", user.getRegTime());
        myApp.infoMap.put("cur_signature", user.getSignature());
    }

    public User SessionGetUser(MyApplication myApp) {
        User user = new User();
        user.setId(Integer.parseInt(myApp.infoMap.get("cur_id")));
        user.setAccount(myApp.infoMap.get("cur_account"));
        user.setPassword(myApp.infoMap.get("cur_password"));
        user.setName(myApp.infoMap.get("cur_name"));
        user.setRegTime(myApp.infoMap.get("cur_regTime"));
        user.setSignature(myApp.infoMap.get("cur_signature"));

        return user;
    }
}
