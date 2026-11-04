import java.util.ArrayList;

public class Order {
    // Static uyeler
    private static int nextOrderNumber = 1001;
    public static final double MIN_ORDER = 100;
    public static final double DELIVERY_FEE = 20;
    private static int completedOrders = 0;
    private static double totalRevenue = 0;

    // Nesne uyeleri
    private int orderNumber;
    private Customer customer;
    private ArrayList<MenuItem> items;
    private Coupon coupon;
    private boolean completed;

    public Order(Customer customer) {
        this.orderNumber = nextOrderNumber++;   // Her yeni siparis sayactan numara alir
        this.customer = customer;
        this.items = new ArrayList<MenuItem>();
        this.coupon = null;
        this.completed = false;
    }

    // Ayni isimde urun varsa miktari artirilir
    public void addItem(MenuItem item) {
        for (MenuItem existing : items) {
            if (existing.getName().equalsIgnoreCase(item.getName())) {
                try {
                    existing.setQuantity(existing.getQuantity() + item.getQuantity());
                } catch (InvalidQuantityException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(String name) throws ItemNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                items.remove(i);
                return;
            }
        }
        throw new ItemNotFoundException("Sipariste bu urun bulunamadi: " + name);
    }

    public void updateQuantity(String name, int quantity)
            throws ItemNotFoundException, InvalidQuantityException {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(quantity);   // gecersizse InvalidQuantityException firlatir
                return;
            }
        }
        throw new ItemNotFoundException("Sipariste bu urun bulunamadi: " + name);
    }

    public double getSubtotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // Disaridan degistirilemesin diye kopya dondurur
    public ArrayList<MenuItem> getItems() {
        return new ArrayList<MenuItem>(items);
    }

    public void applyCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public double checkout() throws MinimumOrderException {
        if (completed) {
            throw new IllegalStateException("Bu siparis zaten tamamlandi: " + orderNumber);
        }

        double subtotal = getSubtotal();
        if (subtotal < MIN_ORDER) {
            throw new MinimumOrderException("Minimum siparis tutari " + MIN_ORDER
                    + " TL. Mevcut tutar: " + subtotal + " TL");
        }

        // Kupon sadece ara toplam minSubtotal'a esit veya buyukse uygulanir
        double discount = 0;
        if (coupon != null && subtotal >= coupon.getMinSubtotal()) {
            discount = subtotal * coupon.getDiscountRate() / 100;
        }

        double total = subtotal - discount + DELIVERY_FEE;

        // Fis
        System.out.println("========== FIS ==========");
        System.out.println("Siparis No : " + orderNumber);
        System.out.println("Musteri    : " + customer.getName());
        System.out.println("Adres      : " + customer.getAddress());
        System.out.println("Urunler:");
        for (MenuItem item : items) {
            System.out.println("  - " + item);
        }
        System.out.println("Ara Toplam    : " + subtotal + " TL");
        System.out.println("Kupon Indirimi: " + discount + " TL");
        System.out.println("Teslimat      : " + DELIVERY_FEE + " TL");
        System.out.println("TOPLAM        : " + total + " TL");
        System.out.println("=========================");

        // Static istatistikleri guncelle
        completed = true;
        completedOrders++;
        totalRevenue += total;

        return total;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public static int getCompletedOrders() {
        return completedOrders;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }
}
