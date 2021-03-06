package com.ycl.car.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ycl.car.R;
import com.ycl.car.adapter.MainNewsAdapter;
import com.ycl.car.contract.NewsContract;
import com.ycl.car.databinding.FragmentNewsDynamicBinding;
import com.ycl.car.model.MainNews;
import com.ycl.car.presenter.NewsPresenter;
import com.ycl.car.utils.HttpManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 研发中心动态
 * Created by y11621546 on 2017/1/18.
 */

public class NewsDynamicFragment extends BasePageFragment implements NewsContract.View {
    FragmentNewsDynamicBinding binding;
    NewsPresenter presenter;

    public static NewsDynamicFragment newInstance() {

        Bundle args = new Bundle();
        NewsDynamicFragment fragment = new NewsDynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_dynamic, container, false);
        binding.rvNewsDynamic.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.rvNewsDynamic.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMainNews("51");
            }
        });
        return binding.getRoot();
    }


    @Override
    public void showLoading() {
        binding.swipeRefresh.setRefreshing(true);
    }

    @Override
    public void dismissLoading() {
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSuccess(List<MainNews.BBean> list) {
        MainNewsAdapter adapter = new MainNewsAdapter(list);
        binding.rvNewsDynamic.setAdapter(adapter);
    }

    @Override
    public void onError() {
        Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Override
    public void fetchData() {
        presenter.getMainNews("51");
    }
}
