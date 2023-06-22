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
import cn.edu.nenu.dao.CommentDao;
import cn.edu.nenu.dao.PostDao;
import cn.edu.nenu.dao.UserDao;
import cn.edu.nenu.entity.Collects;
import cn.edu.nenu.entity.Comment;
import cn.edu.nenu.entity.Post;
import cn.edu.nenu.entity.User;
import cn.edu.nenu.util.TimeUtil;

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
    private Comment comment;
    private UserDao userDao;
    private PostDao postDao;
    private CollectsDao collectsDao;
    private CommentDao commentDao;

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
    public void addPost() {
        instance();

        int random = 1;
        for (int i = 1; i <= 5; i++) {
            post = new Post();
            post.setTitle("title");
            post.setContent("content");
            post.setTime(TimeUtil.getCurrentSystemTime());

            random = (int)(Math.random() * 20) + 1;
            post.setAuthor(String.valueOf(random));
            post.setCollects_num(0);

            random = (int)(Math.random() * 10);
            random = random % 4;
            String block;
            if (random == 0) {
                block = "瓜田趣事";
            } else if (random == 1) {
                block = "二手闲置";
            } else if (random == 2) {
                block = "打听求助";
            } else {
                block = "新闻八卦";
            }
            post.setBlock(block);

            postDao.insert(post);
        }
    }

    @Test
    public void registerUser() {
        instance();

        for (int i = 5; i <= 20; i++) {
            user = new User();
            user.setAccount("test" + i);
            user.setPassword("123");
            user.setName("NENU test" + i);
            user.setRegTime(TimeUtil.getCurrentSystemTime());
            user.setSignature("该用户太懒了，什么都没留下！");
            userDao.insert(user);
        }
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