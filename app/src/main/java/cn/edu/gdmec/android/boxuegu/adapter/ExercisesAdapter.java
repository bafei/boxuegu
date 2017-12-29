package cn.edu.gdmec.android.boxuegu.adapter;

/**
 * Created by student on 17/12/28.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;

/**
 * Created by ASUS PRO on 2017/12/25.
 */

public class ExercisesAdapter extends BaseAdapter {

    private Context mContext;

    public ExercisesAdapter(Context mContext){
        this.mContext = mContext;
    }

    private List<ExercisesBean> eb1;

    public void setData(List<ExercisesBean> eb1){
        this.eb1 = eb1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eb1 == null ? 0 : eb1.size();
    }

    @Override
    public Object getItem(int position) {
        return eb1 == null ?  null : eb1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;

        if (convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item,null);
            vh.title = convertView.findViewById(R.id.tv_title);
            vh.content = convertView.findViewById(R.id.tv_content);
            vh.order = convertView.findViewById(R.id.tv_order);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final ExercisesBean bean = (ExercisesBean) getItem(position);
        if (bean != null){
            vh.order.setText(position + 1 + "");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean == null){
                    return;
                }
                //跳转到详情页
                Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
                intent.putExtra("id",bean.id);
                intent.putExtra("title",bean.title);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        public TextView title,content;
        public TextView order;
    }
}