package cn.edu.nenu.admin;

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

import cn.edu.nenu.MyApplication;
import cn.edu.nenu.R;
import cn.edu.nenu.adapter.MPostBaseAdapter;
import cn.edu.nenu.adapter.MUserBaseAdapter;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.ToastUtil;

public class MPostActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MyApplication myApp;
    private PostDao postDao;
    private UserDao userDao;
    private List<Post> postList;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpost);
        Log.d("execute log", "执行了MUserActivity类的onCreate()方法...");

        /* 待优化：可以增加一个点击作者名、发帖日期的排序功能。 */
        myApp = MyApplication.getInstance();
        postDao = myApp.getCampusInfoDB().postDao();
        postList = postDao.queryAll();

        /* 获取User，并映射成{id: account} */
        Map<Integer, String> userMap = new HashMap<Integer, String>();
        userDao = myApp.getCampusInfoDB().userDao();
        userList = userDao.queryAll();
        for (User user: userList) {
            userMap.put(user.getId(), user.getAccount());
        }

        MPostBaseAdapter adapter = new MPostBaseAdapter(this, postList, userMap);

        ListView lv_post = findViewById(R.id.lv_post);
        lv_post.setAdapter(adapter);
        lv_post.setOnItemClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            Intent intent = new Intent(this, ManagerActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(this, "点击了" + postList.get(i).getTitle());

        /* 保存相应Post信息，并跳转到相应界面，显示信息 */
        SessionUtil sUtil = new SessionUtil();
        sUtil.SessionSetPost(postList.get(i));

        Intent intent = new Intent(this, UPostActivity.class);
        startActivity(intent);
    }
}