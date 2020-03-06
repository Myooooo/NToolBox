package com.moyang.ntoolbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private MyViewPager myViewPager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String[] newItemNames = {"物品记录","日期记录","交易记录","事件记录"};

    private HomeFragment homeFragment;
    private ListFragment listFragment;
    private ToolsFragment toolsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionManager.verifyPermissions(this);
        setContentView(R.layout.activity_main);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        myViewPager = (MyViewPager) findViewById(R.id.my_view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_left);

        homeFragment = new HomeFragment();
        listFragment = new ListFragment();
        toolsFragment = new ToolsFragment();

        initView();
    }

    private void initView(){
        // 状态栏
        setSupportActionBar(toolbar);
        toolbar.setTitle("N工具箱");//设置主标题
        //toolbar.setSubtitle();//设置子标题
        //toolbar.setLogo();//设置图标

        // 连接抽屉和监听器
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        // 页面驻留限制
        myViewPager.setOffscreenPageLimit(2);

        // 设置默认页面
        myViewPager.setCurrentItem(0);

        // 底部导航栏监听
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem nav_item) {
                switch (nav_item.getItemId()) {
                    case R.id.nav_home:
                        myViewPager.setCurrentItem(0);
                        return true;

                    case R.id.nav_list:
                        myViewPager.setCurrentItem(1);
                        return true;

                    case R.id.nav_tools:
                        myViewPager.setCurrentItem(3);
                        return true;
                }
                return false;
            }
        });

        setupViewPager(myViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        BottomNavAdapter adapter = new BottomNavAdapter(getSupportFragmentManager());
        adapter.addFragment(homeFragment);
        adapter.addFragment(listFragment);
        adapter.addFragment(toolsFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        int searchViewID = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchViewText = (TextView) searchView.findViewById(searchViewID);
        searchViewText.setHintTextColor(Color.GRAY);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("搜索...");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 提交时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 搜索
                return false;
            }
            // 变化时触发
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_add_new:
                AlertDialog.Builder addNewDialog = new AlertDialog.Builder(this);
                addNewDialog.setTitle("新建...");

                // 添加列表并设置监听
                addNewDialog.setItems(newItemNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch(which){
                            case 0:
                                intent = new Intent(MainActivity.this,AddItemNoteActivity.class);
                                startActivityForResult(intent,Constants.ADD_ITEM_NOTE_SUCCESS);
                                break;
                            case 1:
                                intent = new Intent(MainActivity.this,AddDateNoteActivity.class);
                                startActivityForResult(intent,Constants.ADD_DATE_NOTE_SUCCESS);
                                break;
                            case 2:
                                intent = new Intent(MainActivity.this,AddMoneyNoteActivity.class);
                                startActivityForResult(intent,Constants.ADD_MONEY_NOTE_SUCCESS);
                                break;
                            case 3:
                                intent = new Intent(MainActivity.this,AddEventNoteActivity.class);
                                startActivityForResult(intent,Constants.ADD_EVENT_NOTE_SUCCESS);
                                break;
                            default:
                                break;
                        }
                    }
                });

                // 设置按钮
                addNewDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                addNewDialog.show();
                break;

            case R.id.main_menu_add_test_data:

                break;

            case R.id.main_menu_settings:

                break;

            case R.id.main_menu_help:

                break;

            case R.id.main_menu_about:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Constants.ADD_NOTE_ERROR){
            Toast.makeText(MainActivity.this, "未保存更改", Toast.LENGTH_SHORT).show();
        }else if(resultCode == Constants.ADD_ITEM_NOTE_SUCCESS){
            Toast.makeText(MainActivity.this, "新建物品记录成功", Toast.LENGTH_SHORT).show();
            bottomNav.setSelectedItemId(R.id.nav_list);
            listFragment.setCurrentPage(0);
        }else if(resultCode == Constants.ADD_DATE_NOTE_SUCCESS){
            Toast.makeText(MainActivity.this, "新建日期记录成功", Toast.LENGTH_SHORT).show();
            bottomNav.setSelectedItemId(R.id.nav_list);
            listFragment.setCurrentPage(1);
        }else if(resultCode == Constants.ADD_MONEY_NOTE_SUCCESS){
            Toast.makeText(MainActivity.this, "新建交易记录成功", Toast.LENGTH_SHORT).show();
            bottomNav.setSelectedItemId(R.id.nav_list);
            listFragment.setCurrentPage(2);
        }else if(resultCode == Constants.ADD_EVENT_NOTE_SUCCESS){
            Toast.makeText(MainActivity.this, "新建事件记录成功", Toast.LENGTH_SHORT).show();
            bottomNav.setSelectedItemId(R.id.nav_list);
            listFragment.setCurrentPage(3);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            AlertDialog.Builder isQuit = new AlertDialog.Builder(this);
            isQuit.setTitle("N工具箱");
            //isQuit.setIcon(R.mipmap.cmdi_logo);
            isQuit.setMessage("确定要退出吗？");
            isQuit.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            isQuit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            isQuit.show();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
