package cn.edu.nenu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.edu.nenu.entity.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM User WHERE id = :id")
    void delete(int id);

    @Update
    int update(User user);

    @Query("SELECT * FROM User")
    List<User> queryAll();

    @Query("SELECT * FROM User WHERE account = :account ORDER BY id LIMIT 1")
    User queryByAccount(String account);

    @Query("SELECT * FROM User WHERE account = :account and password = :password")
    User queryByAcc_Pwd(String account, String password);

    @Query("SELECT * FROM User WHERE account != 'admin'")
    List<User> queryAllNoAdminAcc();
}
