package cn.edu.nenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.TimeUtil;
import cn.edu.nenu.util.ToastUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_account;
    private EditText et_pwd;
    private Button btn_register;
    private EditText et_acc_hint;
    private UserDao userDao;
    private List<User> uList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("execute log", "执行了RegisterActivity类的onCreate()方法...");

        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        btn_register = findViewById(R.id.btn_register);
        et_acc_hint = findViewById(R.id.et_acc_hint);

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

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

//    监听方法
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            uList = userDao.queryAll();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String acc = et_account.getText().toString();
            int flag = 1;
            for (User u: uList) {
                if (et_pwd.getText().length() == 0) {
                    break;
                }
                if (acc.equals(u.getAccount())) {
                    flag = 0;
                    et_acc_hint.setText("用户名已存在");
                    break;
                }
            }

            if (et_account.getText().toString().equals("admin")) {
                flag = 0;
                et_acc_hint.setText("用户名不可用");
            } else if (et_account.getText().length() == 0 || et_pwd.getText().length() == 0) {
                flag = 0;
            } else {
                btn_register.setEnabled(true);
                btn_register.setBackground(getResources().getDrawable(R.drawable.shape_big_register_btn));
            }

            if (flag == 1) {
                et_acc_hint.setText("");
            } else {
                btn_register.setEnabled(false);
                btn_register.setBackground(getResources().getDrawable(R.drawable.shape_big_btn_unable));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}