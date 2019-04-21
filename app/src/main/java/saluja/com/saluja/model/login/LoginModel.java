package saluja.com.saluja.model.login;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("last_order")
    @Expose
    private LastOrder lastOrder;
    @SerializedName("orders_count")
    @Expose
    private Integer ordersCount;
    @SerializedName("total_spent")
    @Expose
    private String totalSpent;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("billing")
    @Expose
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("_links")
    @Expose
    private Links links;
    public final static Parcelable.Creator<LoginModel> CREATOR = new Creator<LoginModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        public LoginModel[] newArray(int size) {
            return (new LoginModel[size]);
        }

    }
            ;

    protected LoginModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModified = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.lastOrder = ((LastOrder) in.readValue((LastOrder.class.getClassLoader())));
        this.ordersCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalSpent = ((String) in.readValue((String.class.getClassLoader())));
        this.avatarUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.billing = ((Billing) in.readValue((Billing.class.getClassLoader())));
        this.shipping = ((Shipping) in.readValue((Shipping.class.getClassLoader())));
        this.links = ((Links) in.readValue((Links.class.getClassLoader())));
    }

    public LoginModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LastOrder getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(LastOrder lastOrder) {
        this.lastOrder = lastOrder;
    }

    public Integer getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(Integer ordersCount) {
        this.ordersCount = ordersCount;
    }

    public String getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(String totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(dateCreated);
        dest.writeValue(dateModified);
        dest.writeValue(email);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(username);
        dest.writeValue(lastOrder);
        dest.writeValue(ordersCount);
        dest.writeValue(totalSpent);
        dest.writeValue(avatarUrl);
        dest.writeValue(billing);
        dest.writeValue(shipping);
        dest.writeValue(links);
    }

    public int describeContents() {
        return 0;
    }

}