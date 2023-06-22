package cn.edu.nenu.database;

import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.CommentDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Comment;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;

@Database(entities = {User.class, Post.class, Collects.class, Comment.class}, version = 2, exportSchema = true)
public abstract class CampusInfoDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract CollectsDao collectsDao();
    public abstract CommentDao commentDao();

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `Comment` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `content` TEXT, `time` TEXT, `userId` TEXT, `postId` TEXT)");
    }



}
