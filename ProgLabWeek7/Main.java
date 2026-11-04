public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer("Ahmet Yilmaz", "0555 111 22 33", "Gul Sokak No:5", "Melikgazi", "Kayseri");
        Customer c2 = new Customer("Ayse Demir", "0555 444 55 66", "Cinar Cad. No:12", "Selcuklu", "Konya");

        Order o1 = new Order(c1);
        Order o2 = new Order(c2);
        Order o3 = new Order(c1);

        // Siparis numaralari otomatik veriliyor
        System.out.println("Siparis numaralari: " + o1.getOrderNumber() + ", "
                + o2.getOrderNumber() + ", " + o3.getOrderNumber());

        try {
            // Urun ekleme (ayni urun iki kez eklenir -> miktar artar)
            o1.addItem(new MenuItem("Pizza", 80, 1));
            o1.addItem(new MenuItem("Pizza", 80, 1));
            o1.addItem(new MenuItem("Ayran", 10, 2));
            o1.addItem(new MenuItem("Salata", 30, 1));

            // Miktar guncelleme ve urun silme
            o1.updateQuantity("Ayran", 3);
            o1.removeItem("Salata");

            System.out.println("Siparis 1 ara toplam: " + o1.getSubtotal());

            // Kupon UYGULANAN siparis (ara toplam 190 >= 100)
            o1.applyCoupon(new Coupon("INDIRIM20", 20, 100));
            o1.checkout();

            // Kupon UYGULANMAYAN siparis (ara toplam 120 < 300)
            o2.addItem(new MenuItem("Kebap", 120, 1));
            o2.applyCoupon(new Coupon("BUYUK50", 50, 300));
            o2.checkout();

        } catch (ItemNotFoundException e) {
            System.out.println("Hata: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("Hata: " + e.getMessage());
        } catch (MinimumOrderException e) {
            System.out.println("Hata: " + e.getMessage());
        }

        // 1) ItemNotFoundException
        try {
            o3.removeItem("Hamburger");
        } catch (ItemNotFoundException e) {
            System.out.println("ItemNotFoundException yakalandi: " + e.getMessage());
        }

        // 2) InvalidQuantityException
        try {
            o3.addItem(new MenuItem("Corba", 25, 2));
            o3.updateQuantity("Corba", 0);
        } catch (ItemNotFoundException e) {
            System.out.println("ItemNotFoundException yakalandi: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("InvalidQuantityException yakalandi: " + e.getMessage());
        }

        // 3) MinimumOrderException (ara toplam 50 < 100)
        try {
            o3.checkout();
        } catch (MinimumOrderException e) {
            System.out.println("MinimumOrderException yakalandi: " + e.getMessage());
        }

        // Tamamlanmis siparis tekrar checkout edilemez
        try {
            o1.checkout();
        } catch (MinimumOrderException e) {
            System.out.println("Hata: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }

        // Static istatistikler sinif adiyla yazdirilir
        System.out.println("Tamamlanan siparis sayisi: " + Order.getCompletedOrders());
        System.out.println("Toplam gelir: " + Order.getTotalRevenue() + " TL");
    }
}
