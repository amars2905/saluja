package saluja.com.saluja.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDetail implements Parcelable {

    private int keyId;
    private String id;
    private String name;
    private String price;
    private String reg_price;
    private String selected_size;
    private String selected_color;
    private String image;
    private String images_array;
    private String attributes_array;
    private int quantity;
    private String description;

    public ProductDetail() {

    }

    public ProductDetail(int keyId, String id, String name, String description, String price, String reg_price, String sale_price,
                         String html_price, String image, String images_array, String attributes_array, int quantity) {
        this.keyId = keyId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.reg_price = reg_price;
        this.selected_size = sale_price;
        this.selected_color = html_price;
        this.image = image;
        this.images_array = images_array;
        this.attributes_array = attributes_array;
        this.quantity = quantity;
    }

    public ProductDetail(String id, String name, String description, String price, String reg_price, String sale_price,
                         String html_price, String image, String images_array, String attributes_array, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.reg_price = reg_price;
        this.selected_size = sale_price;
        this.selected_color = html_price;
        this.image = image;
        this.images_array = images_array;
        this.attributes_array = attributes_array;
        this.quantity = quantity;
    }

    protected ProductDetail(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        reg_price = in.readString();
        selected_size = in.readString();
        selected_color = in.readString();
        image = in.readString();
        images_array = in.readString();
        attributes_array = in.readString();
        quantity = in.readInt();
        keyId = in.readInt();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final Creator<ProductDetail> CREATOR = new Creator<ProductDetail>() {
        @Override
        public ProductDetail createFromParcel(Parcel in) {
            return new ProductDetail(in);
        }

        @Override
        public ProductDetail[] newArray(int size) {
            return new ProductDetail[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReg_price() {
        return reg_price;
    }

    public void setReg_price(String reg_price) {
        this.reg_price = reg_price;
    }

    public String getSelected_size() {
        return selected_size;
    }

    public void setSelected_size(String selected_size) {
        this.selected_size = selected_size;
    }

    public String getSelected_color() {
        return selected_color;
    }

    public void setSelected_color(String selected_color) {
        this.selected_color = selected_color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImages_array() {
        return images_array;
    }

    public void setImages_array(String images_array) {
        this.images_array = images_array;
    }

    public String getAttributes_array() {
        return attributes_array;
    }

    public void setAttributes_array(String attributes_array) {
        this.attributes_array = attributes_array;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(price);
        parcel.writeString(reg_price);
        parcel.writeString(selected_size);
        parcel.writeString(selected_color);
        parcel.writeString(image);
        parcel.writeString(images_array);
        parcel.writeString(attributes_array);
        parcel.writeInt(quantity);
        parcel.writeInt(keyId);
    }
}
