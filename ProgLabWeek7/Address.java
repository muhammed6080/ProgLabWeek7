public class Address {
    private String street;
    private String district;
    private String city;

    public Address(String street, String district, String city) {
        this.street = street;
        this.district = district;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + district + " / " + city;
    }
}
