//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.mpd.hyder_iqbal_S1902534.MainActivity;
import org.mpd.hyder_iqbal_S1902534.R;
import org.mpd.hyder_iqbal_S1902534.activities.DetailsActivity;
import org.mpd.hyder_iqbal_S1902534.adapter.IncidentsMainAdapter;
import org.mpd.hyder_iqbal_S1902534.api.FetchRoadWorks;
import org.mpd.hyder_iqbal_S1902534.api.RunnerClass;
import org.mpd.hyder_iqbal_S1902534.databinding.FragmentPlannedBinding;
import org.mpd.hyder_iqbal_S1902534.listener.ItemClickListener;
import org.mpd.hyder_iqbal_S1902534.modelclasses.RoadWorksModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlannedFragment extends Fragment implements ItemClickListener {
    private FragmentPlannedBinding layoutBinding;
    private static ArrayList<RoadWorksModel> arrayList = new ArrayList<>();
    private static IncidentsMainAdapter incidentsAdapter1;
    private String[] desDate;
    private String[] startDateSplit, endDateSplit;
    private String startDate, endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d("cdsgasgheck", "Restoring saved state");
            // Restore id & name
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutBinding = FragmentPlannedBinding.inflate(inflater, container, false);

        clickListener();
        callingApis();
        return layoutBinding.getRoot();
    }

    private void clickListener() {
        layoutBinding.tvPlanJourney.setOnClickListener(v -> {
            try {
                showDateDialog(requireActivity());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        layoutBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callingApis();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void setAdapter(ArrayList<RoadWorksModel> arrayList) {
        incidentsAdapter1 = new IncidentsMainAdapter(arrayList, getActivity());
        layoutBinding.rvPlanned.setAdapter(incidentsAdapter1);
        layoutBinding.rvPlanned.setLayoutManager(new LinearLayoutManager(getActivity()));
        incidentsAdapter1.setOnClickListener(this);
    }

    private void callingApis() {
        RunnerClass runnerClass2 = new RunnerClass();
        runnerClass2.executeAsync(new FetchRoadWorks("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx"), (data) -> {
            layoutBinding.pbCurrent.setVisibility(View.GONE);
            if (data != null) {
                arrayList = data;
                setAdapter(arrayList);
            }
        });
    }

    public static void filter(String text) {
        ArrayList<RoadWorksModel> filteredList = new ArrayList<>();

        for (RoadWorksModel item : arrayList) {
            if (item.getTitle() != null) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        if (arrayList != null) {
            incidentsAdapter1.filterList(filteredList);
        }
    }

    public void showDateDialog(Activity activity) throws ParseException {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.date_dialog);

        TextView tvSubmit = dialog.findViewById(R.id.tv_submit);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        ImageView ivCancel = dialog.findViewById(R.id.iv_cancel);
        DatePicker datePicker = dialog.findViewById(R.id.date_picker);


        tvSubmit.setOnClickListener(view -> {

            int day = datePicker.getDayOfMonth();
            int month = (datePicker.getMonth() + 1);
            int year = datePicker.getYear();

            //tvDate.setText(year + "-" + month + "-" + day);
            Toast.makeText(activity, "" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();

            String dateselect = +year + "-" + month + "-" + day;
            String inputFormat = "yyyy-MM-dd";
            String OutPutFormat = "EEEE, dd MMM yyyy";
            String formats1 = null;
            try {
                SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd");

                long startDateSelectLong, endDateSelectLong;
                Date dateStart = formatter5.parse(dateselect);
                Date dateEnd = formatter5.parse("2022-3-28");
                startDateSelectLong = dateStart.getTime();
                endDateSelectLong = dateEnd.getTime();

                getListBetweenTwoDates(startDateSelectLong, endDateSelectLong);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            dialog.dismiss();
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getListBetweenTwoDates(long DatePassed, long endDatePassed) {
        ArrayList<RoadWorksModel> filteredList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            desDate = arrayList.get(i).getDescription().split("<br />");
            startDateSplit = desDate[0].split("Start Date:");
            startDateSplit = startDateSplit[1].split("-");

            endDateSplit = desDate[1].split("End Date:");
            endDateSplit = endDateSplit[1].split("-");

            startDate = startDateSplit[0];
            endDate = endDateSplit[0];

            try {
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //Date dateStart = sdf.parse(startDate);
                //Date dateEnd = sdf.parse(endDate);

                SimpleDateFormat inputFormat = new SimpleDateFormat(" EEEE, d MMM yyyy ");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date dateStart = inputFormat.parse(startDate);
                startDate = outputFormat.format(dateStart);

                Date dateEnd = inputFormat.parse(endDate);
                endDate = outputFormat.format(dateEnd);

                long startDateLong = dateStart.getTime();
                long endDateLong = dateEnd.getTime();

                if (DatePassed >= startDateLong && DatePassed <= endDateLong) {
                    filteredList.add(arrayList.get(i));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        incidentsAdapter1.filterList(filteredList);
    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(requireActivity(), DetailsActivity.class);
        intent.putExtra("title", arrayList.get(pos).getTitle());
        intent.putExtra("description", arrayList.get(pos).getDescription());
        intent.putExtra("link", arrayList.get(pos).getLink());
        intent.putExtra("pubDate", arrayList.get(pos).getPubDate());
        intent.putExtra("mLatLong", arrayList.get(pos).getLocPoints());
        startActivity(intent);
    }
}