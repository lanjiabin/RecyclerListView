package com.lanjiabin.recyclerlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<String> mList;
    private Context mContext;

    //记录用户选择了RecyclerListView哪个item
    //赋值-1是为了防止默认选中position=0的item
    private int mPosition = -1;

    public RecyclerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    //获取当前点击item的position
    public void getCurrentPosition(int position) {
        this.mPosition = position;
    }

    //移除数据
    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    //新增数据
    public void addData(int position) {
        mList.add(position, "Add One" + position);
        notifyItemInserted(position);
    }

    //更改某个位置的数据
    public void changeData(int position) {
        mList.set(position, "Item " + position + " has changed");
        notifyItemChanged(position);
    }

    /**
     * 该方法会在RecyclerView需要展示一个item的时候回调，
     * 重写该方法，使ViewHolder加载item的布局，布局复用，提高性能，
     * 就ListView的优化一样，只不过RecyclerView把这个集成到官方方法中了。
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(
                LayoutInflater.from(mContext)
                        .inflate(
                                R.layout.item_recycler,
                                parent,
                                false));
        return holder;
    }

    /**
     * 该方法是填充绑定item数据的
     */
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.textView.setText(mList.get(position));

        //修改选中item的颜色
        if (mPosition == position) {
            holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }

    /**
     * 该方法是返回item的数量。
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
