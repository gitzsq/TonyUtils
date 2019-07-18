package com.tony.utils.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tony.utils.R;
import com.tony.utils.business.entity.StepEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartsActivity extends AppCompatActivity {
    private ListView listview;
    private List<StepEntity> stepEntityList = new ArrayList<>();

    private LineChartView lineChart;
    private List<StepEntity> list;
    String[] date;
    float[] score;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        initView();
        initdata();


        date = new String[list.size()];
        score = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            date[i] = list.get(i).getCurdate();
            score[i] = Float.parseFloat(list.get(i).getSteps());

        }
        getAxisXLables();
        getAxisPoints();
        initLineChart();

    }

    private void initdata() {
        list=new ArrayList<>();
        for(int i=10;i<20;i++) {
            StepEntity stepEntity = new StepEntity("2019-01-"+i,i+"" );
            list.add(stepEntity);
        }

    }


    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);
        //折线图上每个数据点的形状，这里是圆形
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines); //坐标轴
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//设置字体颜色
        axisX.setTextSize(8);//设置字体大小
        axisX.setMaxLabelChars(8);//最多几个X轴坐标
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);
        Axis axisY = new Axis();
        axisY.setName("");
        axisY.setTextSize(8);
        data.setAxisYLeft(axisY); //设置行为属性，缩放、滑动、平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 3);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE); //设置X轴数据的显示个数（x轴0-7个数据）
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        lineChart = (LineChartView) findViewById(R.id.line_chart);
    }

    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

}
