package cn.edu.nenu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import cn.edu.nenu.R;

public class UPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upost);
        Log.d("execute log", "执行了UPostActivity类的onCreate()方法...");


        /* 删除帖子数据、删除收藏过该帖子的数据 */
    }
}