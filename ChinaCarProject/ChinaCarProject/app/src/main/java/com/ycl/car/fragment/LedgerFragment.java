package com.ycl.car.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycl.car.Next1Activity;
import com.ycl.car.R;
import com.ycl.car.contract.LedgerContract;
import com.ycl.car.databinding.FragmentLedgerBinding;
import com.ycl.car.model.C;
import com.ycl.car.model.D;
import com.ycl.car.model.Ledger;
import com.ycl.car.presenter.LedgerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y11621546 on 2017/3/4.
 */

public class LedgerFragment extends BasePageFragment implements LedgerContract.View {
    private FragmentLedgerBinding binding;
    private Bundle bundle;

    private LedgerPresenter presenter;

    public static LedgerFragment newInstance(Bundle bundle) {


        LedgerFragment fragment = new LedgerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        presenter = new LedgerPresenter(this);
        presenter.getLedgerList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ledger, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bundle == null) return;
        ((Next1Activity) getActivity()).getBinding().toolbarRight.setText("");
        ((Next1Activity) getActivity()).getBinding().toolbarTitle.setText(bundle.getString("title"));
    }

    @Override
    public void setPresenter(LedgerContract.Presenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(Ledger ledger) {
        List<String> cStringList = new ArrayList<>();
        List<String> dStringList = new ArrayList<>();
        final List<Integer> cIntList = new ArrayList<>();
        final List<Integer> dIntList = new ArrayList<>();


        for (Ledger.CBean cBean : ledger.getC()) {
            cStringList.add(cBean.getTpname());
            cIntList.add(cBean.getId());
        }
        String[][] aa = new String[][]{};
        for (Ledger.DBean dBean : ledger.getD()) {

        }
    }


}
