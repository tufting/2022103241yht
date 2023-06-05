package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.TimeUtil;
import cn.edu.nenu.util.ToastUtil;

public class MyOneActivity extends AppCompatActivity implements View.OnClickListener {

    private MyApplication myApp;
    private PostDao postDao;
    private CollectsDao collectsDao;
    private Post post;

    private EditText et_title;
    private EditText et_content;
    private String block;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_one);
        Log.d("execute log", "执行了MyOneActivity类的onCreate()方法...");

        /* 获取post数据 */
        myApp = MyApplication.getInstance();
        post = new Post();
        post.setId(Integer.parseInt(myApp.infoMap.get("pId")));
        post.setTitle(myApp.infoMap.get("pTitle"));
        post.setContent(myApp.infoMap.get("pContent"));
        block = myApp.infoMap.get("pBlock");
        post.setBlock(block);
        post.setTime(myApp.infoMap.get("pTime"));
        post.setAuthor(myApp.infoMap.get("pAuthor"));
        post.setCollects_num(Integer.parseInt(myApp.infoMap.get("pCollectsNum")));
        Log.d("execute log", "当前post信息：" + post.toString());

        /* 获取文本框 */
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        RadioGroup rb_block = findViewById(R.id.rg_block);
        tv_time = findViewById(R.id.tv_time);

        /* 显示文本信息 */
        et_title.setText(post.getTitle());
        et_content.setText(post.getContent());
        tv_time.setText(post.getTime());

        RadioButton rb;
        int checked = findRadioButtonChecked(block);
        if (checked == 2) {
            rb = findViewById(R.id.rb_block2);
        } else if (checked == 3) {
            rb = findViewById(R.id.rb_block3);
        } else if (checked == 4) {
            rb = findViewById(R.id.rb_block4);
        } else {
            rb = findViewById(R.id.rb_block1);
        }
        rb.setChecked(true);

        /* 找到用户选择的block */
        rb_block.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = rb_block.findViewById(checkedId);
                block = radioButton.getText().toString();
                Log.d("execute log", "block = " + block);
            }
        });

        postDao = myApp.getCampusInfoDB().postDao();

        /* 按钮监听 */
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_confirmUpdate).setOnClickListener(this);
        findViewById(R.id.btn_deletePost).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.iv_back) {
            intent = new Intent(this, MyPostActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_confirmUpdate) {
            /* 更改后，get文本信息，并重新set文本信息 */
            post.setTitle(et_title.getText().toString());
            post.setContent(et_content.getText().toString());
            post.setBlock(block);
            post.setTime(TimeUtil.getCurrentSystemTime());

            /* 修改至数据库表 */
            int flag = postDao.update(post);
            if (flag > 0) {
                ToastUtil.show(this, "修改成功");

                SessionUtil sUtil = new SessionUtil();
                sUtil.SessionSetPost(post);

                intent = new Intent(this, MyOneActivity.class);
                startActivity(intent);
            } else {
                ToastUtil.show(this, "修改失败");
                intent = new Intent(this, MyPostActivity.class);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.btn_deletePost) {
            /* 待优化：添加事务 */

            /* 删除帖子 */
            postDao.delete(post.getId());

            /* 删除收藏过该帖子的collect字段 */
            collectsDao = myApp.getCampusInfoDB().collectsDao();
            List<Collects> collectsList = collectsDao.queryByPostId(post.getId());
            for (Collects c1: collectsList) {
                collectsDao.delete(c1);
            }

            /* 也要把用户收藏数减少，这里使用lazy标记的思想，当用户查看自己收藏的时候再去更新用户收藏夹。（由于user没有收藏字段，该行可忽略） */
            ToastUtil.show(this, "删除成功");

            intent = new Intent(this, MyPostActivity.class);
            startActivity(intent);
        }
    }

    private int findRadioButtonChecked(String block_name) {
        switch (block_name) {
            case "瓜田趣事":
                return 1;
            case "二手闲置":
                return 2;
            case "打听求助":
                return 3;
            case "新闻八卦":
                return 4;
        }
        return 1;
    }
}