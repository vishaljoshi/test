package product.dao;

import java.util.List;

import product.dto.Product;


public interface Dao {
    public List<Product> findProduct(int id);
    
    public Product getProduct(int id);
    
    public boolean updateProduct(int id,String field,final Double  doublevalue,final String  Stringvalue );
}
