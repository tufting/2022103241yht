package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.ToastUtil;

public class UpdateUserActivity extends AppCompatActivity implements View.OnClickListener {

    private MyApplication myApp;

    private TextView tv_account;
    private EditText et_password;
    private EditText et_name;
    private TextView tv_regTime;
    private EditText et_signature;

    private User user;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Log.d("execute log", "执行了UpdateUserActivity类的onCreate()方法...");

//        1、通过Application获取用户信息
        myApp = MyApplication.getInstance();
        user = new User();
        user.setId(Integer.parseInt(myApp.infoMap.get("cur_id")));
        user.setAccount(myApp.infoMap.get("cur_account"));
        user.setPassword(myApp.infoMap.get("cur_password"));
        user.setName(myApp.infoMap.get("cur_name"));
        user.setRegTime(myApp.infoMap.get("cur_regTime"));
        user.setSignature(myApp.infoMap.get("cur_signature"));

        Log.d("execute log", "当前用户信息：" + user.toString());

//        2、获取et的id
        tv_account = findViewById(R.id.tv_account);
        et_password = findViewById(R.id.et_password);
        et_name = findViewById(R.id.et_name);
        tv_regTime = findViewById(R.id.tv_regTime);
        et_signature = findViewById(R.id.et_signature);

//        3、设置et文本
        tv_account.setText(user.getAccount());
        et_password.setText(user.getPassword());
        et_name.setText(user.getName());
        tv_regTime.setText(user.getRegTime());
        et_signature.setText(user.getSignature());

        userDao = myApp.getCampusInfoDB().userDao();

        et_password.addTextChangedListener(textWatcher);
        et_name.addTextChangedListener(textWatcher);

//        4、设置修改button监听，imgview的返回监听
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if(view.getId() == R.id.btn_update) {
//            5、将修改后的信息提交到数据库
            user.setPassword(et_password.getText().toString());
            user.setName(et_name.getText().toString());
            user.setSignature(et_signature.getText().toString());

            int flag = userDao.update(user);

//            6、弹出相关提示
            if (flag != 0) {
                ToastUtil.show(this, "修改成功");

                SessionUtil sUtil = new SessionUtil(); // 保存当前用户信息
                sUtil.SessionSetUser(user);
            } else {
                ToastUtil.show(this, "修改失败");
            }

//            7、更新当前页面信息
            intent = new Intent(this, UpdateUserActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.iv_back) {
            intent = new Intent(this, MyActivity.class);
            startActivity(intent);
        }
    }

//    监听方法
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (et_password.getText().length() == 0 || et_name.getText().length() == 0) {
                findViewById(R.id.btn_update).setEnabled(false);
            } else {
                findViewById(R.id.btn_update).setEnabled(true);
            }
        }
    };
}