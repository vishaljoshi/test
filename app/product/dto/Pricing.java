package product.dto;

public class Pricing {

    private Double cost;
    private Double price;
    private Double promoPrice;
    private Double savings;
    private Double onsale;
    public Double getCost() {
        return cost;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPromoPrice() {
        return promoPrice;
    }
    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }
    public Double getSavings() {
        return savings;
    }
    public void setSavings(Double savings) {
        this.savings = savings;
    }
    public Double getOnsale() {
        return onsale;
    }
    public void setOnsale(Double onsale) {
        this.onsale = onsale;
    }    
}
