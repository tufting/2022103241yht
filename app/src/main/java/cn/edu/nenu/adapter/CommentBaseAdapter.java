package cn.edu.nenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.edu.nenu.R;
import cn.edu.nenu.entity.Comment;
import cn.edu.nenu.entity.Post;

public class CommentBaseAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> commentList;
    private Map<Integer, String> userMap;

    public CommentBaseAdapter(Context context, List<Comment> commentList, Map<Integer, String> userMap) {
        this.context = context;
        this.commentList = commentList;
        this.userMap = userMap;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
//        一页视图足够展示数据时
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder = new ViewHolder();
            holder.tv_author = convertView.findViewById(R.id.tv_author);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

//        给holder设置数据
        Comment comment = commentList.get(i);
        holder.tv_author.setText(userMap.get(comment.getUserId()));
        holder.tv_content.setText(comment.getContent());
        holder.tv_time.setText(comment.getTime());

        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_author;
        public TextView tv_content;
        public TextView tv_time;
    }
}
