package com.example.project_sewing;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class graphActivity extends AppCompatActivity {
    private int HemSkirtDress=0,ShortPants=0,AddButton=0,ZipperReplacement =0,ShortDress=0;
    private Button graph_BTN_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        checkDetails();
        initGraph();

        graph_BTN_back = findViewById(R.id.graph_BTN_back);
        graph_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initGraph() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(ShortPants, 0));
        entries.add(new BarEntry(HemSkirtDress, 1));
        entries.add(new BarEntry(ShortDress,2));
        entries.add(new BarEntry(ZipperReplacement, 3));
        entries.add(new BarEntry(AddButton, 4));

        BarChart barChart = findViewById(R.id.barchart);
        BarDataSet bardataset = new BarDataSet(entries, "Keys");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Shorten pants");
        labels.add("Hem a skirt or dress");
        labels.add("Shorten dress-shirt sleeves");
        labels.add("Zipper replacement");
        labels.add("Add a button");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
    }


    private void checkDetails() {
        if(First_page.allEvents != null) {
            for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                Object x = First_page.allEvents.getData().keySet().toArray()[i];
                for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                    if (First_page.allEvents.getData().get(x).get(j).getStatus() == 2) {
                        String str = First_page.allEvents.getData().get(x).get(j).getType();
                        switch (str) {
                            case ("Shorten pants"):
                                ShortPants += 25;
                                break;
                            case ("Hem a skirt or dress"):
                                HemSkirtDress += 30;
                                break;
                            case ("Shorten dress-shirt sleeves"):
                                ShortDress += 55;
                                break;
                            case ("Zipper replacement"):
                                ZipperReplacement += 40;
                                break;
                            case ("Add a button"):
                                ShortDress += 20;
                                break;
                        }
                    }
                }
            }
        }
    }
}