package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.util.TimeUtil;
import cn.edu.nenu.util.ToastUtil;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_content;
    private String block;

    private MyApplication myApp;
    private PostDao postDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        Log.d("execute log", "AddPostActivity类onCreate()被执行。");

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

//        找到用户选择的block
        RadioGroup rb_block = findViewById(R.id.rg_block);
        rb_block.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = rb_block.findViewById(checkedId);
                block = radioButton.getText().toString();
                Log.d("execute log", "block = " + block);
            }
        });

        myApp = MyApplication.getInstance();
        postDao = myApp.getCampusInfoDB().postDao();

        findViewById(R.id.btn_addPost).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if (view.getId() == R.id.btn_addPost) {
            String title = et_title.getText().toString();
            String content =et_content.getText().toString();
            String currentTime = TimeUtil.getCurrentSystemTime();
            String author = myApp.infoMap.get("cur_id");

            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setTime(currentTime);
            post.setAuthor(author);
            post.setCollects_num(0);
            post.setBlock(block);

            Log.d("execute log", post.toString());

            postDao.insert(post);
            ToastUtil.show(this, "发布成功");

            intent = new Intent(this, MyPostActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_back) {
            intent = new Intent(this, MyActivity.class);
            startActivity(intent);
        }
    }
}