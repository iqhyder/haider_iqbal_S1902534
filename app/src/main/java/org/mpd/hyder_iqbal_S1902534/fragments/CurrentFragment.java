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

import org.mpd.hyder_iqbal_S1902534.activities.DetailsActivity;
import org.mpd.hyder_iqbal_S1902534.adapter.IncidentsMainAdapter;
import org.mpd.hyder_iqbal_S1902534.api.FetchRoadWorks;
import org.mpd.hyder_iqbal_S1902534.api.RunnerClass;
import org.mpd.hyder_iqbal_S1902534.databinding.FragmentCurrentBinding;
import org.mpd.hyder_iqbal_S1902534.listener.ItemClickListener;
import org.mpd.hyder_iqbal_S1902534.modelclasses.RoadWorksModel;

import java.util.ArrayList;

public class CurrentFragment extends Fragment implements ItemClickListener {
    private FragmentCurrentBinding layoutBinding;
    private ArrayList<RoadWorksModel> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutBinding = FragmentCurrentBinding.inflate(inflater, container, false);

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
        IncidentsMainAdapter incidentsAdapter = new IncidentsMainAdapter(arrayList, getActivity());
        layoutBinding.rvIncidents.setAdapter(incidentsAdapter);
        layoutBinding.rvIncidents.setLayoutManager(new LinearLayoutManager(getActivity()));
        incidentsAdapter.setOnClickListener(this);
    }

    private void callingApis() {
        RunnerClass runnerClass1 = new RunnerClass();
        runnerClass1.executeAsync(new FetchRoadWorks("https://trafficscotland.org/rss/feeds/currentincidents.aspx"), (data) -> {
            layoutBinding.pbCurrent.setVisibility(View.GONE);
            if (data != null) {
                arrayList = data;
                setAdapter(arrayList);
            }
        });
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