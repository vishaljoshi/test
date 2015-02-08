/**
 * Project:test
 * File:product.controller.ProductController.java
 * 
 * History:
 * ----------------------------------------------------------------------------------------------------
 * Author                   | Date                |        Description                                |
 * ----------------------------------------------------------------------------------------------------
 *  Vishal Joshi            |Feb 06, 2015         | Creation                                          |
 * ----------------------------------------------------------------------------------------------------
 */
package product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import product.dto.Error;
import product.dto.Product;
import product.service.ProductService;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Vishal Joshi
 *
 */
@org.springframework.stereotype.Controller
public class ProductController extends Controller {


    @Autowired
    private ProductService productService = null;
    
    /**
     * Description: searching the product based on partial Id's
     * @param id
     * @return
     */
    public   Promise<Result> findProduct(Integer id) {
        Promise<Result> resultPromise = null;  
        resultPromise = productService.findProduct(id).map(new Function<List<Product>, Result>() {
            @Override
            public Result apply(List<Product> product) throws Throwable {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString =null;
                if(product==null || product.size()==0){
                    Error er = new Error("No data found", "110");
                    jsonString=mapper.writeValueAsString(er );
                }else{
                    jsonString=mapper.writeValueAsString(product );
                }
                
                //Json.toJson(this)
                return ok(jsonString);
            }
        });
        return resultPromise;
    }

    
    /**
     * Description: this method gets the product details based on product id
     * @param id
     * @return
     */
    public  Promise<Result> getProduct(int id) {
        Promise<Result> resultPromise = null;  
        resultPromise = productService.getProduct(id).map(new Function<Product, Result>() {
            @Override
            public Result apply(Product product) throws Throwable {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString =null;
                if(product==null){
                    Error er = new Error("No data found", "110");
                    jsonString=mapper.writeValueAsString(er );
                }else{
                    jsonString=mapper.writeValueAsString(product );
                }
               
                //Json.toJson(this)
                return ok(jsonString);
            }
        });
        return resultPromise;
    }
    
    

    /**
     * Description: updates the product fields , its a post request
     * @return
     */
    public  Promise<Result> updateProduct() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        final int id = Integer.parseInt(values.get("id")[0]);
        final String field = values.get("field")[0].toString();       
        final String value = values.get("value")[0].toString();
        Promise<Result> resultPromise = null;
        resultPromise = productService.updateProduct(id, field, value).map(new Function<Boolean, Result>() {
            @Override
            public Result apply(Boolean updated) throws Throwable {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(updated);
                //Json.toJson(this)
                return ok(jsonString);
            }
        });
        return resultPromise;
    }

}
