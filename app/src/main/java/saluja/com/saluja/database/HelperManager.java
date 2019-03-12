package saluja.com.saluja.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import saluja.com.saluja.model.ProductDetail;


public class HelperManager {

    Context context;

    SQLiteDatabase db;
    Helper helper;

    public HelperManager(Context context) {
        this.context = context;
        helper = new Helper(context, Helper.DBName, null, Helper.DBVERSION);
    }

    // wishlist method
    // 1. insert wishlist
    public boolean insertWishlist(ProductDetail productDetail) {

        db = helper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(DatabaseConstant.TABLE_WISHLIST_ID, productDetail.getId());
        v.put(DatabaseConstant.TABLE_WISHLIST_NAME, productDetail.getName());
        v.put(DatabaseConstant.TABLE_WISHLIST_PRICE, productDetail.getPrice());
        v.put(DatabaseConstant.TABLE_WISHLIST_REG_PRICE, productDetail.getReg_price());
        v.put(DatabaseConstant.TABLE_WISHLIST_SALE_PRICE, productDetail.getSelected_size());
        v.put(DatabaseConstant.TABLE_WISHLIST_HTML_PRICE, productDetail.getSelected_color());
        v.put(DatabaseConstant.TABLE_WISHLIST_IMAGE, productDetail.getImage());
        v.put(DatabaseConstant.TABLE_WISHLIST_IMAGES_ARRAY, productDetail.getImages_array());
        v.put(DatabaseConstant.TABLE_WISHLIST_ARRTIB, productDetail.getAttributes_array());
        v.put(DatabaseConstant.TABLE_WISHLIST_DESCRIPTION, productDetail.getDescription());
        v.put(DatabaseConstant.TABLE_WISHLIST_QUANTITY, productDetail.getQuantity());
        long isCheck = db.insert(DatabaseConstant.TABLE_NAME_WISHLIST, null, v);
        if (isCheck > 0) {
            return true;
        } else {
            return false;
        }
    }

    //2. delete all wishlist
    public void deleteallWishlist() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + DatabaseConstant.TABLE_NAME_WISHLIST);
        db.close();
    }

    // 3. read all wishlist
    public ArrayList<ProductDetail> readAllWishlist() {
        db = helper.getReadableDatabase();
        ArrayList<ProductDetail> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + DatabaseConstant.TABLE_NAME_WISHLIST, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String regprice = cursor.getString(3);
            String saleprice = cursor.getString(4);
            String htmlprice = cursor.getString(5);
            String image = cursor.getString(6);
            String imgarray = cursor.getString(7);
            String attribrray = cursor.getString(8);
            String description = cursor.getString(9);
            int qty = cursor.getInt(10);

            list.add(new ProductDetail(id, name, description, price, regprice, saleprice, htmlprice, image, imgarray, attribrray,
                    qty));
        }
        return list;
    }

    // 4. read only wishlist id
    public ArrayList<String> readAllWishlistID() {
        db = helper.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + DatabaseConstant.TABLE_NAME_WISHLIST, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            list.add(id);
        }
        return list;
    }

    // 5. delete one product
    public void deletesingleWishlist(ProductDetail productDetail) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DatabaseConstant.TABLE_NAME_WISHLIST, DatabaseConstant.TABLE_WISHLIST_ID + " = ?",
                new String[]{String.valueOf(productDetail.getId())});
        db.close();
    }

    // cart method
    // 1. insert cart
    public boolean insertCart(ProductDetail productDetail) {

        db = helper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(DatabaseConstant.TABLE_CART_ID, productDetail.getId());
        v.put(DatabaseConstant.TABLE_CART_NAME, productDetail.getName());
        v.put(DatabaseConstant.TABLE_CART_PRICE, productDetail.getPrice());
        v.put(DatabaseConstant.TABLE_CART_REG_PRICE, productDetail.getReg_price());
        v.put(DatabaseConstant.TABLE_CART_SALE_PRICE, productDetail.getSelected_size());
        v.put(DatabaseConstant.TABLE_CART_HTML_PRICE, productDetail.getSelected_color());
        v.put(DatabaseConstant.TABLE_CART_IMAGE, productDetail.getImage());
        v.put(DatabaseConstant.TABLE_CART_IMAGES_ARRAY, productDetail.getImages_array());
        v.put(DatabaseConstant.TABLE_CART_ARRTIB, productDetail.getAttributes_array());
        v.put(DatabaseConstant.TABLE_CART_DESCRIPTION, productDetail.getDescription());
        v.put(DatabaseConstant.TABLE_CART_QUANTITY, productDetail.getQuantity());
        long isCheck = db.insert(DatabaseConstant.TABLE_NAME_CART, null, v);
        if (isCheck > 0) {
            return true;
        } else {
            return false;
        }
    }

    //2. delete all wishlist
    public void deleteallCart() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + DatabaseConstant.TABLE_NAME_CART);
        db.close();
    }

    // 3. read all cart
    public ArrayList<ProductDetail> readAllCart() {
        db = helper.getReadableDatabase();
        ArrayList<ProductDetail> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + DatabaseConstant.TABLE_NAME_CART, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String regprice = cursor.getString(3);
            String saleprice = cursor.getString(4);
            String htmlprice = cursor.getString(5);
            String image = cursor.getString(6);
            String imgarray = cursor.getString(7);
            String attribrray = cursor.getString(8);
            String description = cursor.getString(9);
            int qty = cursor.getInt(10);

            list.add(new ProductDetail(id, name, description, price, regprice, saleprice, htmlprice, image, imgarray,
                    attribrray, qty));
        }
        return list;
    }

    // 4. read only cart id
    public ArrayList<String> readAllCartID() {
        db = helper.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + DatabaseConstant.TABLE_NAME_CART, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            list.add(id);
        }
        return list;
    }


    // 3. delete one product from cart
    public void deletesingleCart(ProductDetail productDetail) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DatabaseConstant.TABLE_NAME_CART, DatabaseConstant.TABLE_CART_ID + " = ?",
                new String[]{String.valueOf(productDetail.getId())});
        db.close();
    }

    //4. update cart
    public void updateCart(int quntity, String id) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstant.TABLE_CART_QUANTITY, quntity);
        // updating row
        db.update(DatabaseConstant.TABLE_NAME_CART, values, DatabaseConstant.TABLE_CART_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


/*
    // 5.read all cart ids
    public ArrayList<String> readOnlyCartID() {
        db = helper.getReadableDatabase();
        ArrayList<String> cart_list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "
                + DatabaseConstant.TABLE_NAME_CART, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            cart_list.add(id);
        }
        return cart_list;
    }
*/

/*
    //6. delete cart
    public void deleteAll() {
        db = helper.getWritableDatabase();
        db.delete(DatabaseConstant.TABLE_NAME_CART, null, null);
        db.close();
    }
*/
}
