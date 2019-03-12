package saluja.com.saluja.model;

public class SaveAddress
{
    private String user_id;
    private String user_address_id;
    private String address;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String date;

    public SaveAddress(String user_id, String user_address_id, String address, String country, String state, String city, String zipcode, String date) {
        this.user_id = user_id;
        this.user_address_id = user_address_id;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_address_id() {
        return user_address_id;
    }

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
