package com.moyang.ntoolbox;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private ActionBar actionBar;
    /*private ImageView ivLogo;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvVersion;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        /*ivLogo = (ImageView) findViewById(R.id.iv_about_logo);
        tvTitle = (TextView) findViewById(R.id.tv_about_title);
        tvDescription = (TextView) findViewById(R.id.tv_about_description);
        tvVersion = (TextView) findViewById(R.id.tv_about_version);*/

        initView();
    }

    private void initView(){

        // 显示返回按钮
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /*ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("2020情人节快乐！");
                tvTitle.setTextColor(Color.RED);
                tvDescription.setText("永远爱你");
                tvVersion.setText("——臭崽");
            }
        });*/

    }

    // 配置返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
