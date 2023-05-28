package cn.edu.nenu.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;

@Database(entities = {User.class, Post.class, Collects.class}, version = 1, exportSchema = true)
public abstract class CampusInfoDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract CollectsDao collectsDao();
}
