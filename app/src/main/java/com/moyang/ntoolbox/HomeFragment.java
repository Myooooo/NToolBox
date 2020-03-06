package com.moyang.ntoolbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private WebView webViewFmyLoveLsq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        //webViewFmyLoveLsq = (WebView) view.findViewById(R.id.wv_fmy_love_lsq);

        initView();
        return view;
    }

    private void initView(){

        /*webViewFmyLoveLsq.loadUrl("http://www.fmylovelsq.top");
        webViewFmyLoveLsq.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                return true;
            }
        });
        // Webview 配置
        WebSettings webSettings = webViewFmyLoveLsq.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);*/

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
