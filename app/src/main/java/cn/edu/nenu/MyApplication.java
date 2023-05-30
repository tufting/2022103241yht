package cn.edu.nenu;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import java.util.HashMap;

import cn.edu.nenu.database.CampusInfoDatabase;

public class MyApplication extends Application {
    private static MyApplication myApp;
    private CampusInfoDatabase campusInfoDB;

//    全局变量，当成session使用 or 考虑用sharedpreference存储
    public HashMap<String, String> infoMap = new HashMap<>();

//    返回MyApplication实例
    public static MyApplication getInstance() {
        return myApp;
    }

//    App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;

        Log.d("execute log", "执行了MyApplication类的onCreate()方法...");

        campusInfoDB = Room.databaseBuilder(this, CampusInfoDatabase.class, "campus_info")
                .addMigrations().allowMainThreadQueries().build();
    }

//    App终止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("execute log", "执行了MyApplication类的onTerminate()方法...");
    }

//    App配置改变时调用
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("execute log", "执行了MyApplication类的onConfigurationChanged()方法...");
    }

//    获取数据库实例
    public CampusInfoDatabase getCampusInfoDB() {
        return campusInfoDB;
    }
}
