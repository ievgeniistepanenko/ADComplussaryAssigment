package com.example.stepanenko.dicecup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.stepanenko.dicecup.Model.Board;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HistoryListActivity extends AppCompatActivity {

    ListView listView;
    Button buttonBack;
    Button buttonClear;
    Map<Date,int[]> history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        listView = (ListView)findViewById(R.id.listView);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonBackClick();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonCleanClick();
            }
        });

        history = getData();
        listView.setAdapter(createAdapter( history ));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("map", (Serializable) history);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       // history = (Map) savedInstanceState.getSerializable("map");
    }

    private void onButtonCleanClick() {
        //listView.removeAllViews();
        Intent intent = new Intent();
        intent.putExtra("isDeleted",true);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void onButtonBackClick() {
        finish();
    }

    private List<Map<String, String>> asListMap(Map<Date, int[]> src)
    {
        List<Map<String, String>> res = new ArrayList<Map<String, String>>();

        Set<Date>  keySet = src.keySet();

        for (Date d : keySet)
        {
            Map<String, String> e = new HashMap<String, String>();
            e.put("date", getStringDate(d));
            e.put("values", getStringArray(src.get(d)));
            res.add(e);
        }
        return res;
    }

    protected SimpleAdapter createAdapter(Map<Date,int[]> data) {

        SimpleAdapter adapter =
                new SimpleAdapter(this,
                        asListMap(data),
                        R.layout.cell,
                        new String[] { "date", "values" },
                        new int[] { R.id.textDate, R.id.textValue });


        return adapter;
    }

    private TreeMap<Date,int[]> getData(){
        Intent intent = getIntent();
        TreeMap<Date,int[]> treeMap = new TreeMap<>( (Map<Date, int[]>) intent.getSerializableExtra("map") );
        return treeMap;
    }

    private String getStringDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
        return df.format(date);
    }

    private String getStringArray(int[] array){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++){
            sb.append( array[i]+" ");
        }
        return sb.toString();
    }
}



