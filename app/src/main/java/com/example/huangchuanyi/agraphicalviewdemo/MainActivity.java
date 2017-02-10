package com.example.huangchuanyi.agraphicalviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by huangchuanyi on 16/4/27.
 */
public class MainActivity extends Activity {

    private ColumnChartView mLineView;
    //private Button btn;
    //private Button btn2;
    private TextView dateTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btn = (Button) findViewById(R.id.btn);
        mLineView = (ColumnChartView) findViewById(R.id.asthma_report_pef_line);
        dateTV = (TextView) findViewById(R.id.date);
        //btn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        startActivity(new TemperatureChart().execute(MainActivity.this));
        //    }
        //});
        //
        //btn2 = (Button) findViewById(R.id.btn2);
        //btn2.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        startActivity(new TemperatureChart().execute(MainActivity.this));
        //    }
        //});

        mLineView.setData(2016, 04);
        mLineView.setOnItemListener(new ColumnChartView.Line2ViewListener() {
            @Override
            public void onChangeItem(int positon, List<PefListData> pefListDatas) {
                dateTV.setText(pefListDatas.get(positon).date);
            }

            @Override
            public void onFinishItem(int positon, List<PefListData> pefListDatas) {

            }

        });


//        WindowManager wm = (WindowManager) this
//                .getSystemService(Context.WINDOW_SERVICE);
//
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        Log.i("hcy", "width:" + width + ",height:" + height);
    }

}
