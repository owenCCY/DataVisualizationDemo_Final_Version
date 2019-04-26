package com.example.datavisualizationdemo;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

public class SensorListActivity extends AppCompatActivity {

    ListView sensorListView;
    String[] sensors;
    String[] IDs;
    String[] descriptions;
    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Resources res = getResources();
        sensorListView = (ListView) findViewById(R.id.sensorListView);
        sensors = res.getStringArray(R.array.sensors);
        IDs = res.getStringArray(R.array.IDs);
        descriptions = res.getStringArray(R.array.descriptions);

        SensorAdapter sensorAdapter = new SensorAdapter(this, sensors, IDs, descriptions);
        sensorListView.setAdapter(sensorAdapter);

        sensorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent showSubMenu = new Intent(getApplicationContext(), SubMenuActivity.class);
                showSubMenu.putExtra("com.example.datavisualizationdemo.PIC_INDEX", i);
                startActivity(showSubMenu);
            }
        });

    }

}
