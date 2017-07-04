package com.adilsal33m.menucard;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adil Saleem on 3/1/2017.
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    public DataBaseHelper dataBaseHelper;
    public SQLiteDatabase mDatabase;
    public String mTable;

    public ArrayList<DataModel> dataModels;
    public CustomAdapter adapter;
    public ListView listView;

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        // Get arguments passed in, if any
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(ARG_PAGE,page);
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

        View view = inflater.inflate(R.layout.fragment_page_list, container, false);
        dataModels= new ArrayList<DataModel>();

        Bundle args = getArguments();
        if (args != null) {
            switch (args.getInt(ARG_PAGE)){
                case 0:
                    mTable = "Starter";
                    break;
                case 1:
                        mTable = "Main_Course";
                        break;
                case 2:
                    mTable = "Dessert";
                    break;
                case 3:
                    mTable = "Others";
                    break;
                default:
                    mTable = "Starter";

            }
        }

        dataBaseHelper= new DataBaseHelper(getActivity());
        mDatabase= dataBaseHelper.openOrCreate();
        String query = "SELECT * FROM "+mTable;
        Cursor cursor = mDatabase.rawQuery(query, null);

        while(cursor.moveToNext() & cursor.getCount()>0)
            dataModels.add(new DataModel(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(0)));

        adapter= new CustomAdapter(dataModels,getActivity());

        listView=(ListView) view;
        listView.setAdapter(adapter);
        return view;
    }
}