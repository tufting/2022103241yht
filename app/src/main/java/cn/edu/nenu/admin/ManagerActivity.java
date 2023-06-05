package cn.edu.nenu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import cn.edu.nenu.MainActivity;
import cn.edu.nenu.MyApplication;
import cn.edu.nenu.R;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.ToastUtil;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private MyApplication myApp;
    private UserDao userDao;
    private PostDao postDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Log.d("execute log", "执行了ManagerActivity类的onCreate()方法...");

        myApp = MyApplication.getInstance();
        userDao = myApp.getCampusInfoDB().userDao();
        List<User> userList = userDao.queryAll();
        postDao = myApp.getCampusInfoDB().postDao();
        List<Post> postList = postDao.queryAll();

        /* 设置总人数的文本 */
        EditText et_totUser = findViewById(R.id.et_totUser);
        et_totUser.setText("1/" + String.valueOf(userList.size()));

        /* 设置总发帖数的文本 */
        EditText et_totPost = findViewById(R.id.et_totPost);
        et_totPost.setText(String.valueOf(postList.size()));

        /* 监听管理功能对应的相对布局框 */
        findViewById(R.id.rl_user).setOnClickListener(this);
        findViewById(R.id.rl_post).setOnClickListener(this);
        findViewById(R.id.rl_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_user:
                /* 管理用户信息 */
                intent = new Intent(this, MUserActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_post:
                /* 管理帖子信息 */
                intent = new Intent(this, MPostActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_logout:
                myApp.infoMap.clear();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                ToastUtil.show(this, "已退出");
                break;
        }
    }
}