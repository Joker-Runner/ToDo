package com.joker.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.joker.data.Constant;
import com.joker.data.DatabaseManager;
import com.joker.setting.SettingActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int labelId;
    private int sortBy;
    DatabaseManager databaseManager;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewItemActivity(MainActivity.this);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化设置
     */
    public void init() {
        sharedPreferences = getSharedPreferences(Constant.SHARED_SETTING, Context.MODE_APPEND);
        editor = sharedPreferences.edit();
        //第一次启动时设置默认新建的是想属于哪个集合
        if (sharedPreferences.getInt(Constant.DEFAULT_LABEL_ID_SETTING, -1) < 0) {
            editor.putInt(Constant.DEFAULT_LABEL_ID_SETTING, Constant.DEFAULT_LABEL_ID).commit();
        }

        databaseManager = new DatabaseManager(this);
        labelId = sharedPreferences.getInt(Constant.DEFAULT_LABEL_ID_SETTING, Constant.DEFAULT_LABEL_ID);
        sortBy = Constant.SORT_BY_DEFAULT;
        startListFragment(labelId, sortBy);//默认打开ListFragment
    }

    /**
     * 新建待办项完成后刷新列表
     */
    @Override
    protected void onResume() {
        super.onResume();
        startListFragment(labelId, sortBy);
    }

    /**
     * 刷新列表
     *
     * @param labelId 显示的列表集ID
     * @param sortBy  排序方式
     */
    public void startListFragment(int labelId, int sortBy) {
        ListFragment listFragment = new ListFragment();
        listFragment.setListFragment(databaseManager, labelId, sortBy);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, listFragment).commit();
    }

    /**
     * 新建一个事件，打开新的Activity
     *
     * @param context 上下文
     */
    public void startNewItemActivity(Context context) {
        Intent intent = new Intent(context, NewItemActivity.class);
        intent.putExtra(Constant.NEW_OR_EDIT, Constant.NEW);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 列表排序规则
     *
     * @param item 被点击的菜单项
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.sort_by_default:
                sortBy = Constant.SORT_BY_DEFAULT;
                break;
            case R.id.sort_by_date:
                sortBy = Constant.SORT_BY_DATE;
                break;
            case R.id.sort_by_significance:
                Toast.makeText(this, "待实现...", Toast.LENGTH_LONG).show();
                break;
        }
        startListFragment(labelId, sortBy);
        return super.onOptionsItemSelected(item);
    }


    /**
     * 抽屉菜单的响应事件
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.label:
                labelId = Constant.DEFAULT_LABEL_ID;
                startListFragment(labelId, sortBy);
                break;
            case R.id.label1:
                labelId = Constant.LABEL_ID_1;
                startListFragment(labelId, sortBy);
                break;
            case R.id.label2:
                labelId = Constant.LABEL_ID_2;
                startListFragment(labelId, sortBy);
                break;
            case R.id.label_have_done:
                labelId = Constant.LABEL_ID_HAVE_DONE;
                startListFragment(labelId, sortBy);
                break;
            case R.id.add_todo_set_group:
                Toast.makeText(this, "待实现...", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "待实现...", Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                startListFragment(Constant.DEFAULT_LABEL_ID, sortBy);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 最后一个Activity销毁前，关闭databaseManager
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseManager.closeDatabase();
    }
}
