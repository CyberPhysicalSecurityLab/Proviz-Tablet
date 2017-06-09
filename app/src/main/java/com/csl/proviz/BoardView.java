package com.csl.proviz;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csl.proviz.model.webservice.Board;


/**
 * Created by Ivann on 3/24/2017.
 */

public class BoardView implements View.OnClickListener, View.OnLongClickListener, View.OnDragListener {

    Board board;
    Activity activity;
    ImageView boardImg;
    TextView boardName;
    LinearLayout linearLayout;
    SensorPopup popup;

    AbsoluteLayout.LayoutParams alp;
    FragmentManager fragMan;

    int x;
    int y;

    ImageView line;
    Point displaySize;

    BoardView(Board board, Activity ac, ImageView iv, TextView tv, LinearLayout ll, Point p, int a, int b, int currentLoopIteration) {
        this.board = board;
        this.activity = ac;
        this.boardImg = iv;
        this.boardName = tv;
        this.linearLayout = ll;

        this.x = a;
        this.y = b;

        line = new ImageView(activity);
        displaySize = p;

        boardName.setText(board.getBoardName());
       // board.setBoardView(this);

        if(Build.VERSION.SDK_INT > 20) {
            switch (board.getBoardType()) {
                case ARDUINO:
                    boardImg.setImageDrawable(activity.getDrawable(R.drawable.arduino_img));
                    break;
                case RASPBERRY_PI:
                    boardImg.setImageDrawable(activity.getDrawable(R.drawable.raspberrypi_img));
                    break;
                case BEAGLEBONE:
                    boardImg.setImageDrawable(activity.getDrawable(R.drawable.beaglebone_img));
                    break;
                default:
                    break;
            }
        }
        if(currentLoopIteration == 0) {
            (activity.findViewById(R.id.absLayout)).setOnDragListener(this);
        }
    }

    void buildLayout(int imgW, int imgH, int densityDPI){

        int margin = 30;
        switch(densityDPI)
        {
            case DisplayMetrics.DENSITY_MEDIUM:
                margin = 10;
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
                margin = 30;
                break;
        }

        //Image layout parameters
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(imgW,imgH);
        imgParams.setMargins(margin, margin, margin, 0);
        boardImg.setLayoutParams(imgParams);
        //Text layout parameters
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtParams.setMargins(margin,0,margin,margin);
        boardName.setLayoutParams(txtParams);

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //LinearLayout container parameters
        alp = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,this.x,this.y);
        linearLayout.setLayoutParams(alp);

        linearLayout.addView(boardImg);
        linearLayout.addView(boardName);
        linearLayout.setBackgroundColor(0xFFD8D8D8);

        ((AbsoluteLayout) activity.findViewById(R.id.absLayout)).addView(linearLayout);

        boardImg.setOnClickListener(this);
        boardImg.setOnLongClickListener(this);

        (activity.findViewById(R.id.absLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(BoardView b : Topology.allBoardViews) {
                    b.changeBorder(false);
                }
            }
        });
    }

    @Override
    public void onClick(View view){
        for(BoardView b : Topology.allBoardViews) {
            b.changeBorder(false);
        }
        changeBorder(true);

        showDetailsDialog(this.getBoard());

    }

    public void ThresholdExceed(boolean isExceed)
    { GradientDrawable border = new GradientDrawable();
        if(isExceed) {
            border.setStroke(1, Color.BLUE);
        }
        else
        {
            border.setStroke(0, Color.BLUE);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackgroundDrawable(border);
        } else {
            linearLayout.setBackground(border);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ClipData clipdata = ClipData.newPlainText("","");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if(Build.VERSION.SDK_INT > 23) {
            view.startDragAndDrop(clipdata, shadowBuilder, view, 0);
        } else {
            view.startDrag(clipdata,shadowBuilder,this,0);
        }
        view.setVisibility(View.INVISIBLE);

        ImageView iv = (ImageView) view;
        ViewParent parent = iv.getParent();
        LinearLayout l = (LinearLayout)parent;
        l.getChildAt(1).setVisibility(View.INVISIBLE);

        return true;
    }
    @Override
    public boolean onDrag(View view, DragEvent event) {
        String TAG = "DragDrop";
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP: {
                for(BoardView b : Topology.allBoardViews){
                    b.changeBorder(false);
                }
                if(Build.VERSION.SDK_INT > 23) {
                    View v = (View) event.getLocalState();
                    ImageView iv = (ImageView) v;

                    ViewParent parent = iv.getParent();
                    LinearLayout l = (LinearLayout) parent;
                    AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) l.getLayoutParams();
                    this.x = (int) event.getX() - (l.getWidth() / 2);
                    this.y = (int) event.getY() - (l.getHeight() / 2) + 30;
                    params.x = x;
                    params.y = y;
                    l.setLayoutParams(params);

                    l.getChildAt(1).setVisibility(View.VISIBLE);
                    iv.setVisibility(iv.VISIBLE);

                    GradientDrawable border = new GradientDrawable();
                    border.setStroke(1, 0xFFFD0000); //black border with full opacity
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        l.setBackgroundDrawable(border);
                    } else {
                        l.setBackground(border);
                    }
                } else {
                    BoardView b = (BoardView) (event.getLocalState());
                    LinearLayout l = (LinearLayout) b.getParentLayout();
                    AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) l.getLayoutParams();
                    this.x = (int) event.getX() - (l.getWidth() / 2);
                    this.y = (int) event.getY() - (l.getHeight() / 2) + 30;
                    params.x = x;
                    params.y = y;
                    l.setLayoutParams(params);

                    l.getChildAt(1).setVisibility(View.VISIBLE);
                    l.getChildAt(0).setVisibility(View.VISIBLE);

                    b.changeBorder(true);
                }

                break;
            }
        }
        return true;
    }
    public void setX(int a) {
        this.x=a;
    }
    public int getX() {
        return this.x;
    }
    public void setY(int b) {
        this.y=b;
    }
    public int getY(){
        return this.y;
    }
    public LinearLayout getParentLayout(){
        return this.linearLayout;
    }

    public void changeBorder(boolean bor){
        if(bor) {
            GradientDrawable border = new GradientDrawable();
            border.setStroke(1, Color.RED); //black border with full opacity
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                linearLayout.setBackgroundDrawable(border);
            } else {
                linearLayout.setBackground(border);
            }
        } else {
            GradientDrawable border = new GradientDrawable();
            border.setStroke(0, Color.RED); //black border with full opacity
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                linearLayout.setBackgroundDrawable(border);
            } else {
                linearLayout.setBackground(border);
            }
        }
    }
    //todo Border isi
    private void showDetailsDialog(Board board) {
        FragmentManager fm = Topology.fragmentManager;

         popup = SensorPopup.newInstance(this, board);




        popup.show(fm, "fragment_edit_name");
    }






    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }



//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                Log.d("TST","Burak");
//                if (popup != null)
//                    popup.updateFields();
//                return null;
//            }
//        };
//
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
//            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        else
//            asyncTask.execute();
//
//    }

}