package com.bom.foodshop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodActivity extends AppCompatActivity {
    public static final String EXTRA_FOODNO = "NoFood";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        int foodNo = (Integer)getIntent().getExtras().get(EXTRA_FOODNO);
        try {
            SQLiteOpenHelper databaseHelper = new ShopDatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor cursor = db.query("FOOD",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id=?",
                    new String[]{Integer.toString(foodNo)},
                    null, null, null);
            //код работы с курсором
            if(cursor.moveToFirst()){
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3)==1);

                TextView name = (TextView)findViewById(R.id.name1);
                name.setText(nameText);
                TextView description = (TextView)findViewById(R.id.description1);
                description.setText(descriptionText);
                ImageView photo = (ImageView)findViewById(R.id.photo1);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                CheckBox favorite = (CheckBox)findViewById(R.id.favorite1);
                favorite.setChecked(isFavorite);

            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onFavoriteClicked(View view){
        int foodNo = (Integer)getIntent().getExtras().get(EXTRA_FOODNO);
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite1);
        ContentValues foodvalues = new ContentValues();
        foodvalues.put("FAVORITE", favorite.isChecked());
        SQLiteOpenHelper databaseHelper = new ShopDatabaseHelper(FoodActivity.this);
        try{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            db.update("FOOD",foodvalues,"_id=?",
                    new String[]{Integer.toString(foodNo)});
            db.close();
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "database is unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}