package com.adilsal33m.menucard;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by Adil Saleem on 6/25/2017.
 */
public class CustomAdapter extends ArrayAdapter<DataModel>{

    private ArrayList<DataModel> dataSet;
    Context mContext;
    billInterface interfaceContext;
    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        Button info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
        interfaceContext=(billInterface)mContext;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (Button) convertView.findViewById(R.id.item_info);
            viewHolder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v,viewHolder.txtName.getText()+" added to order list", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                    interfaceContext.addToBill(viewHolder.txtName.getText().toString(),viewHolder.txtVersion.getText().toString());
                }
            });

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getDesc());
        viewHolder.txtVersion.setText(dataModel.getPrice());
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

    public interface billInterface{
        public void addToBill(String dish,String price);
    }
}