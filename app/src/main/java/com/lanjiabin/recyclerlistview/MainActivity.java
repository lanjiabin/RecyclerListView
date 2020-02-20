package com.lanjiabin.recyclerlistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<String> mList = new ArrayList<>();
    private Button addDataButton, changeData;

    /**
     * 记录用户选择了RecyclerListView哪个item
     * 赋值-1是为了防止默认选中position=0的item
     */
    private int mPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRecyclerView();
    }

    public void initView() {
        addDataButton = findViewById(R.id.addData);
        changeData = findViewById(R.id.changeData);
        mRecyclerView = findViewById(R.id.recyclerListView);

        //添加数据到选择item的上方
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 因为添加了header和footer，所以mPosition的值是从-1到 mList.size()+1
                 * 头部和尾部的item是不应该支持修改数据的，因为用的是不同的布局，数据也不是相同的
                 * */
                if (mPosition>=0 && mPosition<mList.size()){
                    mRecyclerAdapter.addData(mPosition);
                    mRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });

        //修改选中item的数据
        changeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 因为添加了header和footer，所以mPosition的值是从-1到 mList.size()+1
                 * 头部和尾部的item是不应该支持修改数据的，因为用的是不同的布局，数据也不是相同的
                 * */
                if (mPosition>=0 && mPosition<mList.size()){
                    mRecyclerAdapter.changeData(mPosition);
                    mRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });


    }

    public void initRecyclerView() {

        //添加测试数据
        for (int i = 0; i < 20; i++) {
            mList.add(i + "-----");
        }
        //设置布局方向-默认布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //设置item增加和删除时的动画，这里设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //1.添加默认分割线：高度为2px，颜色为灰色
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        //设置adapter关联的list
        mRecyclerAdapter = new RecyclerAdapter(this, mList,mRecyclerView);

        //利用GestureDetector手势检测类来实现回调
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //颜色改变了，更新Adapter
                mRecyclerAdapter.notifyDataSetChanged();

                //记录用户选择了哪个item
                mPosition = position-1;

                //传递点击了哪个item，以此用来更新选中item的颜色
                mRecyclerAdapter.getCurrentPosition(position);

                Toast.makeText(MainActivity.this, "单击了：" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                /**
                 * 因为添加了header和footer，所以mPosition的值是从-1到 mList.size()+1
                 * 头部和尾部的item是不应该支持修改数据的，因为用的是不同的布局，数据也不是相同的
                 * */
                if (position>0 && position<=mList.size()){
                    //长按删除的item
                    mRecyclerAdapter.removeData(position);
                }

                Toast.makeText(MainActivity.this, "长击了：" + (position), Toast.LENGTH_SHORT).show();
            }
        }));

        //关联adapter
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

}

