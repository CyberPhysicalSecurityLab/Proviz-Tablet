package com.csl.proviz;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csl.proviz.model.webservice.Board;
import com.csl.proviz.model.webservice.Cluster;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

/**
 * Created by Ivann on 4/7/2017.
 */

public class Topology extends Fragment  {

    RelativeLayout mainLayout;
    public static Point displaySize;
    public static ArrayList<BoardView> allBoardViews;
    public static FragmentManager fragmentManager;
    public  int totalSensorAmount;
    int imageW, imageH, density;
    private Tracker tracker;


    private void fillTopology()
    {
        Cluster cluster = DataManager.getInstance().getCluster();
        getImageDimensions();
        for(int i =0; i<cluster.getBoards().size();i++)
        {
            Board board = cluster.getBoards().get(i);
            totalSensorAmount += board.getSensors().size();
            Point current = getCoord(displaySize,i,cluster.getBoards().size());

            ImageView newImage = new ImageView(getActivity());
            TextView newText = new TextView(getActivity());

            BoardView newBoardView = new BoardView(board, getActivity(), newImage, newText, new LinearLayout(getActivity()), displaySize,current.x,current.y,i);
            newBoardView.buildLayout(imageW,imageH, density);
            allBoardViews.add(newBoardView);

        }
        ((TextView) getActivity().findViewById(R.id.totalSensorAmount)).setText("" + totalSensorAmount);

    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("Topology View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        allBoardViews = new ArrayList<>();

        totalSensorAmount = 0;

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);

        Log.e("SCREEN_DIMENSIONS", "w: "+ displaySize.x + ", h: " + displaySize.y);

        fragmentManager = getFragmentManager();
if(DataManager.getInstance().getCluster() != null)
        fillTopology();

        //Setup some variables for the sidebar
        ((TextView) getActivity().findViewById(R.id.totalBoardAmount)).setText("" + allBoardViews.size());

        tracker.setScreenName("Topology View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    void getImageDimensions(){
        imageW = (int) (displaySize.x / 11.4);
        imageH = (int) (64 * imageW) / 91;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Analytics analytics = (Analytics) getActivity().getApplication();
        tracker = analytics.getDefaultTracker();

    }

    public Point getCoord(Point size, int current, int total){
        int rad = displaySize.y / 3;
        int cur = current + 1;
        int unit = (int) 360/total;

        //Multiply size.x and size.y by a coefficient that scales the position
        int xs = (int) ((size.x * 0.98 / 2) - (imageW / 2));
        int ys =(int)  (((size.y * 0.86) / 2) - (imageH / 2));

        density = getResources().getDisplayMetrics().densityDpi; //Get Screen density
        switch(density)
        {
            case DisplayMetrics.DENSITY_MEDIUM:
                xs *= 1;
                ys *= 0.88;
                break;
            case DisplayMetrics.DENSITY_HIGH:

                break;
            case DisplayMetrics.DENSITY_XHIGH:

                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                break;
            default:
                break;
        }

        int y = (int) Math.round(rad * (Math.cos(Math.toRadians(unit * cur))));
        int x = (int) Math.round(rad * (Math.sin(Math.toRadians(unit * cur))));

        return new Point(xs + x, ys + y);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.topology, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
    }


}
