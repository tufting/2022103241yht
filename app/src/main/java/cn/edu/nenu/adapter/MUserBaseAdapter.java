package cn.edu.nenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.nenu.R;
import cn.edu.nenu.entity.User;

public class MUserBaseAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;

    public MUserBaseAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_muser, null);
            holder = new ViewHolder();
            holder.tv_id = convertView.findViewById(R.id.tv_id);
            holder.tv_account = convertView.findViewById(R.id.tv_account);
            holder.tv_password = convertView.findViewById(R.id.tv_password);
            holder.tv_regTime = convertView.findViewById(R.id.tv_regTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

//        给holder设置数据
        User user = userList.get(i);
        holder.tv_id.setText(String.valueOf(user.getId()));
        holder.tv_account.setText(user.getAccount());
        holder.tv_password.setText(user.getPassword());
        holder.tv_regTime.setText(user.getRegTime());

        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_id;
        public TextView tv_account;
        public TextView tv_password;
        public TextView tv_regTime;
    }
}
