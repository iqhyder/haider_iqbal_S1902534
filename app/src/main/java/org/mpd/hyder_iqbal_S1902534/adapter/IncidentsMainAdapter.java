//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mpd.hyder_iqbal_S1902534.R;
import org.mpd.hyder_iqbal_S1902534.listener.ItemClickListener;
import org.mpd.hyder_iqbal_S1902534.modelclasses.RoadWorksModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class IncidentsMainAdapter extends RecyclerView.Adapter<IncidentsMainAdapter.ViewHolder> {

    private ArrayList<RoadWorksModel> mArrayList = new ArrayList<>();
    private final Context context;
    private ItemClickListener itemClickListener;

    public IncidentsMainAdapter(ArrayList<RoadWorksModel> arrayList, Context mContext) {
        this.mArrayList = arrayList;
        context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.roadworks_recycler_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,  int position) {

        viewHolder.mTitle.setText(mArrayList.get(position).getTitle());

        String startDate = null, endDate = null;
        if (mArrayList.get(0).getDescription().contains("Start Date:")) {
            String[] startDateSplit, endDateSplit;
            String[] desDate;
            desDate = mArrayList.get(position).getDescription().split("<br />");
            startDateSplit = desDate[0].split("Start Date:");
            startDateSplit = startDateSplit[1].split("-");

            //endDateSplit = desDate[1].split("<br />");
            endDateSplit = desDate[1].split("End Date:");
            endDateSplit = endDateSplit[1].split("-");

            startDate = startDateSplit[0];
            endDate = endDateSplit[0];

            viewHolder.mDateEnd.setVisibility(View.VISIBLE);
            viewHolder.ivDateEnd.setVisibility(View.VISIBLE);
            viewHolder.mDate.setText("Start: " + startDate);
            viewHolder.mDateEnd.setText("End: " + endDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" EEEE, dd MMM yyyy ");
            try {
                Date date1 = simpleDateFormat.parse(startDate);
                Date date2 = simpleDateFormat.parse(endDate);

                long diff = dateDifference(date1, date2);
                if (diff == 0) {
                    viewHolder.mTitle.setBackgroundDrawable(context.getDrawable(R.drawable.header_green_bg));
                    viewHolder.mTitle.setTextColor(context.getColor(R.color.secondary_color));
                } else if (diff == 1) {
                    viewHolder.mTitle.setBackgroundDrawable(context.getDrawable(R.drawable.header_orange_bg));
                    viewHolder.mTitle.setTextColor(context.getColor(R.color.primary_color));
                } else {
                    viewHolder.mTitle.setBackgroundDrawable(context.getDrawable(R.drawable.header_red_bg));
                    viewHolder.mTitle.setTextColor(context.getColor(R.color.secondary_color));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            viewHolder.mTitle.setBackgroundDrawable(context.getDrawable(R.drawable.header_bg));
            viewHolder.mTitle.setTextColor(context.getColor(R.color.secondary_color));
            viewHolder.mDate.setText(mArrayList.get(position).getPubDate());
            viewHolder.mDateEnd.setVisibility(View.GONE);
            viewHolder.ivDateEnd.setVisibility(View.GONE);
            //viewHolder.clMain.setBackgroundDrawable(context.getDrawable(R.drawable.bg_road_work_item_current));
        }

        viewHolder.clMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public void setOnClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle, mDate, mDateEnd;
        private final ImageView ivDateEnd;
        private final ConstraintLayout clMain;

        public ViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.tv_title);
            mDate = view.findViewById(R.id.tv_Date);
            mDateEnd = view.findViewById(R.id.tv_Date_end);
            ivDateEnd = view.findViewById(R.id.iv_calendar_end);
            clMain = view.findViewById(R.id.cl_main);
        }
    }

    public long dateDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        return elapsedDays;
    }

    public void filterList(ArrayList<RoadWorksModel> filteredList) {
        this.mArrayList = filteredList;
        notifyDataSetChanged();
    }

}