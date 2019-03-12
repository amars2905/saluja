package saluja.com.saluja.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import saluja.com.saluja.model.ProductDetail;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Url table name
    private static final String TABLE_URL = "url_table";
    // Url Table Columns names

    private static final String KEY_ID = "id";
    public static final String TABLE_CART_ID = "cartid";
    public static final String TABLE_CART_NAME = "cartname";
    public static final String TABLE_CART_PRICE = "cartprice";
    public static final String TABLE_CART_REG_PRICE = "cartregprice";
    public static final String TABLE_CART_SALE_PRICE = "cartsaleprice";
    public static final String TABLE_CART_HTML_PRICE = "carthtml";
    public static final String TABLE_CART_IMAGE = "cartimages";
    public static final String TABLE_CART_IMAGES_ARRAY = "cartimg_array";
    public static final String TABLE_CART_ARRTIB = "cartattrib";
    public static final String TABLE_CART_DESCRIPTION = "cartdes";
    public static final String TABLE_CART_QUANTITY = "cartqty";

    public DatabaseHandler(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_URL_TABLE = "CREATE TABLE " + TABLE_URL + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + TABLE_CART_ID + " TEXT," + TABLE_CART_NAME + " TEXT," + TABLE_CART_PRICE + " TEXT,"
                + TABLE_CART_REG_PRICE + " TEXT," + TABLE_CART_SALE_PRICE + " TEXT," + TABLE_CART_HTML_PRICE + " TEXT,"
                + TABLE_CART_IMAGE + " TEXT," + TABLE_CART_IMAGES_ARRAY + " TEXT," + TABLE_CART_ARRTIB + " TEXT,"
                + TABLE_CART_DESCRIPTION + " TEXT," + TABLE_CART_QUANTITY + " TEXT" + ")";
        db.execSQL(CREATE_URL_TABLE);
        Log.e("Table", CREATE_URL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URL);
        onCreate(db);
    }

    //Adding new Url
    public void addItemCart(ProductDetail urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CART_ID, urlModal.getId());
        values.put(TABLE_CART_NAME, urlModal.getName());
        values.put(TABLE_CART_PRICE, urlModal.getPrice());
        values.put(TABLE_CART_REG_PRICE, urlModal.getReg_price());
        values.put(TABLE_CART_SALE_PRICE, urlModal.getSelected_size());
        values.put(TABLE_CART_HTML_PRICE, urlModal.getSelected_color());
        values.put(TABLE_CART_IMAGE, urlModal.getImage());
        values.put(TABLE_CART_IMAGES_ARRAY, urlModal.getImages_array());
        values.put(TABLE_CART_ARRTIB, urlModal.getAttributes_array());
        values.put(TABLE_CART_DESCRIPTION, urlModal.getDescription());
        values.put(TABLE_CART_QUANTITY, urlModal.getQuantity());

        // Inserting InstructionsRow
        db.insert(TABLE_URL, null, values);
        db.close(); // Closing database connection
    }

    // Getting single url
    public ProductDetail getSingleItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_URL, new String[]{KEY_ID, TABLE_CART_ID, TABLE_CART_NAME, TABLE_CART_PRICE, TABLE_CART_REG_PRICE
                        , TABLE_CART_SALE_PRICE, TABLE_CART_HTML_PRICE, TABLE_CART_IMAGE, TABLE_CART_IMAGES_ARRAY,
                        TABLE_CART_ARRTIB, TABLE_CART_DESCRIPTION, TABLE_CART_QUANTITY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new ProductDetail(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                cursor.getString(10), Integer.parseInt(cursor.getString(11)));
    }

    //Getting all Url list
    public ArrayList<ProductDetail> getAllUrlList() {
        ArrayList<ProductDetail> urlModalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_URL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductDetail urlModal = new ProductDetail();
                urlModal.setKeyId(Integer.parseInt(cursor.getString(0)));
                urlModal.setId(cursor.getString(1));
                urlModal.setName(cursor.getString(2));
                urlModal.setPrice(cursor.getString(3));
                urlModal.setReg_price(cursor.getString(4));
                urlModal.setSelected_size(cursor.getString(5));
                urlModal.setSelected_color(cursor.getString(6));
                urlModal.setImage(cursor.getString(7));
                urlModal.setImages_array(cursor.getString(8));
                urlModal.setAttributes_array(cursor.getString(9));
                urlModal.setDescription(cursor.getString(10));
                urlModal.setQuantity(Integer.parseInt(cursor.getString(11)));
                urlModalList.add(urlModal);
            } while (cursor.moveToNext());
        }

        return urlModalList;
    }

    // Updating single urlModal
    public int updateUrl(ProductDetail urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, urlModal.getKeyId());
        values.put(TABLE_CART_ID, urlModal.getId());
        values.put(TABLE_CART_NAME, urlModal.getName());
        values.put(TABLE_CART_PRICE, urlModal.getPrice());
        values.put(TABLE_CART_REG_PRICE, urlModal.getReg_price());
        values.put(TABLE_CART_SALE_PRICE, urlModal.getSelected_size());
        values.put(TABLE_CART_HTML_PRICE, urlModal.getSelected_color());
        values.put(TABLE_CART_IMAGE, urlModal.getImage());
        values.put(TABLE_CART_IMAGES_ARRAY, urlModal.getImages_array());
        values.put(TABLE_CART_ARRTIB, urlModal.getAttributes_array());
        values.put(TABLE_CART_DESCRIPTION, urlModal.getDescription());
        values.put(TABLE_CART_QUANTITY, urlModal.getQuantity());

        int updateValue = db.update(TABLE_URL, values, KEY_ID + " = ?", new String[]{String.valueOf(urlModal.getKeyId())});
        db.close();
        return updateValue;
    }

    // Deleting single url
    public void deleteContact(ProductDetail urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_URL, KEY_ID + " = ?",
                new String[]{String.valueOf(urlModal.getKeyId())});
        db.close();
    }

    public void deleteallCart(DatabaseHandler databaseCart) {
        SQLiteDatabase db = databaseCart.getWritableDatabase();
        db.execSQL("delete from " + TABLE_URL);
        db.close();
    }

    // Getting url Count
    public boolean getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_URL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int i = cursor.getCount();
        cursor.close();
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    //check exist data
    public boolean verification(String _username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = -1;
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM " + TABLE_URL + " WHERE " + TABLE_CART_ID + " = ?";
            c = db.rawQuery(query, new String[]{_username});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }
}