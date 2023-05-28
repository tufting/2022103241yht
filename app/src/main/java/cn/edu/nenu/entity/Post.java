package cn.edu.nenu.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;

//    标题、正文、板块、发表时间、作者、收藏数
    private String title;
    private String content;
    private String block;
    private String time;

    private String author;
    private int collects_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCollects_num() {
        return collects_num;
    }

    public void setCollects_num(int collects_num) {
        this.collects_num = collects_num;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", block='" + block + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", collects_num=" + collects_num +
                '}';
    }
}
