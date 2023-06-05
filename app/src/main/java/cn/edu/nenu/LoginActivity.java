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

import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_account;
    private EditText et_pwd;
    private Button btn_login;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("execute log", "执行了LoginActivity类的onCreate()方法...");
        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);

        /* 添加监听 */
        et_account.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);

        btn_login.setOnClickListener(this);

        userDao = MyApplication.getInstance().getCampusInfoDB().userDao();
    }

    @Override
    public void onClick(View view) {
        String account = et_account.getText().toString();
        String pwd = et_pwd.getText().toString();

        /* 点击登录按钮 */
        if(view.getId() == R.id.btn_login) {
            User user = userDao.queryByAcc_Pwd(account, pwd);

            if (user != null) {
                Log.d("execute log", String.valueOf(user));
                ToastUtil.show(this, "登录成功");

                /* 保存当前用户信息 */
                SessionUtil sUtil = new SessionUtil();
                sUtil.SessionSetUser(user);

                /* 获取当前用户收藏过哪些帖子 */


                /* 跳转到资讯主页 */
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                ToastUtil.show(this, "用户名或密码错误！");
            }
        }
    }

    /* 监听方法 */
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
                btn_login.setEnabled(false);
                btn_login.setBackground(getResources().getDrawable(R.drawable.shape_big_btn_unable));
            } else {
                btn_login.setEnabled(true);
                btn_login.setBackground(getResources().getDrawable(R.drawable.shape_big_login_btn));
            }
        }
    };
}