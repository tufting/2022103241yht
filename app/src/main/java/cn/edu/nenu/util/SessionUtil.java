package cn.edu.nenu.util;

import android.util.Log;

import cn.edu.nenu.MyApplication;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;

public class SessionUtil {

    public void SessionSetUser(User user) {
        Log.d("execute log", "SessionUtil类：SessionSetUser(User user)被执行。");
        Log.d("execute log", "当前用户信息：" + user.toString());

        MyApplication myApp = MyApplication.getInstance();

        myApp.infoMap.put("cur_id", String.valueOf(user.getId()));
        myApp.infoMap.put("cur_account", user.getAccount());
        myApp.infoMap.put("cur_password", user.getPassword());
        myApp.infoMap.put("cur_name", user.getName());
        myApp.infoMap.put("cur_regTime", user.getRegTime());
        myApp.infoMap.put("cur_signature", user.getSignature());
    }

    public void SessionSetPost(Post post) {
        Log.d("execute log", "SessionUtil类：SessionSetPost(Post post)被执行。");
        Log.d("execute log", "当前帖子信息：" + post.toString());

        MyApplication myApp = MyApplication.getInstance();

        myApp.infoMap.put("pId", String.valueOf(post.getId()));
        myApp.infoMap.put("pTitle", post.getTitle());
        myApp.infoMap.put("pContent", post.getContent());
        myApp.infoMap.put("pBlock", post.getBlock());
        myApp.infoMap.put("pTime", post.getTime());
        myApp.infoMap.put("pAuthor", post.getAuthor());
        myApp.infoMap.put("pCollectsNum", String.valueOf(post.getCollects_num()));
    }
}
