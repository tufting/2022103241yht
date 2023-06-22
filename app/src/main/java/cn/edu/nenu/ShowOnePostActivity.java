package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nenu.adapter.CommentBaseAdapter;
import cn.edu.nenu.adapter.HomeBaseAdapter;
import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.CommentDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Comment;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.TimeUtil;
import cn.edu.nenu.util.ToastUtil;

public class ShowOnePostActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MyApplication myApp;
    private PostDao postDao;
    private UserDao userDao;
    private CollectsDao collectsDao;
    private CommentDao commentDao;
    private Post post;

    private EditText et_comment_cnt;
    private ListView lv_comment;

    private int findExist;
    private int curId;
    private int postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_post);
        Log.d("execute log", "执行了ShowOnePostActivity类的onCreate()方法...");

        /* session get */
        myApp = MyApplication.getInstance();
        userDao = myApp.getCampusInfoDB().userDao();
        collectsDao = myApp.getCampusInfoDB().collectsDao();
        postDao = myApp.getCampusInfoDB().postDao();
        commentDao = myApp.getCampusInfoDB().commentDao();

        post = new Post();
        post.setId(Integer.parseInt(myApp.infoMap.get("pId")));
        post.setTitle(myApp.infoMap.get("pTitle"));
        post.setContent(myApp.infoMap.get("pContent"));
        String block = myApp.infoMap.get("pBlock");
        post.setBlock(block);
        post.setTime(myApp.infoMap.get("pTime"));
        post.setAuthor(myApp.infoMap.get("pAuthor"));
        post.setCollects_num(Integer.parseInt(myApp.infoMap.get("pCollectsNum")));
        Log.d("execute log", post.toString());

        /* 获取id */
        TextView tv_author = findViewById(R.id.tv_author);
        TextView tv_content = findViewById(R.id.tv_content);
        TextView tv_block = findViewById(R.id.tv_block);
        TextView tv_time = findViewById(R.id.tv_time);
        TextView tv_collects_num = findViewById(R.id.tv_collects_num);

        /* 获取用户的name字段 */
        int authorId = Integer.parseInt(post.getAuthor());
        String name = userDao.queryById(authorId);

        /* 界面上显示post信息 */
        tv_author.setText(name);
        tv_content.setText(post.getTitle() + "。" + post.getContent());
        tv_block.setText("#" + block + "#");
        tv_time.setText(post.getTime());

        /* 获取当前用户Id、当前帖子Id，用于判断是否已经收藏过该帖子 */
        curId = Integer.parseInt(myApp.infoMap.get("cur_id"));
        postId = post.getId();

        findExist = collectsDao.queryByDoubleId(curId, postId);
        if (findExist == 0) {
            tv_collects_num.setText(post.getCollects_num() + "人收藏(点击收藏)");
        } else {
            tv_collects_num.setText(post.getCollects_num() + "人收藏(点击取消收藏)");
        }

        /* 输入评论 */
        et_comment_cnt = findViewById(R.id.et_comment_cnt);

        /* 获取User，并映射成{id: name} */
        Map<Integer, String> userMap = new HashMap<Integer, String>();
        userDao = myApp.getCampusInfoDB().userDao();
        List<User> userList = userDao.queryAll();
        for (User user: userList) {
            userMap.put(user.getId(), user.getName());
        }

        /* 评论列表 */
        List<Comment> commentList = commentDao.queryByPostId(postId);
        CommentBaseAdapter adapter = new CommentBaseAdapter(this, commentList, userMap);

        lv_comment = findViewById(R.id.lv_comment);
        lv_comment.setAdapter(adapter);
        lv_comment.setOnItemClickListener(this);

        /* 按钮监听 */
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.ll_collects).setOnClickListener(this); // 点击收藏的监听
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if (view.getId() == R.id.iv_back) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_send) {
            String my_comment = et_comment_cnt.getText().toString();
            if (my_comment.length() > 0) {
                /* 将其添加到数据库，刷新当前页面 */
                Comment comment = new Comment();
                comment.setContent(my_comment);
                comment.setUserId(curId);
                comment.setPostId(postId);
                comment.setTime(TimeUtil.getCurrentSystemTime());
                commentDao.insert(comment);
                ToastUtil.show(this, "评论成功");

                intent = new Intent(this, ShowOnePostActivity.class);
                startActivity(intent);
            } else {
                ToastUtil.show(this, "请输入评论内容");
            }
        } else if (view.getId() == R.id.ll_collects) {
            Collects collects = new Collects();
            collects.setUserId(curId);
            collects.setPostId(postId);

            /* 先看是否已经收藏了 */
            if (findExist == 0) {
                collectsDao.insert(collects);
                postDao.collectModifyOne(postId, 1);

                ToastUtil.show(this, "收藏成功");
                findExist = 1;
            }
//            else {
//                collectsDao.delete(collects);
//                postDao.collectModifyOne(postId, -1);
//
//                ToastUtil.show(this, "已取消收藏");
//                findExist = 0;
//            }

//            SessionUtil sUtil = new SessionUtil();
//            sUtil.SessionSetPost(post);

            /* 更新当前页面 */
//            intent = new Intent(this, ShowOnePostActivity.class);
//            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /* 点击评论信息触发该方法 */
    }
}