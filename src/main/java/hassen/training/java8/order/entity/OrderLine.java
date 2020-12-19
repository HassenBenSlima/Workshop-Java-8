package hassen.training.java8.order.entity;

public class OrderLine {

    private Product product;

    private int count;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static boolean isSpecialOffer(OrderLine orderLine) {

        return  true;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
