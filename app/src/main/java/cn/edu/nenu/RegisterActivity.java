package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.database.CampusInfoDatabase;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.TimeUtil;
import cn.edu.nenu.util.ToastUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_account;
    private EditText et_pwd;
    private Button btn_register;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("execute log", "执行了RegisterActivity类的onCreate()方法...");

        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        btn_register = findViewById(R.id.btn_register);

//        添加监听
        et_account.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);

        btn_register.setOnClickListener(this);

        userDao = MyApplication.getInstance().getCampusInfoDB().userDao();
    }

    @Override
    public void onClick(View view) {
        String account = et_account.getText().toString();
        String pwd = et_pwd.getText().toString();
        String currentTime = TimeUtil.getCurrentSystemTime();

//        点击注册，把et_account和et_pwd加到数据库里
        if(view.getId() == R.id.btn_register) {
            User user = new User();
            user.setAccount(account);
            user.setPassword(pwd);
            user.setName("NENU " + account);
            user.setRegTime(currentTime);
            user.setSignature("该用户太懒了，什么都没留下！");

            userDao.insert(user);

            ToastUtil.show(this, "注册成功");

//            打印所有用户信息
            List<User> list = userDao.queryAll();
            for (User u: list) {
                Log.d("execute log", u.toString());
            }

//            跳转到登陆页面
            Intent intent = new Intent(this, LoginActivity.class);
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
            if (et_account.getText().length() == 0 || et_pwd.getText().length() == 0) {
                btn_register.setEnabled(false);
            } else {
                btn_register.setEnabled(true);
            }
        }
    };

}