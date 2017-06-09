package com.csl.proviz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csl.proviz.model.webservice.Board;
import com.csl.proviz.model.webservice.Sensor;
import com.csl.proviz.model.webservice.SensorData;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by Ivann on 4/7/2017.
 */

public class SensorPopup extends DialogFragment implements IncomingDataListener{

    private static String dialogTitle;
    private LinearLayout sensorsBody;
    private LinearLayout.LayoutParams sensorsBodyParams;
    private HashMap<String,TextView> sensorVariableViews;
    private static int sensorAmount;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    private BoardView boardView;

    private Tracker tracker;

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        board.unscribeBoard(this);

    }

    public SensorPopup() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }
    public static SensorPopup newInstance(BoardView boardView, Board board) {
        SensorPopup frag = new SensorPopup();
        frag.setBoard(board);
        frag.setBoardView(boardView);
        dialogTitle=board.getBoardName();
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.popup_content, container);
        sensorVariableViews = new HashMap<>();
        //Set up the title of the dialog box

        TextView tv = new TextView(getActivity());
        tv.setText(dialogTitle);
        tv.setTextColor(Color.parseColor("#222222"));
        tv.setTextSize(19.5f);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTypeface(null, Typeface.BOLD);
        titleParams.setMargins(0,50,0,25);
        tv.setLayoutParams(titleParams);

        ((LinearLayout) root.findViewById(R.id.mainSensorWrapper)).addView(tv);

        //Set Up wrapper Linear Layout
        sensorsBody = new LinearLayout(getActivity());
        sensorsBody.setOrientation(LinearLayout.VERTICAL);
        sensorsBodyParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sensorsBodyParams.setMargins(0,0,0,50);
        sensorsBody.setLayoutParams(sensorsBodyParams);
        //Add Sensors text to linear sensors body
        TextView sensorsText = new TextView(getActivity());
        sensorsText.setText("Sensors");
        sensorsText.setTextColor(Color.parseColor("#666666"));
        sensorsText.setTextSize(16f);
        sensorsText.setTypeface(null,Typeface.BOLD);
        LinearLayout.LayoutParams sensorsTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sensorsTextParams.setMargins(50,10,0,10);
        sensorsText.setLayoutParams(sensorsTextParams);
        //Once text is created, add it to the LinearLayout
        sensorsBody.addView(sensorsText);

        generateDynamicLayout();

        ((LinearLayout) root.findViewById(R.id.mainSensorWrapper)).addView(sensorsBody);

        Analytics analytics = (Analytics) getActivity().getApplication();
         tracker = analytics.getDefaultTracker();
        tracker.setScreenName("Data Table View");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        return root;
    }
    public void generateDynamicLayout(){
        LinearLayout sensorWrapper = new LinearLayout(getActivity());
        sensorWrapper.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams sensorWrapperParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sensorWrapperParams.setMargins(100, 0,0,0);
        board.addSubscribe(this);
        sensorWrapper.setLayoutParams(sensorWrapperParams);
        try{
            for(Sensor sensor:board.getSensors()) {
                String sensorName = sensor.getSensorName();
                sensorWrapper.addView(newSensorDescription(sensorName));

                for(SensorData sensorData: sensor.getSensorData()) {
                    LinearLayout valuesWrapper = new LinearLayout(getActivity());
                    valuesWrapper.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams valueWrapperParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    valuesWrapper.setLayoutParams(valueWrapperParams);
                    valuesWrapper.addView(pairValues(sensorData.getSensorUnit(),Double.toString(sensorData.getSensorValue())));
                    sensorWrapper.addView(valuesWrapper);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        sensorsBody.addView(sensorWrapper);
    }

    public void updateFields()
    {
        for(Sensor sensor :board.getSensors())
        {
            for(SensorData sensorData: sensor.getSensorData())
            {
                sensorVariableViews.get(sensorData.getSensorUnit()).setText(Double.toString(sensorData.getSensorValue()));

            }
        }
    }

    public TextView newSensorDescription(String text){
        TextView description = new TextView(getActivity());
        LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        descriptionParams.setMargins(0,15,0,15);

        description.setText(text);
        description.setTextColor(Color.parseColor("#666666"));
        description.setTextSize(16f);
        description.setLayoutParams(descriptionParams);
        return description;
    }
    public LinearLayout pairValues(String key, String val){
        //Create LinearLayout for metricView
        LinearLayout metricViewLayout = new LinearLayout(getActivity());
        metricViewLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams metricViewLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        metricViewLayoutParams.weight = 1;
        metricViewLayout.setLayoutParams(metricViewLayoutParams);
        //Create TextView for the metric text
        TextView metricView = new TextView(getActivity());
        LinearLayout.LayoutParams metricViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        metricView.setLayoutParams(metricViewParams);
        metricView.setText(key);
        metricView.setTextColor(Color.parseColor("#666666"));
        metricView.setTextSize(16f);
        //Add TextView to LinearLayout
        metricViewLayout.addView(metricView);

        //Create LinearLayout for metricView
        LinearLayout valueViewLayout = new LinearLayout(getActivity());
        valueViewLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams valueViewLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        valueViewLayoutParams.weight = 1;
        valueViewLayout.setLayoutParams(metricViewLayoutParams);
        //Create TextView for the Value
        TextView valueView = new TextView(getActivity());
        valueView.setLayoutParams(metricViewParams);
        valueView.setText(val);
        valueView.setTextColor(Color.parseColor("#666666"));
        valueView.setTextSize(16f);
        //Add TextView for MetricView
        valueViewLayout.addView(valueView);

        //Create Horizontal LinearLayout Object to hold Key and Value Pairs
        LinearLayout keyValueLayout = new LinearLayout(getActivity());
        keyValueLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams keyValueLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        keyValueLayoutParams.setMargins(50,10,0,10);
        keyValueLayout.setLayoutParams(keyValueLayoutParams);

        //Add TextViews to LinearLayout
        keyValueLayout.addView(metricViewLayout);
        keyValueLayout.addView(valueViewLayout);
        sensorVariableViews.put(key,valueView);
        return keyValueLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        ((TextView) getActivity().findViewById(R.id.totalSensorAmount)).setText("" + sensorAmount);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    public void onResume() {
        int width = Topology.displaySize.x / 3;
        int height = Topology.displaySize.y / 2;

        getDialog().getWindow().setLayout(width, height);
        // Call super onResume after sizing
        super.onResume();
    }


    @Override
    public void onDataReceived() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateFields();
            }
        });
    }
}
