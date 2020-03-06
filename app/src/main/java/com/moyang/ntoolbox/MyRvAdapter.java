package com.moyang.ntoolbox;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private int mode;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_title, tv_description, tv_time_past;
        public ImageView iv_star;
        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.item_title);
            tv_description = (TextView) view.findViewById(R.id.item_description);
            tv_time_past = (TextView) view.findViewById(R.id.item_time_past);
            iv_star = (ImageView) view.findViewById(R.id.item_star);
        }
    }

    public MyRvAdapter(Context context, int mode){
        this.context = context;
        this.mode = mode;
        inflater = LayoutInflater.from(this.context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_list_item,parent, false);
        MyRvAdapter.MyViewHolder holder = new MyRvAdapter.MyViewHolder(view);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        switch(mode){
            case(Constants.ITEM_NOTE):
                holder.tv_title.setText(DataUtility.ItemNoteList.get(position).getTitle());
                holder.tv_description.setText(DataUtility.ItemNoteList.get(position).getLocation());
                holder.tv_time_past.setText(TimeUtility.getTimePast(DataUtility.ItemNoteList.get(position).getAddDate()));
                if(DataUtility.ItemNoteList.get(position).getIsStarred()){
                    holder.iv_star.setImageResource(R.drawable.round_star_24);
                    Drawable drawable = holder.iv_star.getDrawable();
                    DrawableCompat.setTint(drawable, Color.parseColor("#FBC02D"));
                    holder.iv_star.setImageDrawable(drawable);
                }
                holder.iv_star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(DataUtility.ItemNoteList.get(position).getIsStarred()){
                            DataUtility.ItemNoteList.get(position).setIsStarred(false);
                            holder.iv_star.setImageResource(R.drawable.round_star_border_24);
                        }else{
                            DataUtility.ItemNoteList.get(position).setIsStarred(true);
                            holder.iv_star.setImageResource(R.drawable.round_star_24);
                            Drawable drawable = holder.iv_star.getDrawable();
                            DrawableCompat.setTint(drawable, Color.parseColor("#FBC02D"));
                            holder.iv_star.setImageDrawable(drawable);
                        }
                    }
                });
                break;
            case(Constants.DATE_NOTE):
                break;
            case(Constants.MONEY_NOTE):
                break;
            case(Constants.EVENT_NOTE):
                break;
            default:
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return DataUtility.ItemNoteList.size();
    }
}
