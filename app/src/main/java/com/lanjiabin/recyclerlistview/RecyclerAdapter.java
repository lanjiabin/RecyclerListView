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

    public RecyclerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 该方法会在RecyclerView需要展示一个item的时候回调，
     * 重写该方法，使ViewHolder加载item的布局，布局复用，提高性能，
     * 就ListView的优化一样，只不过RecyclerView把这个集成到官方方法中了。
     * */
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
     * */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));
    }

    /**
     * 该方法是返回item的数量。
     * */
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
