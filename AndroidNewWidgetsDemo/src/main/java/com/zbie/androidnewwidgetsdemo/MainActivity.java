package com.zbie.androidnewwidgetsdemo;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zbie.androidnewwidgetsdemo.adapter.ZbieViewPagerAdapter;
import com.zbie.androidnewwidgetsdemo.fragment.ZbieFragment;
import com.zbie.androidnewwidgetsdemo.utils.SnackbarUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    //初始化各种控件，照着xml中的顺序写
    private DrawerLayout         mDrawerLayout;
    private CoordinatorLayout    mCoordinatorLayout;
    private AppBarLayout         mAppBarLayout;
    private Toolbar              mToolbar;
    private TabLayout            mTabLayout;
    private ViewPager            mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private NavigationView       mNavigationView;

    //TabLayout中的tab标题
    private String[]             mTitles;
    //填充到ViewPager中的Fragment
    private List<Fragment>       mFragments;
    //ViewPager的数据适配器
    private ZbieViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            //localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION | localLayoutParams.flags);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
            Log.d(TAG, "onCreate: LOLLIPOP");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            //localLayoutParams.flags = ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            Log.d(TAG, "onCreate: KITKAT");
        }

        //getWindow().getDecorView().findViewById(R.id.content).setPadding();

        //初始化各种控件
        initViews();

        //初始化mTitles、mFragments等ViewPager需要的数据
        //这里的数据都是模拟出来了，自己手动生成的，在项目中需要从网络获取数据
        initData();

        //对各种控件进行设置、适配、填充数据
        configViews();
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }

    private void initData() {
        //Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写
        mTitles = getResources().getStringArray(R.array.tab_titles);

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("flag", i);
            ZbieFragment mFragment = new ZbieFragment();
            mFragment.setArguments(mBundle);
            mFragments.add(i, mFragment);
        }
    }

    private void configViews() {
        //设置显示Toolbar
        setSupportActionBar(mToolbar);

        //设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        //mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        setDrawerLeftEdgeSize(.5f);

        //给NavigationView填充顶部区域，也可在xml中使用app:headerLayout="@layout/header_nav"来设置
        mNavigationView.inflateHeaderView(R.layout.header_nav);
        //给NavigationView填充Menu菜单，也可在xml中使用app:menu="@menu/menu_nav"来设置
        mNavigationView.inflateMenu(R.menu.menu_nav);

        //自己写的方法，设置NavigationView中menu的item被选中后要执行的操作
        onNavgationViewMenuItemSelected(mNavigationView);

        //初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new ZbieViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        //设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(5);
        //给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
        //mViewPager.setOnPageChangeListener(this);
        mViewPager.addOnPageChangeListener(this);

        mTabLayout.setTabMode(MODE_SCROLLABLE);
        //将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        //设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);

        //设置FloatingActionButton的点击事件
        mFloatingActionButton.setOnClickListener(this);
    }

    /**
     * 解决DrawerLayout不能全屏滑动的问题(反射)
     *
     * @param displayWidthPercentage 50% 半屏; 100% 全屏
     */
    private void setDrawerLeftEdgeSize(float displayWidthPercentage) {
        if (mDrawerLayout == null) {
            return;
        }
        try {
            //找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField = mDrawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(mDrawerLayout);

            //找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            //设置新的边缘大小
            Point displaySize = new Point();
            getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x * displayWidthPercentage)));
        } catch (Exception ignored) {
        }
    }

    /**
     * 设置NavigationView中menu的item被选中后要执行的操作
     *
     * @param mNav
     */
    private void onNavgationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem item) {
                String msgString = "";
                switch (item.getItemId()) {
                    case R.id.nav_menu_home:
                        msgString = (String) item.getTitle();
                        break;
                    case R.id.nav_menu_categories:
                        msgString = (String) item.getTitle();
                        break;
                    case R.id.nav_menu_feedback:
                        msgString = (String) item.getTitle();
                        break;
                    case R.id.nav_menu_setting:
                        msgString = (String) item.getTitle();
                        break;
                }
                //Menu item点击后选中，并关闭Drawerlayout
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                //android-support-design兼容包中新添加的一个类似Toast的控件。
                SnackbarUtil.show(mViewPager, msgString, 0);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mToolbar.setTitle(mTitles[position]);
    }

    @Override
    public void onPageSelected(int position) {
        //mToolbar.setTitle(mTitles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //FloatingActionButton的点击事件
            case R.id.floating_action_button:
                //SnackbarUtil.show(v, getString(R.string.plusone), 0);
                Toast.makeText(this, getString(R.string.plusone), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
