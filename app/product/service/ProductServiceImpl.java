package product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import play.libs.F.Function0;
import play.libs.F.Promise;
import product.dao.Dao;
import product.dto.Product;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private Dao dao;
    @Override
    public Promise<List<Product>> findProduct(final int id) {
       
      
        Promise<List<Product>> promiseResult = Promise.promise(new Function0<List<Product>>() {
            @Override
            public List<Product> apply() {
                return   dao.findProduct(id);
            }
        });
        return promiseResult;
    }

    @Override
    public Promise<Product> getProduct(final int id) {

        Promise<Product> promiseResult = Promise.promise(new Function0<Product>() {
            @Override
            public Product apply() {
                return   dao.getProduct(id);
            }
        });
        return promiseResult;
    }

    @Override
    public  Promise<Boolean> updateProduct(final int id,final String field,final String  value ){
        Promise<Boolean> promiseResult = Promise.promise(new Function0<Boolean>() {
            @Override
            public Boolean apply() {
                try{
                if("price".equals(field)){                    
                    return   dao.updateProduct(id, field, Double.parseDouble(value),null);
                }else{
                    return   dao.updateProduct(id, field, null,value);
                }
                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }
               
            }
        });
        return promiseResult;
    }

}
