package com.ycl.car;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ycl.car.databinding.ActivityDetailBinding;
import com.ycl.car.fragment.PostDetailFragment;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 详情页
 */
public class DetailActivity extends AutoLayoutActivity {
    private ActivityDetailBinding binding;
    private static final String BUNDLE = "bundle";
    private static final String TITLE = "title";
    private Bundle bundle;

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra(BUNDLE, bundle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getBundleExtra(BUNDLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.toolbar.setTitle("");
        if (bundle == null) return;
        binding.toolbarTitle.setText(bundle.getString(TITLE, TITLE));
        setSupportActionBar(binding.toolbar);

        binding.toolbar.setNavigationIcon(R.mipmap.fanhui1_2x);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                View contentview = LayoutInflater.from(DetailActivity.this).inflate(R.layout.layout_popup, null);
                contentview.setFocusable(true); // 这个很重要
                contentview.setFocusableInTouchMode(true);
                PopupWindow popupWindow = new PopupWindow(contentview, 250,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                //点击其他地方消失

                popupWindow.setBackgroundDrawable(new ColorDrawable(0));
                popupWindow.showAsDropDown(binding.toolbarRight);
                return false;
            }
        });

        if ("帖子详情".equals(bundle.getString(TITLE, TITLE))) {
            addFragment(PostDetailFragment.newInstance(), false);
        } else if ("新闻详情".equals(bundle.getString(TITLE, TITLE))) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if ("帖子详情".equals(bundle.getString(TITLE, TITLE))) {
            getMenuInflater().inflate(R.menu.menu_right, menu);

            menu.getItem(0).setIcon(R.mipmap.gengduo1_3x);
        } else if ("新闻详情".equals(bundle.getString(TITLE, TITLE))) {
            getMenuInflater().inflate(R.menu.menu_right, menu);
            menu.getItem(0).setIcon(R.mipmap.fenxiang1_3x);
        }


        return true;
    }

    public ActivityDetailBinding getBinding() {
        return binding;
    }

    //添加fragment
    public void addFragment(Fragment fragment, boolean isAddToBackStack) {
        if (fragment == null) return;
        if (isAddToBackStack) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_detail, fragment).addToBackStack("").commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_detail, fragment).commit();
        }

    }
}
