package com.bom.foodshop;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

class ShopDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "foodshop"; // Имя базы данных starbuzz
    private static final int DB_VERSION = 3; // Версия базы данных

    ShopDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        updateMyDatabase(db, oldVersion, newVersion);
    }
    private static void insertData(SQLiteDatabase db, String name,
                                   String description, int resourceId, String tableName) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert(tableName, null, drinkValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion<1){
            db.execSQL("CREATE TABLE DRINK ("
                    +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"NAME TEXT, "
                    +"DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertData(db, "Latte", "Espresso and steamed milk", R.drawable.latte, "DRINK");
            insertData(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino, "DRINK");
            insertData(db, "Filter", "Our best drip coffee", R.drawable.filter, "DRINK");
        }
        if(oldVersion<2){
            //код добавления нового столбца
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
        if(oldVersion<3){
            //код добавления новой таблицы
            db.execSQL("CREATE TABLE FOOD ("
                    +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"NAME TEXT, "
                    +"DESCRIPTION TEXT, "
                    +"IMAGE_RESOURCE_ID INTEGER, "
                    +"FAVORITE NUMERIC);");
            insertData(db, "Burger", "tasty burger", R.drawable.burger, "FOOD");
            insertData(db, "Fri", "crisby popato fri in fryer", R.drawable.fri, "FOOD");
            insertData(db, "Shaverma", "cool shaverma grill in our souse", R.drawable.shava, "FOOD");
        }
    }
}
