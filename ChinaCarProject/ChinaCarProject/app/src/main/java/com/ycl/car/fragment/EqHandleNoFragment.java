package com.ycl.car.fragment;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ycl.car.R;
import com.ycl.car.adapter.EqHandleAdapter;
import com.ycl.car.adapter.EqHandleNoAdapter;
import com.ycl.car.contract.EqHandleContract;
import com.ycl.car.databinding.FragmentEqHandleBinding;
import com.ycl.car.databinding.FragmentEqHandleNoBinding;
import com.ycl.car.model.Repair;
import com.ycl.car.presenter.EqHandlePresenter;

import java.util.Calendar;
import java.util.List;

/**
 * 设备维修--未处理
 * Created by y11621546 on 2017/3/4.
 */

public class EqHandleNoFragment extends BasePageFragment implements EqHandleContract.View {
    private FragmentEqHandleNoBinding binding;
    private EqHandlePresenter presenter;
    private String uid, eqno, status;
    private KProgressHUD kProgressHUD;
    private String startTime, endTime;
    private List<Repair> repairList;
    private EqHandleNoAdapter adapter;

    public static EqHandleNoFragment newInstance(String uid, String eqno, String status) {

        Bundle args = new Bundle();
        args.putString("uid", uid);
        args.putString("eqno", eqno);
        args.putString("status", status);
        EqHandleNoFragment fragment = new EqHandleNoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void fetchData() {
        presenter.getHandleList(uid, eqno, status);
    }

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EqHandlePresenter(this);
        uid = getArguments().getString("uid");
        eqno = getArguments().getString("eqno", null);
        status = getArguments().getString("status");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_eq_handle_no, container, false);
        binding.rvEquiment.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvEquiment.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.rvEquiment.setHasFixedSize(true);
        if ("0".equals(status)) {
            binding.llTop.setVisibility(View.GONE);
        } else {
            binding.llTop.setVisibility(View.VISIBLE);
        }
        binding.tvQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                    if (startTime.compareTo(endTime) < 0) {
                        presenter.queryHandleList(uid, null, status, startTime, endTime);
                    } else {
                        Toast.makeText(getActivity(), "开始时间应小于结束时间", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "请选择时间", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.spinnerStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                startTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                                binding.spinnerStartTime.setText(startTime);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();//显示日期设置对话框
            }
        });
        binding.spinnerEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                endTime = year + "-" + monthOfYear + "-" + dayOfMonth;
                                binding.spinnerEndTime.setText(endTime);

                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();//显示日期设置对话框
            }

        });
        return binding.getRoot();
    }

    @Override
    public void setPresenter(EqHandleContract.Presenter presenter) {

    }

    @Override
    public void showLoading() {
        if (kProgressHUD == null) {
            kProgressHUD = KProgressHUD.create(getContext())
                    .setLabel("正在加载...")
                    .show();
        }

    }

    @Override
    public void dismissLoading() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }

    }

    @Override
    public void onSuccess(List<Repair> repairList) {
        adapter = new EqHandleNoAdapter(repairList);
        binding.rvEquiment.setAdapter(adapter);


    }


    @Override
    public void onQuerySuccess(List<Repair> repairList) {
        Log.d("EqHandleFragment", "repairList.size():" + repairList.size());
        Log.d("EqHandleFragment", "repairList.isEmpty():" + repairList.isEmpty());
        adapter = new EqHandleNoAdapter(repairList);
        binding.rvEquiment.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_item_empty, (ViewGroup) binding.rvEquiment.getParent());
    }


    @Override
    public void onError() {
        Toast.makeText(getActivity(), "数据加载失败，请稍候重试", Toast.LENGTH_SHORT).show();
    }
}
