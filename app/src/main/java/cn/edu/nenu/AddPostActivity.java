package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.edu.nenu.entity.Post;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_content;
    private RadioGroup rb_block;
    private Button btn_addPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        rb_block = findViewById(R.id.rg_block);


        btn_addPost = findViewById(R.id.btn_addPost);
        btn_addPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Post post = new Post();
//        判断单选框那个被选中
        for (int i = 0; i < rb_block.getChildCount(); i++) {
            RadioButton r = (RadioButton)rb_block.getChildAt(i);
            if (r.isChecked()) {
                post.setBlock(r.getText().toString());
            }
        }


    }
}