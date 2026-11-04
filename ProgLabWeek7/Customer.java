public class Customer {
    private String name;
    private String phone;
    private Address address;

    // Adres bilgileri parametre olarak gelir, Address nesnesini Customer kendisi olusturur (composition)
    public Customer(String name, String phone, String street, String district, String city) {
        this.name = name;
        this.phone = phone;
        this.address = new Address(street, district, city);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return name + " - " + phone + " - " + address;
    }
}
