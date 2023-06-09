package cn.edu.nenu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.edu.nenu.MyApplication;
import cn.edu.nenu.R;
import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.ToastUtil;

public class UUserActivity extends AppCompatActivity implements View.OnClickListener {
    private MyApplication myApp;
    private User user;
    private UserDao userDao;
    private PostDao postDao;
    private CollectsDao collectsDao;

    private EditText et_account;
    private EditText et_password;
    private EditText et_name;
    private TextView tv_regTime;
    private EditText et_signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuser);
        Log.d("execute log", "执行了UUserActivity类的onCreate()方法...");

        myApp = MyApplication.getInstance();
        user = new User();
        user.setId(Integer.parseInt(myApp.infoMap.get("cur_id")));
        user.setAccount(myApp.infoMap.get("cur_account"));
        user.setPassword(myApp.infoMap.get("cur_password"));
        user.setName(myApp.infoMap.get("cur_name"));
        user.setRegTime(myApp.infoMap.get("cur_regTime"));
        user.setSignature(myApp.infoMap.get("cur_signature"));

        Log.d("execute log", "当前用户信息：" + user.toString());

        /* 获取et的id */
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        et_name = findViewById(R.id.et_name);
        tv_regTime = findViewById(R.id.tv_regTime);
        et_signature = findViewById(R.id.et_signature);

        /* 设置et文本 */
        et_account.setText(user.getAccount());
        et_password.setText(user.getPassword());
        et_name.setText(user.getName());
        tv_regTime.setText(user.getRegTime());
        et_signature.setText(user.getSignature());

        userDao = myApp.getCampusInfoDB().userDao();
        
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        if(view.getId() == R.id.btn_update) {
            /* 将修改后的信息提交到数据库 */
            user.setAccount(et_account.getText().toString());
            user.setPassword(et_password.getText().toString());
            user.setName(et_name.getText().toString());
            user.setSignature(et_signature.getText().toString());

            int flag = userDao.update(user);

            /* 弹出相关提示 */
            if (flag != 0) {
                ToastUtil.show(this, "修改成功");

                SessionUtil sUtil = new SessionUtil(); // 保存当前用户信息
                sUtil.SessionSetUser(user);
            } else {
                ToastUtil.show(this, "修改失败");
            }
            
            intent = new Intent(this, UUserActivity.class);
        } else if (view.getId() == R.id.btn_delete) {
            /* 删除用户数据 */
            userDao.delete(user.getId());

            /* 删除用户发表过的帖子数据 */
            postDao = myApp.getCampusInfoDB().postDao();
            List<Post> postList = postDao.queryByAuthor(String.valueOf(user.getId()));
            postDao.deleteByAuthor(String.valueOf(user.getId()));

            /* 删除用户收藏过的帖子数据 */
            collectsDao = myApp.getCampusInfoDB().collectsDao();
            collectsDao.deleteByUserId(user.getId());
            for (Post post: postList) {
                collectsDao.deleteByPostId(post.getId());
            }

            intent = new Intent(this, ManagerActivity.class);
        } else if (view.getId() == R.id.iv_back) {
            intent = new Intent(this, ManagerActivity.class);
        }
        startActivity(intent);
    }
}