//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.mpd.hyder_iqbal_S1902534.R;
import org.mpd.hyder_iqbal_S1902534.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityDetailsBinding layoutBinding;
    private GoogleMap map;
    private String mTitle, mDes, mLink, mDate;
    private Double mLat, mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        SupportMapFragment mFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mFrag != null;
        mFrag.getMapAsync(this);

        setData();
    }

    private void setData() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mDes = intent.getStringExtra("description");
        mLink = intent.getStringExtra("link");
        mDate = intent.getStringExtra("pubDate");
        String mLatLong = intent.getStringExtra("mLatLong");
        mLat = Double.parseDouble(mLatLong.split(" ")[0]);
        mLong = Double.parseDouble(mLatLong.split(" ")[1]);

        layoutBinding.tvTitle.setText(mTitle);
        layoutBinding.tvDescription.setText(Html.fromHtml(mDes));
        layoutBinding.tvLink.setText(mLink);
        layoutBinding.tvDate.setText(mDate);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(mLat, mLong);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLong), 13f));
    }
}