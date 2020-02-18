package com.lanjiabin.recyclerlistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        //添加测试数据
        for (int i = 0; i < 1000; i++) {
            mList.add(i + " ");
        }
        //布局关联
        mRecyclerView = findViewById(R.id.recyclerListView);
        //设置布局方向-默认布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置item增加和删除时的动画，这里设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //1.添加默认分割线：高度为2px，颜色为灰色
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
//
//        //2.添加自定义分割线：可自定义分割线drawable
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                this, LinearLayoutManager.HORIZONTAL, R.drawable.recyclerview_divideritem));
//
//        //3.添加自定义分割线：可自定义分割线高度和颜色
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                this, LinearLayoutManager.HORIZONTAL, 3, getResources().getColor(R.color.colorPrimaryDark)));

        //设置adapter关联的list
        mRecyclerAdapter = new RecyclerAdapter(this, mList);

        //item单击
        mRecyclerAdapter.setmOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"点击了："+position,Toast.LENGTH_SHORT).show();

            }
        });

        //item长按监听
        mRecyclerAdapter.setmOnItemLongClickListener(new RecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"长点击了："+position,Toast.LENGTH_SHORT).show();

            }
        });

        //数据更新
        mRecyclerAdapter.notifyDataSetChanged();
        //关联adapter
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}

