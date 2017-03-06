package com.ycl.car.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycl.car.NextActivity;
import com.ycl.car.R;
import com.ycl.car.adapter.PostAdapter;
import com.ycl.car.adapter.PostItemAdapter;
import com.ycl.car.databinding.FragmentAboutBinding;
import com.ycl.car.databinding.FragmentPostBinding;
import com.ycl.car.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的帖子
 * Created by y11621546 on 2017/1/18.
 */

public class PostFragment extends BasePageFragment {
    private FragmentPostBinding binding;
    private static final String text = "中国汽车工业工程有限公司由原机械工业部第四,第五两个设计元合并而成.现为中国机械工业集团全资子公司";

    public static PostFragment newInstance() {

        Bundle args = new Bundle();

        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        binding.rvPost.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Post.BBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Post.BBean());
        }
        PostAdapter adapter = new PostAdapter(list);
        binding.rvPost.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((NextActivity) getActivity()).getBinding().toolbarTitle.setText("我的帖子");
        ((NextActivity) getActivity()).getBinding().toolbarRight.setText("");
    }

    @Override
    public void fetchData() {
        System.out.println("MessageFragment.fetchData");
    }


}
