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
import cn.edu.nenu.entity.Post;

public class MPostBaseAdapter extends BaseAdapter {

    private Context context;
    private List<Post> postList;
    private Map<Integer, String> userMap;

    public MPostBaseAdapter(Context context, List<Post> postList, Map<Integer, String> userMap) {
        this.context = context;
        this.postList = postList;
        this.userMap = userMap;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mpost, null);
            holder = new ViewHolder();
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_block = convertView.findViewById(R.id.tv_block);
            holder.tv_author = convertView.findViewById(R.id.tv_author);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

//        给holder设置数据
        Post post = postList.get(i);
        holder.tv_title.setText(post.getTitle());
        holder.tv_author.setText(userMap.get(Integer.parseInt(post.getAuthor())));
        holder.tv_block.setText(post.getBlock());
        holder.tv_time.setText(post.getTime());

        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_title;
        public TextView tv_block;
        public TextView tv_author;
        public TextView tv_time;
    }
}
