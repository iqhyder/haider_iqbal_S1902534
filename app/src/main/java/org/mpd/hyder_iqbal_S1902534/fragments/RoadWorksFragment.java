//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mpd.hyder_iqbal_S1902534.R;
import org.mpd.hyder_iqbal_S1902534.activities.DetailsActivity;
import org.mpd.hyder_iqbal_S1902534.adapter.IncidentsMainAdapter;
import org.mpd.hyder_iqbal_S1902534.api.FetchRoadWorks;
import org.mpd.hyder_iqbal_S1902534.api.RunnerClass;
import org.mpd.hyder_iqbal_S1902534.databinding.FragmentPlannedBinding;
import org.mpd.hyder_iqbal_S1902534.databinding.FragmentRoadWorksBinding;
import org.mpd.hyder_iqbal_S1902534.listener.ItemClickListener;
import org.mpd.hyder_iqbal_S1902534.modelclasses.RoadWorksModel;

import java.util.ArrayList;

public class RoadWorksFragment extends Fragment implements ItemClickListener {
    private FragmentRoadWorksBinding layoutBinding;
    private static ArrayList<RoadWorksModel> arrayList;
    private static IncidentsMainAdapter incidentsAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutBinding = FragmentRoadWorksBinding.inflate(inflater, container, false);

        clickListener();
        callingApis();
        return layoutBinding.getRoot();
    }

    private void clickListener() {
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

    private void setAdapter(ArrayList<RoadWorksModel> arrayList) {
        incidentsAdapter2 = new IncidentsMainAdapter(arrayList, getActivity());
        layoutBinding.rvRoadWorks.setAdapter(incidentsAdapter2);
        layoutBinding.rvRoadWorks.setLayoutManager(new LinearLayoutManager(getActivity()));
        incidentsAdapter2.setOnClickListener(this);
    }

    public void callingApis() {
        RunnerClass runnerClass = new RunnerClass();
        runnerClass.executeAsync(new FetchRoadWorks("https://trafficscotland.org/rss/feeds/roadworks.aspx"), (data) -> {
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
            incidentsAdapter2.filterList(filteredList);
        }
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