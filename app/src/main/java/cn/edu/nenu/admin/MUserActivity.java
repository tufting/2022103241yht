package cn.edu.nenu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import cn.edu.nenu.MyApplication;
import cn.edu.nenu.R;
import cn.edu.nenu.adapter.MUserBaseAdapter;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.SessionUtil;
import cn.edu.nenu.util.ToastUtil;

public class MUserActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MyApplication myApp;
    private UserDao userDao;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muser);
        Log.d("execute log", "执行了MUserActivity类的onCreate()方法...");

        /* 待优化：可以增加一个点击注册日期的排序功能。 */
        myApp = MyApplication.getInstance();
        userDao = myApp.getCampusInfoDB().userDao();
        userList = userDao.queryAllNoAdminAcc();

        MUserBaseAdapter adapter = new MUserBaseAdapter(this, userList);

        ListView lv_user = findViewById(R.id.lv_user);
        lv_user.setAdapter(adapter);
        lv_user.setOnItemClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            Intent intent = new Intent(this, ManagerActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(this, "点击了" + userList.get(i).getAccount());

        /* 保存相应User信息，并跳转到相应界面，显示信息 */
        SessionUtil sUtil = new SessionUtil();
        sUtil.SessionSetUser(userList.get(i));

        Intent intent = new Intent(this, UUserActivity.class);
        startActivity(intent);
    }
}