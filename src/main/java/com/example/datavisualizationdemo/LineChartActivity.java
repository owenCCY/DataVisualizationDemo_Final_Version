package com.example.datavisualizationdemo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class LineChartActivity extends AppCompatActivity {

    TextView progressTextView;
    TextView data;
    Map<Integer, Float> map = new LinkedHashMap<>();
    LineChart lineChart;
    ArrayList<Entry>  entries = new ArrayList<>();
    //ArrayList<String> ids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        Resources res = getResources();
        progressTextView = (TextView) findViewById(R.id.progressTextView);
        data = (TextView) findViewById(R.id.data);

        progressTextView.setText("");
        Button btn = (Button) findViewById(R.id.getDataButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData retrieveData = new GetData();
                retrieveData.execute("");
            }
        });

        lineChart = (LineChart) findViewById(R.id.lineChart);


    }

    private class GetData extends AsyncTask<String, String, String>{

        String msg = "";
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String URL = DbStrings.URL;

        @Override
        protected void onPreExecute(){
            progressTextView.setText("Connecting to database ... ");
        }

        @Override
        protected String doInBackground(String... strings) {

            Connection conn = null;
            Statement stmt = null;
            try{
                Class.forName(JDBC_DRIVER).newInstance();
                ///////////
                conn = DriverManager.getConnection(URL, DbStrings.USERNAME, DbStrings.PASSWORD);
                ///////////
                stmt = conn.createStatement();
                Intent in = getIntent();
                //if(in.getIntArrayExtra("checkedList") == null)
                final int index = in.getIntExtra("com.example.datavisualizationdemo.PIC_INDEX", -1);
                String sql = "select * from Sensor.SensorData where SensorId = " + String.valueOf(index) + ";";
                ResultSet rs = stmt.executeQuery(sql);
                int i = 0;
                while(rs.next()){
                    //Integer id = rs.getInt("Id");
                    float quantity = rs.getFloat("Quantity");
                    map.put(i, quantity);
                    entries.add(new Entry(i, quantity));
                    i++;
                }
                LineDataSet data = new LineDataSet(entries, "Quantity");
                LineData lineData = new LineData(data);
                lineChart.setData(lineData);
                lineChart.invalidate();
                msg = "Process complete.";
                rs.close();
                stmt.close();
                conn.close();

            }catch(SQLException connError){
                msg = connError.toString();
                connError.printStackTrace();
            } catch (ClassNotFoundException e) {
                msg = "JDBC NOT FOUND.";
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

        @Override
        protected void onPostExecute(String msg){

            progressTextView.setText(this.msg);

            if(map.size() > 0){
                data.setText("Success");
            }else{
                data.setText("Failed");
            }

        }

    }

}//
