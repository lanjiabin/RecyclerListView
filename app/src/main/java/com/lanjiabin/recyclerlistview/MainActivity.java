package com.lanjiabin.recyclerlistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        for (int i = 0; i < 100; i++) {
            mList.add(i + " ");
        }
        //布局关联
        mRecyclerView = findViewById(R.id.recyclerListView);
        //设置布局方向-表格布局
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,5));
        //设置item增加和删除时的动画，这里设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置adapter关联的list
        mRecyclerAdapter = new RecyclerAdapter(this, mList);
        //数据更新
        mRecyclerAdapter.notifyDataSetChanged();
        //关联adapter
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}

