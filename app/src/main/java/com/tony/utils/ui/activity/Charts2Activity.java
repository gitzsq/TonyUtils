package com.tony.utils.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tony.utils.R;
import com.tony.utils.customview.LineCharView;

import java.util.ArrayList;
import java.util.List;

public class Charts2Activity extends AppCompatActivity {

    private LineCharView test;
    List<String> x_coords =new ArrayList<>();
    List<String> x_coord_values=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts2);
        test=(LineCharView)findViewById(R.id.test);
        for(int i=0;i<20;i++){
            x_coords.add(i+"");
            x_coord_values.add("A");
        }
        test.setValue(x_coords,x_coord_values);
    }
}
