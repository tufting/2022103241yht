package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.edu.nenu.util.ToastUtil;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_signature;
    private MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        tv_name = findViewById(R.id.tv_name);
        tv_signature = findViewById(R.id.tv_signature);

        myApp = MyApplication.getInstance();

        tv_name.setText(myApp.infoMap.get("cur_name"));
        tv_signature.setText(myApp.infoMap.get("cur_signature"));

        findViewById(R.id.tv_update).setOnClickListener(this);
        findViewById(R.id.iv_update).setOnClickListener(this);

        findViewById(R.id.tv_myPost).setOnClickListener(this);
        findViewById(R.id.iv_myPost).setOnClickListener(this);

        findViewById(R.id.tv_addPost).setOnClickListener(this);
        findViewById(R.id.iv_addPost).setOnClickListener(this);

        findViewById(R.id.tv_logout).setOnClickListener(this);
        findViewById(R.id.iv_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_update:
            case R.id.iv_update:
                intent = new Intent(this, UpdateUserActivity.class);
                startActivity(intent);

                ToastUtil.show(this, "更改信息");
                break;
            case R.id.tv_myPost:
            case R.id.iv_myPost:
                break;
            case R.id.tv_addPost:
            case R.id.iv_addPost:
                intent = new Intent(this, AddPostActivity.class);
                startActivity(intent);

                ToastUtil.show(this, "发布新帖");
                break;
            case R.id.tv_logout:
            case R.id.iv_logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                ToastUtil.show(this, "已退出");
                break;
        }
    }
}