package com.bom.foodshop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor favoritesCursor;
    private Cursor favoritesCursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> NonScrollListView,
                                    View itemView,
                                    int position,
                                    long l) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, FoodCategoryActivity.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, Shops.class);
                    startActivity(intent);
                }

            }
        };
        NonScrollListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        NonScrollListView listFavorites =  findViewById(R.id.list_favorites);
        NonScrollListView listFavorites1 =  findViewById(R.id.list_favorites1);
        try {
            SQLiteOpenHelper databaseHelper = new ShopDatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            favoritesCursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE=1",
                    null, null, null, null);
            favoritesCursor1 = db.query("FOOD",
                    new String[]{"_id", "NAME"},
                    "FAVORITE=1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1, favoritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            CursorAdapter favoriteAdapter1 = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1, favoritesCursor1,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
            listFavorites1.setAdapter(favoriteAdapter1);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> NonScrollListView, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
                startActivity(intent);
            }
        });
        listFavorites1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> NonScrollListView, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                intent.putExtra(FoodActivity.EXTRA_FOODNO, (int) id);
                startActivity(intent);
            }
        });
    }

    //Закрытие курсора и базы данных в методе onDestroy()
    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        favoritesCursor1.close();
        db.close();
    }


    public void onRestart() {
        super.onRestart();
        try {
            ShopDatabaseHelper databaseHelper = new ShopDatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            Cursor newCursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            NonScrollListView listFavorites =  findViewById(R.id.list_favorites);
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;
            Cursor newCursor1 = db.query("FOOD",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            NonScrollListView listFavorites1 =  findViewById(R.id.list_favorites1);
            CursorAdapter adapter1 = (CursorAdapter) listFavorites1.getAdapter();
            adapter1.changeCursor(newCursor1);
            favoritesCursor = newCursor1;
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}