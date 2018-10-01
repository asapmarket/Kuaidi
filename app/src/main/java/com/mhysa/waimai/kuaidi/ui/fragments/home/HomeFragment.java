package com.mhysa.waimai.kuaidi.ui.fragments.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.joey.devilfish.fusion.FusionCode;
import com.joey.devilfish.model.tab.TabInfo;
import com.joey.devilfish.ui.fragment.base.BaseFragment;
import com.joey.devilfish.widget.indicator.TitleIndicator;
import com.joey.devilfish.widget.indicator.ViewPagerCompat;
import com.mhysa.waimai.kuaidi.R;
import com.mhysa.waimai.kuaidi.ui.activities.errandorder.ErrandOrderListActivity;
import com.mhysa.waimai.kuaidi.ui.activities.order.OrderListMapActivity;
import com.mhysa.waimai.kuaidi.ui.fragments.errand.ErrandOrderZipCodeListFragment;
import com.mhysa.waimai.kuaidi.ui.fragments.order.OrderHomeZipcodeListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 文件描述
 * Date: 2017/7/21
 *
 * @author xusheng
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final int TAB_TAKEOUT = 0;

    private static final int TAB_ERRAND = 1;

    private int mCurrentTab = TAB_TAKEOUT;

    private TitleIndicator mTitleIndicator;

    private ViewPagerCompat mViewPager;

    private MyAdapter mAdapter;

    private List<TabInfo> mTabInfoList = new ArrayList<TabInfo>();

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initContentView() {
        super.initContentView();

        mViewPager = (ViewPagerCompat) mRootView.findViewById(R.id.vpc_order_content);
        mViewPager.setNoScroll(false);
        mTitleIndicator = (TitleIndicator) mRootView.findViewById(R.id.ti_order_title);
        bindTabList();
        bindViewPager();
    }

    private void bindTabList() {
        mTabInfoList.add(new TabInfo(TAB_TAKEOUT, getResources().getString(R.string.takeout_order)));
        mTabInfoList.add(new TabInfo(TAB_ERRAND, getResources().getString(R.string.errand_order)));
        mTitleIndicator.init(mCurrentTab, mTabInfoList, mViewPager, false);
    }

    private void bindViewPager() {
        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(mTabInfoList.size());
        mViewPager.setCurrentItem(mCurrentTab);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTitleIndicator.onScrolled((mViewPager.getWidth() + mViewPager.getPageMargin()) * position + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mTitleIndicator.onSwitched(position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            BaseFragment fragment = null;
            switch (pos) {
                case TAB_TAKEOUT:
                    OrderHomeZipcodeListFragment firstFragment = new OrderHomeZipcodeListFragment();
                    fragment = firstFragment;
                    break;
                case TAB_ERRAND:
                    ErrandOrderZipCodeListFragment secondFragment = new ErrandOrderZipCodeListFragment();
                    secondFragment.setState(FusionCode.OrderStatus.ORDER_STATUS_CHECKOUT_SUCCESS);
                    secondFragment.setExpId("");
                    secondFragment.setTag(ErrandOrderListActivity.TAG_HOME);
                    fragment = secondFragment;
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mTabInfoList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseFragment fragment = null;
            switch (position) {
                case TAB_TAKEOUT:
                    fragment = (OrderHomeZipcodeListFragment) super.instantiateItem(container, position);
                    break;
                case TAB_ERRAND:
                    fragment = (ErrandOrderZipCodeListFragment) super.instantiateItem(container, position);
                    break;
                default:
                    break;
            }

            return fragment;
        }
    }

    @OnClick({R.id.tv_map})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_map: {
                // 地图接单
                OrderListMapActivity.invoke(mContext);
                break;
            }

            default: {
                break;
            }
        }
    }
}
