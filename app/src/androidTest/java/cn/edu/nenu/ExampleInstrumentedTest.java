package cn.edu.nenu;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

import cn.edu.nenu.dao.CollectsDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private MyApplication myApp;
    private User user;
    private Post post;
    private Collects collects;
    private UserDao userDao;
    private PostDao postDao;
    private CollectsDao collectsDao;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cn.edu.nenu", appContext.getPackageName());
    }

    public void instance() {
        myApp = MyApplication.getInstance();

        userDao = myApp.getCampusInfoDB().userDao();
        postDao = myApp.getCampusInfoDB().postDao();
        collectsDao = myApp.getCampusInfoDB().collectsDao();
    }

    @Test
    public void queryAllUser() {
        instance();

        List<User> userList = userDao.queryAll();
        for (User user: userList) {
            Log.d("execute log", user.toString());
        }
    }

    @Test
    public void queryAllPost() {
        instance();

        List<Post> postList = postDao.queryAll();
        for (Post post: postList) {
            Log.d("execute log", post.toString());
        }
    }

    @Test
    public void queryALlCollects() {
        instance();

        List<Collects> collectsList = collectsDao.queryAll();
        for (Collects collects: collectsList) {
            Log.d("execute log", collects.toString());
        }
    }
}