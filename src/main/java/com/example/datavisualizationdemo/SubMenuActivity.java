package com.example.datavisualizationdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SubMenuActivity extends AppCompatActivity {

    double lat;
    double lon;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        Intent in = getIntent();
        index = in.getIntExtra("com.example.datavisualizationdemo.PIC_INDEX", -1);
        Button graphBtn = (Button) findViewById(R.id.historyButton);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), LineChartActivity.class);
                startIntent.putExtra("com.example.datavisualizationdemo.PIC_INDEX", index + 1);
                startActivity(startIntent);
            }
        });
        Button realTimeButton = (Button) findViewById(R.id.realTimeButton);
        realTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), RealTimeActivity.class);
                startIntent.putExtra("com.example.datavisualizationdemo.PIC_INDEX", index + 1);
                startActivity(startIntent);
            }
        });
        Button mapButton = (Button) findViewById(R.id.mapButton);
        GetData getData = new GetData();
        getData.execute("");
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startIntent.putExtra("com.example.datavisualizationdemo.PIC_INDEX", Integer.toString(index+1) + " " + Double.toString(lon) + " " + Double.toString(lat));
                System.out.println(Integer.toString(index+1) + " " + Double.toString(lon) + " " + Double.toString(lat));
                startActivity(startIntent);
            }
        });

    }
        public class GetData extends AsyncTask<String, String, String> {

            static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

            @Override
            protected String doInBackground(String... strings) {

                Connection conn = null;
                Statement stmt = null;
                try{
                    Class.forName(JDBC_DRIVER).newInstance();
                    ///////////
                    conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/Sensor", DbStrings.USERNAME, DbStrings.PASSWORD);
                    ///////////
                    stmt = conn.createStatement();
                    Intent in = getIntent();
                    String sql = "select * from Sensor.SensorLoc where id = " + String.valueOf(index+1) + ";";
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        lat = rs.getDouble("lat");
                        lon = rs.getDouble("lon");
                    }
                    rs.close();
                    stmt.close();
                    conn.close();

                }catch(SQLException connError){
                    connError.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        if(stmt != null) {
                            stmt.close();
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    try{
                        if(conn != null) {
                            conn.close();
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
                return null;
            }

        }



}


