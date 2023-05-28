package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nenu.adapter.HomeBaseAdapter;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.ToastUtil;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MyApplication myApp;
    private PostDao postDao;
    private UserDao userDao;

    private List<Post> postList;
    private ListView lv_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("execute log", "HomeActivity类onCreate()被执行。");

        findViewById(R.id.iv_home).setOnClickListener(this);
        findViewById(R.id.iv_collect).setOnClickListener(this);
        findViewById(R.id.iv_my).setOnClickListener(this);

        myApp = MyApplication.getInstance();

        /* 获取User，并映射成{id: name} */
        Map<Integer, String> userMap = new HashMap<Integer, String>();
        userDao = myApp.getCampusInfoDB().userDao();
        List<User> userList = userDao.queryAll();
        for (User user: userList) {
            userMap.put(user.getId(), user.getName());
        }

        /* 获取Post信息 */
        postDao = myApp.getCampusInfoDB().postDao();
        postList = postDao.queryAll();

        HomeBaseAdapter adapter = new HomeBaseAdapter(this, postList, userMap);

        lv_post = findViewById(R.id.lv_post);
        lv_post.setAdapter(adapter);
        lv_post.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_home:
                break;
            case R.id.iv_collect:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_my:
                intent = new Intent(this, MyActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(this, "您选择的是：" + postList.get(i));
    }
}