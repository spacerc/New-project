package com.hfad.starbuzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodActivity extends AppCompatActivity {
public static final String EXTRA_FOODNO = "NoFood";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        int foodNo = (Integer)getIntent().getExtras().get(EXTRA_FOODNO);
        Food food = Food.foods[foodNo];
        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(food.getImageResourceId());
        photo.setContentDescription(food.getName());
        TextView name = findViewById(R.id.name);
        name.setText(food.getName());
        TextView description = (TextView)findViewById(R.id.description);
        description.setText(food.getDescription());

    }
}
