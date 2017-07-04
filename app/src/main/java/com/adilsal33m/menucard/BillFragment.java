package com.adilsal33m.menucard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Adil Saleem on 3/1/2017.
 */

public class BillFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static ArrayList<DataModel> orderList= new ArrayList<DataModel>();

    public static String total="Rs. 0";

    public static BillFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        orderList= new ArrayList<DataModel>();
        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void hideUI(){
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_page, container, false);
//        TextView textView= (TextView) view;
//        textView.setText("Fragment# "+mPage);
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillBill();
        final Button orderButton= (Button)getActivity().findViewById(R.id.order_button);
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.button_anim);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderButton.startAnimation(myAnim);
                orderList= new ArrayList<DataModel>();
                final LinearLayout linearLayout=(LinearLayout) getActivity().findViewById(R.id.bill_layout);
                linearLayout.animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                linearLayout.setAlpha(1.0f);
                                fillBill();
                            }
                        });
            }
        });
    }

    public void fillBill(){
        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0f);
        btnParams.rightMargin= 8;
        btnParams.gravity= Gravity.CENTER_VERTICAL;

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        textParams.gravity= Gravity.CENTER_VERTICAL;

        final LinearLayout ll=(LinearLayout) getActivity().findViewById(R.id.bill_layout);
        ll.removeAllViews();
        for(int i=0;i<orderList.size();i++) {
            LinearLayout lm = new LinearLayout(getActivity());
            lm.setBackgroundColor(Color.parseColor("#80000000"));
            lm.setPadding(8,8,8,8);
            lm.setOrientation(LinearLayout.HORIZONTAL);

            final TextView textView = new TextView(getActivity());
            textView.setText(orderList.get(i).getName());
            textView.setPadding(8,0,8,8);
            textView.setTextSize(18);
            textView.setTextColor(Color.WHITE);
            textView.setLayoutParams(textParams);
            lm.addView(textView);
            //Price
            final TextView price = new TextView(getActivity());
            price.setText("Rs."+orderList.get(i).getPrice());
            price.setPadding(8,0,8,0);
            price.setTextSize(18);
            price.setLayoutParams(btnParams);
            price.setTextColor(Color.WHITE);
            lm.addView(price);
            //Item Count
            final TextView count = new TextView(getActivity());
            count.setText("x"+orderList.get(i).getCount());
            count.setPadding(8,0,8,0);
            count.setTextSize(18);
            count.setLayoutParams(btnParams);
            count.setTextColor(Color.RED);
            lm.addView(count);

            // Create Button
            Button btn = new Button(getActivity());
            // Give button an ID
            //btn.setId(j+1);
            btn.setText("Remove");
            btn.setBackgroundResource(R.drawable.button_shape_2);
            // set the layoutParams on the button
            btn.setPadding(8,0,8,0);
            btn.setTextColor(Color.WHITE);
            btn.setLayoutParams(btnParams);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final View v=view;
                    DataModel item=removeItem(orderList,textView.getText().toString());
                    if(item==null) {
                        ((LinearLayout) v.getParent()).animate()
                                .alpha(0.0f)
                                .translationX(200f)
                                .setDuration(300)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        ((LinearLayout) v.getParent()).setVisibility(View.GONE);
                                    }
                                });
                    }
                    else
                        count.setText("x"+item.getCount());
                    calculateTotal();
                }
            });
            lm.addView(btn);
            ll.addView(lm);
        }
        calculateTotal();
    }

    public void calculateTotal(){
        int totalSum=0;
        for (DataModel dm:orderList){
            totalSum=totalSum+(Integer.parseInt(dm.getPrice())*Integer.parseInt(dm.getCount()));
        }
        TextView t=(TextView)getActivity().findViewById(R.id.bill_total);
        t.setText("Rs."+totalSum+"/-");

    }

    public void addItem(ArrayList<DataModel> dataModels,String name,String price){
        DataModel item= null;
        for(DataModel d:dataModels){
            if(d.getName().equals(name)){
                item=d;
                break;
            }
        }
        if(item != null){
            item.setCount(String.valueOf(Integer.parseInt(item.getCount())+1));
        }
        else
            dataModels.add(new DataModel(name,"",price,"1"));;
    }


    public DataModel removeItem(ArrayList<DataModel> dataModels,String name){
        DataModel item=null;
        for(DataModel d:dataModels){
            if(d.getName().equals(name)){
                if(d.getCount().equals("1"))
                    dataModels.remove(d);
                else {
                    d.setCount(String.valueOf(Integer.parseInt(d.getCount()) - 1));
                    item = d;
                }
                break;
            }
        }

        return item;
    }
}