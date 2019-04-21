package saluja.com.saluja.model.login;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection implements Parcelable {

    @SerializedName("href")
    @Expose
    private String href;
    public final static Parcelable.Creator<Collection> CREATOR = new Creator<Collection>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        public Collection[] newArray(int size) {
            return (new Collection[size]);
        }

    };

    protected Collection(Parcel in) {
        this.href = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Collection() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(href);
    }

    public int describeContents() {
        return 0;
    }

}