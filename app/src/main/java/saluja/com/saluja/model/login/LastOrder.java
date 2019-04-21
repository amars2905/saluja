package saluja.com.saluja.model.login;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastOrder implements Parcelable {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("date")
    @Expose
    private Object date;
    public final static Parcelable.Creator<LastOrder> CREATOR = new Creator<LastOrder>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LastOrder createFromParcel(Parcel in) {
            return new LastOrder(in);
        }

        public LastOrder[] newArray(int size) {
            return (new LastOrder[size]);
        }

    };

    protected LastOrder(Parcel in) {
        this.id = ((Object) in.readValue((Object.class.getClassLoader())));
        this.date = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public LastOrder() {
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}