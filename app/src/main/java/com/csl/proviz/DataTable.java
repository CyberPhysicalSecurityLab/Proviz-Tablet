package com.csl.proviz;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csl.proviz.model.webservice.Board;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

//import static com.example.provizdemo.MainActivity.adapter;


/**
 * Created by Ivann on 4/7/2017.
 */
public class DataTable extends Fragment implements  IncomingDataListener{




    public MainActivity activity;

    //================================
    //  handlers maintain timing event
    //================================
   static Handler myHandler1;
   static Handler myHandler2;
   static Handler myHandler3;
   static Handler[] handler = {myHandler1, myHandler2, myHandler3};
    //======================================
    // objects that run code on run() override
    //=======================================
    private Runnable myRunnable1;
    private Runnable myRunnable2;
    private Runnable myRunnable3;
    private Runnable[] runner = {myRunnable1, myRunnable2, myRunnable3};
    public RecyclerView rvContacts; // instance of recycleview
    public  DataAdapter adapter; // adapter between listdata and recyclewview
    public ImageButton sortUp, limit;
    public TextView serverIP, name, sensor, data, value, message;
    private Boolean isSocketCLosed = true;

    private Tracker tracker;

    public String socketIP = "";
    static int postCount = 2;
    private static final String TAG = "BackgroundService";



    public DataTable(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //point the instances to their ui elements

        // set adapter .. pass object, current activity, and github list
        subscribe2Boards(DataManager.getInstance().getCluster().getBoards());
        adapter = new DataAdapter(DataManager.getInstance().getCluster());
        Analytics analytics = (Analytics) getActivity().getApplication();
        tracker = analytics.getDefaultTracker();

        tracker.setScreenName("Data Table View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        Log.i("Datatable", "onCreate");

    }

    private void subscribe2Boards(ArrayList<Board> boards)
    {
        for(Board board:boards)
        {
            board.addSubscribe(this);
        }
    }

    public void unsubscribeFromListenersList()
    {

        for(Board board:DataManager.getInstance().getCluster().getBoards())
        {
            board.unscribeBoard(this);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.datatable, container, false);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rvContacts = (RecyclerView) rootView.findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(mLayoutManager);
        ItemDecoration itemDeco = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        rvContacts.addItemDecoration(itemDeco);
        rvContacts.setItemAnimator(new LandingAnimator());
        rvContacts.setAdapter(adapter);
        Log.i("Datatable", "View Created");
        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.i("Datatable", "onAttached");
        if (context instanceof MainActivity){
            this.activity =(MainActivity) context;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // getActivity().setContentView(R.layout.datatable);
        Log.i("Datatable", "Activity Created");

        // creating the adapter with passed dataList elements
        // Asssign new layout manager for recycleview
        // set recycleview adapter to created adapter

        //sortUp = (ImageButton) getActivity().findViewById(R.id.imageButton);
        //limit = (ImageButton) getActivity().findViewById(R.id.limitsort);
        name = (TextView) getActivity().findViewById(R.id.name);
        sensor = (TextView) getActivity().findViewById(R.id.sensor);
        data = (TextView) getActivity().findViewById(R.id.data);
        value = (TextView) getActivity().findViewById(R.id.value);
        message = (TextView) getActivity().findViewById(R.id.message);
        serverIP = (TextView) getActivity().findViewById(R.id.serverIP);



       // socketIP = "/192.168.1.112";
        // server = new Server(this);

        //Toast.makeText(MainActivity.this,server.getIpAddress() + ":" + server.getPort(),Toast.LENGTH_LONG).show();
        //Toast.makeText(MainActivity.this, "Socket IP" + server.getSocketIP(), Toast.LENGTH_SHORT).show();

        for (int index = 0; index < 3; index++) {

            handler[index] = new Handler(activity.getMainLooper());
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("Data Table View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //server.onDestroy();
        //client1.onDestroy();
        unsubscribeFromListenersList();
        Log.i("Datatable", "Destroyed");
    }



    @Override
    public void onDataReceived() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
               // rvContacts.invalidate();
            }
        });



    }
}
