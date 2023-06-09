package cn.edu.nenu.admin;

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

import cn.edu.nenu.MyApplication;
import cn.edu.nenu.MyOneActivity;
import cn.edu.nenu.MyPostActivity;
import cn.edu.nenu.R;
import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.TimeUtil;
import cn.edu.nenu.util.ToastUtil;

public class UPostActivity extends AppCompatActivity implements View.OnClickListener {
    private MyApplication myApp;
    private Post post;
    private PostDao postDao;
    private CollectsDao collectsDao;

    private EditText et_title;
    private EditText et_content;
    private String block;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upost);
        Log.d("execute log", "执行了UPostActivity类的onCreate()方法...");

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
        collectsDao = myApp.getCampusInfoDB().collectsDao();

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if(view.getId() == R.id.btn_update) {
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

                intent = new Intent(this, UPostActivity.class);
            } else {
                ToastUtil.show(this, "修改失败");
                intent = new Intent(this, MPostActivity.class);
            }
        } else if (view.getId() == R.id.btn_delete) {
            /* 删除帖子数据、删除收藏过该帖子的数据 */
            postDao.delete(post.getId());
            collectsDao.deleteByPostId(post.getId());

            intent = new Intent(this, MPostActivity.class);
        } else {
            intent = new Intent(this, MPostActivity.class);
        }
        startActivity(intent);
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