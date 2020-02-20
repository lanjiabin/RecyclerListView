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
    /**
     * 记录用户选择了RecyclerListView哪个item
     * 赋值-1是为了防止默认选中position=0的item
     */
    private int mPosition = -1;

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    /**
     * mHeaderView 头部
     * mFooterView 尾部
     */
    private View mHeaderView;
    private View mFooterView;

    public RecyclerAdapter(Context context, List<String> list, RecyclerView recyclerView) {
        this.mContext = context;
        this.mList = list;
        this.mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.header_recylerview, recyclerView, false);
        this.mFooterView = LayoutInflater.from(mContext).inflate(R.layout.footer_recylerview, recyclerView, false);
    }

    //获取当前点击item的position
    public void getCurrentPosition(int position) {
        this.mPosition = position;
    }

    //移除数据
    public void removeData(int position) {
        /**
         * 这里要重点讲解一下；
         * 添加了header和footer之后；
         * header的位置就是recyclerView是在position为0位置，footer是在position为recyclerView.size()-1的位置；
         * 那数据list真正占用的位置就是recyclerView的 1 --> recyclerView.size()-2 位置
         * 当点击recyclerView的footer的前一个位置的时候（recyclerView.size()-2）
         * 得到的recyclerView的position为recyclerView.size()+1，而list的position为list.size()
         * 位置差了一个1,所以在传递最后一个数据点击删除的时候，会数组越界
         * 举例：position指的是下标
         * list的长度为10，那数据的position就是 0 ~ 9；
         * 加入header和footer后，recyclerView的position=0 的位置就是header；
         * position=11 的位置就是footer；
         * list的 position=0 的数据放在recyclerView的 position=1 的位置；
         * list的 position=9 的数据放在recyclerView的 position=10 的位置；
         * 长按recyclerView的 position=10的位置的时候，传递的方法remove(position)中的position=10；
         * 传递的是recyclerView的position，就成了 mList.remove(10);
         * mList.remove(10)，明显数组越界了，因为mList最大的position为9；
         * 所以在删除最后一个数据的时候，要特别处理，就是下面的方法。
         * 重点是notifyItemRemoved(position);方法
         * 删除的position - 1处的list数据
         * 而更新的是recyclerView的position的位置
         * 因为他们错位了 1
         * */
        mList.remove(position - 1);
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

    //获取item的view的类型
    @Override
    public int getItemViewType(int position) {

        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1 && mFooterView != null) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 该方法会在RecyclerView需要展示一个item的时候回调，
     * 重写该方法，使ViewHolder加载item的布局，布局复用，提高性能，
     * 就ListView的优化一样，只不过RecyclerView把这个集成到官方方法中了。
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);

        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MyViewHolder(mFooterView);
        }

        MyViewHolder holder = new MyViewHolder(
                LayoutInflater.from(mContext)
                        .inflate(
                                R.layout.item_recyclerview,
                                parent,
                                false));
        return holder;
    }

    /**
     * 该方法是填充绑定item数据的
     */
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof MyViewHolder) {
                holder.textView.setText(mList.get(position - 1));
                //修改选中item的颜色
                if (mPosition == position) {
                    holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            holder.headerTextView.setText("----我是头部header----");
            return;
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            holder.footerTextView.setText("++++我是尾部footer+++++");
            return;
        }


    }

    /**
     * 该方法是返回item的数量。
     */
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mList.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return mList.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return mList.size() + 1;
        } else {
            return mList.size() + 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView, headerTextView, footerTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            /**
             * 如果是headerView或者是footerView,直接返回
             * */
            if (itemView == mHeaderView) {
                headerTextView = mHeaderView.findViewById(R.id.headerTextView);
                return;
            }
            if (itemView == mFooterView) {
                footerTextView = mFooterView.findViewById(R.id.footerTextView);
                return;
            }
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
