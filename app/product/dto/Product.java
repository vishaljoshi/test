package product.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class Product {
    @Id
    private ObjectId  _id;
    private long id;
    private String title;
    private Pricing pricing ;
    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Pricing getPricing() {
        return pricing;
    }
    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
   
    
    /*
     * { "id" : 9650, "pricing" : { "cost" : 1.22, "price" : 1.5, "promo_price"
     * : 0, "savings" : 10, "on_sale" : 0 }, "title" :
     * "Calbee Hot & Spicy Potato Chips" }
     */
}
