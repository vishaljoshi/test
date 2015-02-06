package product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.libs.Json;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import product.dto.Product;
import product.dto.Error;
import product.service.ProductService;

@org.springframework.stereotype.Controller
public class ProductController extends Controller {


    @Autowired
    private ProductService productService = null;
    
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
