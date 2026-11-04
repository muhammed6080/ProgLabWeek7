public class Coupon {
    private String code;
    private double discountRate;   // yuzde olarak (en fazla 50)
    private double minSubtotal;

    public Coupon(String code, double discountRate, double minSubtotal) {
        this.code = code;
        // Indirim orani en fazla %50 olabilir
        if (discountRate > 50) {
            this.discountRate = 50;
        } else if (discountRate < 0) {
            this.discountRate = 0;
        } else {
            this.discountRate = discountRate;
        }
        this.minSubtotal = minSubtotal;
    }

    public String getCode() {
        return code;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getMinSubtotal() {
        return minSubtotal;
    }
}
