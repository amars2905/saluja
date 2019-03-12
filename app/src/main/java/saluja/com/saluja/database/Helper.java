package saluja.com.saluja.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Win-7 on 5/14/2016.
 */
public class Helper extends SQLiteOpenHelper {

    public static String DBName = "stygian";
    public static int DBVERSION = 3;

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table " + DatabaseConstant.TABLE_NAME_WISHLIST + "(" + DatabaseConstant.TABLE_WISHLIST_ID + " text,"
                + DatabaseConstant.TABLE_WISHLIST_NAME + " text,"
                + DatabaseConstant.TABLE_WISHLIST_PRICE + " text,"
                + DatabaseConstant.TABLE_WISHLIST_REG_PRICE + " text,"
                + DatabaseConstant.TABLE_WISHLIST_SALE_PRICE + " text,"
                + DatabaseConstant.TABLE_WISHLIST_HTML_PRICE + " text,"
                + DatabaseConstant.TABLE_WISHLIST_IMAGE + " text,"
                + DatabaseConstant.TABLE_WISHLIST_IMAGES_ARRAY + " text,"
                + DatabaseConstant.TABLE_WISHLIST_ARRTIB + " text,"
                + DatabaseConstant.TABLE_WISHLIST_DESCRIPTION + " text,"
                + DatabaseConstant.TABLE_WISHLIST_QUANTITY + " INTEGER)");

        db.execSQL("Create table " + DatabaseConstant.TABLE_NAME_CART + "(" + DatabaseConstant.TABLE_CART_ID + " text,"
                + DatabaseConstant.TABLE_CART_NAME + " text,"
                + DatabaseConstant.TABLE_CART_PRICE + " text,"
                + DatabaseConstant.TABLE_CART_REG_PRICE + " text,"
                + DatabaseConstant.TABLE_CART_SALE_PRICE + " text,"
                + DatabaseConstant.TABLE_CART_HTML_PRICE + " text,"
                + DatabaseConstant.TABLE_CART_IMAGE + " text,"
                + DatabaseConstant.TABLE_CART_IMAGES_ARRAY + " text,"
                + DatabaseConstant.TABLE_CART_ARRTIB + " text,"
                + DatabaseConstant.TABLE_CART_DESCRIPTION + " text,"
                + DatabaseConstant.TABLE_CART_QUANTITY + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
