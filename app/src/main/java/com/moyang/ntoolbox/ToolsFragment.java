package com.moyang.ntoolbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ToolsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tools_fragment, container, false);

        initView();
        return view;
    }

    private void initView(){



    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
