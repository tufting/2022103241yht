package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nenu.adapter.CollectBaseAdapter;
import cn.edu.nenu.adapter.HomeBaseAdapter;
import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.ToastUtil;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MyApplication myApp;
    private PostDao postDao;
    private UserDao userDao;
    private CollectsDao collectsDao;

    private List<Post> postList;
    private ListView lv_post;

    private int curId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        Log.d("execute log", "执行了CollectActivity类的onCreate()方法...");

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
        postList = postDao.queryByCollectUserId(curId);
        Log.d("execute log", "我收藏的帖子=" + postList);

        Log.d("execute log", "当前用户收藏过 = " + collection);

        CollectBaseAdapter adapter = new CollectBaseAdapter(this, postList, userMap, collection);

        lv_post = findViewById(R.id.lv_post);
        lv_post.setAdapter(adapter);
        lv_post.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(this, "触发了点击事件" + postList.get(i).getTitle());
    }
}