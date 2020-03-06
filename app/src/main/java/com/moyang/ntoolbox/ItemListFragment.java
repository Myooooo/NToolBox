package com.moyang.ntoolbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragment extends Fragment {

    private TextView textView;
    private RecyclerView recyclerView;
    private MyRvAdapter recycleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list_fragment, container, false);

        textView = (TextView) view.findViewById(R.id.tv_empty);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_note_list);
        recycleAdapter = new MyRvAdapter(getContext(),Constants.ITEM_NOTE);

        initView();
        return view;
    }

    private void initView(){

        if(DataUtility.ItemNoteList.size() == 0){
            textView.setText("无物品记录");
        }else{
            textView.setText("");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(DataUtility.ItemNoteList.size() == 0){
            textView.setText("无物品记录");
        }else{
            textView.setText("");
        }

        recycleAdapter.notifyDataSetChanged();
    }

}
