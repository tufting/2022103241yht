package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nenu.adapter.HomeBaseAdapter;
import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.ToastUtil;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MyApplication myApp;
    private PostDao postDao;
    private UserDao userDao;
    private CollectsDao collectsDao;

    private List<Post> postList;
    private ListView lv_post;

    private EditText et_search;

    private int curId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("execute log", "执行了HomeActivity类的onCreate()方法...");

        findViewById(R.id.iv_home).setOnClickListener(this);
        findViewById(R.id.iv_collect).setOnClickListener(this);
        findViewById(R.id.iv_my).setOnClickListener(this);

        /* 监听搜索框，设置模糊查询 */
        et_search = findViewById(R.id.et_search);
        findViewById(R.id.iv_search).setOnClickListener(this);

        myApp = MyApplication.getInstance();

        /* 获取User，并映射成{id: name} */
        Map<Integer, String> userMap = new HashMap<Integer, String>();
        userDao = myApp.getCampusInfoDB().userDao();
        List<User> userList = userDao.queryAll();
        for (User user: userList) {
            userMap.put(user.getId(), user.getName());
        }

        /* 获取Collect信息，添加到collection列表中 */
        Collection<Integer> collection = new ArrayList<>();
        collectsDao = myApp.getCampusInfoDB().collectsDao();
        curId = Integer.parseInt(myApp.infoMap.get("cur_id"));
        List<Integer> list = collectsDao.queryByUserId(curId);
        for (int postId: list) {
            collection.add(postId);
        }

        /* 获取Post信息 */
        postDao = myApp.getCampusInfoDB().postDao();
        String content = myApp.infoMap.get("search_content");
        Log.d("execute log", "HomeActivity页面的搜索框内容(search_content)为" + content);
        if (content != null) {
            postList = postDao.queryByContent("%" + content + "%");
        } else {
            postList = postDao.queryAll();
        }

        et_search.setText(content);
        myApp.infoMap.put("search_content", null);

        Log.d("execute log", "当前用户收藏过 = " + collection);

        HomeBaseAdapter adapter = new HomeBaseAdapter(this, postList, userMap, collection);

        lv_post = findViewById(R.id.lv_post);
        lv_post.setAdapter(adapter);
        lv_post.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_home:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_collect:
                intent = new Intent(this, CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_my:
                intent = new Intent(this, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_search:
                myApp.infoMap.put("search_content", et_search.getText().toString());
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int postId = postList.get(i).getId();

        /* 这里写收藏功能，先看是否已经收藏了 */
        int findExist = collectsDao.queryByDoubleId(curId, postId);
        if (findExist == 0) {
            Collects collects = new Collects();
            collects.setUserId(curId);
            collects.setPostId(postId);

            /* 待优化：这里需要添加事务管理 */
            collectsDao.insert(collects);
            postDao.collectAddOne(postId);

            /* 待优化：收藏成功后，应在显示页面将收藏数文本更新 */

            ToastUtil.show(this, "收藏成功");
        } else {
            ToastUtil.show(this, "您已经收藏了");
        }
    }
}