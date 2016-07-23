package com.example.android.networkconnect;



        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.app.Activity;
        import java.util.List;

public class SensorDataListAdapter extends ArrayAdapter<List <SensorData>> {

    private Context context;
    private boolean useList = true;


    int layoutResourceId;
    List<SensorData> lSensorData = null;

    public SensorDataListAdapter(Context context,int layoutResourceId, List items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.lSensorData = items;
    }

    /**
     * Holder for the list items.
     */
    private class SensorDataViewHolder{
        ImageView imgIcon;
        TextView nameSensorData;
        TextView tempSensorData;
        TextView humSensorData;
        TextView vbatSensorData;
        TextView timestampSensorData;

    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SensorDataViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SensorDataViewHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.nameSensorData = (TextView)row.findViewById(R.id.nameSensorData);
            holder.tempSensorData = (TextView)row.findViewById(R.id.tempSensorData);
            holder.humSensorData = (TextView)row.findViewById(R.id.humSensorData);
            holder.vbatSensorData = (TextView)row.findViewById(R.id.vbatSensorData);
            holder.timestampSensorData = (TextView)row.findViewById(R.id.timestampSensorData);

            row.setTag(holder);
        }
        else
        {
            holder = (SensorDataViewHolder)row.getTag();
        }

        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
        holder.nameSensorData.setText(lSensorData.get(position).getSensorName());
        holder.tempSensorData.setText(lSensorData.get(position).getTempString());
        holder.humSensorData.setText(lSensorData.get(position).getHumString());
        holder.vbatSensorData.setText(lSensorData.get(position).getVbatString());
        holder.timestampSensorData.setText(lSensorData.get(position).getTimestampString());
        return row;
    }
}
