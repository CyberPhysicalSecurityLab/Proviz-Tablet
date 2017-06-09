package com.csl.proviz;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csl.proviz.model.webservice.Board;
import com.csl.proviz.model.webservice.Cluster;
import com.csl.proviz.model.webservice.Sensor;
import com.csl.proviz.model.webservice.SensorData;

import java.util.ArrayList;


/**
 * Created by Chevy on 2/21/17.
 * RecycleView Adapter Class
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>  {

    private Cluster cluster;
    // Setup Viewholder class to manage textviews
    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView name, sensor, data, value, message;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
           sensor = (TextView) itemView.findViewById(R.id.sensor);
            data = (TextView) itemView.findViewById(R.id.data);
            value = (TextView) itemView.findViewById(R.id.value);
            message = (TextView) itemView.findViewById(R.id.message);

            itemView.setClickable(true);

        }
    }

    public DataAdapter(Cluster cluster)
    {
        this.cluster = cluster;


    }


    // sort dataTable in descending order
    public void sortDecending(){
      //  Collections.sort(gitContent);
        // todo sort things..
        notifyDataSetChanged();
    }


    /**
     * @param parent
     * @param viewType
     * @return viewholder once inflated to screen
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View contactView = inflater.inflate(R.layout.item_content, parent, false);
        Log.i("DataAdapter","view inflated");
        // Return a new holder instance
        final ViewHolder viewHolder = new ViewHolder(contactView);
//        contactView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Save the selected positions to the SparseBooleanArray
//                if(selectedItems.get(viewHolder.getAdapterPosition(), false)){
//                    selectedItems.delete(selectedPosition);
//
//                }
//
//
//            }
//        });
        return viewHolder;
    }


    /*
       viewHolder.name.setText(content.getBoardName());
                viewHolder.sensor.setText(content.getSensorName());
                viewHolder.data.setText(content.getData());
                if (content.getData().equals("Temperature")){
                    viewHolder.value.setText(content.getValue()+"\u00b0");
                } else if (content.getData().equals("Humidity")){
                    viewHolder.value.setText(content.getValue()+"%");
                }else {
                    viewHolder.value.setText(content.getValue()+"m");
                }
                viewHolder.message.setText(content.getLimit());
     */

    /**
     * @param viewHolder
     * @param position index of item in listContent
     * Responsible for setting and binding the recieved Server data to their
     * corresponding text views
     * Updates on 'adapter.notifyDataSetChanged'
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Log.i("DataAdapter","binding data to viewHolder");

        //viewHolder.itemView.setSelected(selectedPosition == position);


        // viewHolder.highlight.setSelected(selectedItems.get(position, false));

     //   setPosition(position);
        SensorData sensorData = getSensorDataFromSensorList(position);
        Sensor sensor = sensorData.getParentSensor();
        Board board = sensor.getParentBoard();

        viewHolder.name.setText(board.getBoardName());
        viewHolder.sensor.setText(sensor.getSensorName());
        viewHolder.data.setText(sensorData.getVariableName());
        viewHolder.value.setText(sensorData.getSensorValue() +" "+sensorData.getSensorUnit());
        viewHolder.message.setText(sensorData.getMessage().toString());
    }

    private SensorData getSensorDataFromSensorList(int position)
    {
        for(Board board: cluster.getBoards())
        {
            for(Sensor sensor: board.getSensors())
            {
                if(sensor.getSensorData().size()> position)
                {
                    return sensor.getSensorData().get(position);
                }
                else
                {
                    position -= sensor.getSensorData().size();
                }
            }


        }
        return null;
    }



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {

        return countElements();
    }
    private int countElements()
    {
        ArrayList<Board> boards = cluster.getBoards();
        int counter = 0;

        for(Board board: boards)
        {
            for(Sensor sensor: board.getSensors())
            {
                counter += sensor.getSensorData().size();
            }
        }

        return counter;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }


}
