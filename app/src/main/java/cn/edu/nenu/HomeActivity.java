package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;
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
import cn.edu.nenu.util.SessionUtil;
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

        /* 监听分类板块 */
        findViewById(R.id.iv_b1).setOnClickListener(this);
        findViewById(R.id.iv_b2).setOnClickListener(this);
        findViewById(R.id.iv_b3).setOnClickListener(this);
        findViewById(R.id.iv_b4).setOnClickListener(this);

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

        String blockClick = myApp.infoMap.get("blockClick");
        if (blockClick != null) { /* 说明我点击了某个block */
            postList = postDao.queryByBlock(blockClick);
        } else if (content != null) {
            postList = postDao.queryByContent("%" + content + "%");
        } else {
            postList = postDao.queryAll();
        }

        et_search.setText(content);

        /* 该次搜索用完就remove掉 */
        myApp.infoMap.remove("search_content");
        myApp.infoMap.remove("blockClick");

        Log.d("execute log", "当前用户收藏过 = " + collection);

        HomeBaseAdapter adapter = new HomeBaseAdapter(this, postList, userMap, collection);

        lv_post = findViewById(R.id.lv_post);
        lv_post.setAdapter(adapter);
        lv_post.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        myApp.infoMap.remove("blockClick");
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

            /* 下面是点击板块信息的监听 */
            case R.id.iv_b1:
                myApp.infoMap.put("blockClick", "瓜田趣事");
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_b2:
                myApp.infoMap.put("blockClick", "二手闲置");
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_b3:
                myApp.infoMap.put("blockClick", "打听求助");
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_b4:
                myApp.infoMap.put("blockClick", "新闻八卦");
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SessionUtil sUtil = new SessionUtil();
        sUtil.SessionSetPost(postList.get(i));

        Intent intent = new Intent(this, ShowOnePostActivity.class);
        startActivity(intent);
    }
}