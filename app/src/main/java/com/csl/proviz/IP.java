package com.csl.proviz;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Ivann on 4/7/2017.
 */

public class IP extends Fragment {
    String IP;
    Tracker tracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Analytics  analytics = (Analytics)getActivity().getApplication();
         tracker = analytics.getDefaultTracker();
        tracker.setScreenName("IP View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("IP View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLocalIpAddress();



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ip, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    public String getLocalIpAddress(){


              try{
                  WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);

                  IP = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

              } catch (Exception e) {
                  e.printStackTrace();
                  Log.e("IP Address", e.toString());
              }

        if(IP != null){
            ((TextView) getActivity().findViewById(R.id.ip_text_field)).setText(IP);
        } else {
            ((TextView) getActivity().findViewById(R.id.ip_text_field)).setText("N/A");
        }

        return null;
    }
}
