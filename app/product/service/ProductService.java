/**
 * Project:test
 * File:product.service.ProductService.java
 * 
 * History:
 * ----------------------------------------------------------------------------------------------------
 * Author                   | Date                |        Description                                |
 * ----------------------------------------------------------------------------------------------------
 *  Vishal Joshi            |Feb 06, 2015         | Creation                                          |
 * ----------------------------------------------------------------------------------------------------
 */
package product.service;

import java.util.List;

import play.libs.F.Promise;
import product.dto.Product;

public interface ProductService {
    
    public  Promise<List<Product>> findProduct(int id);
    
    public  Promise<Product> getProduct(int id);
    
    public  Promise<Boolean> updateProduct(final int id,final String field,final String  value  );
    
}
