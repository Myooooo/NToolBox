package com.moyang.ntoolbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DateListFragment extends Fragment {

    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list_fragment, container, false);

        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);

        initView();
        return view;
    }

    private void initView(){

        if(DataUtility.DateNoteList.size() == 0){
            tvEmpty.setText("无日期记录");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}