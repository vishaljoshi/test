/**
 * Project:test
 * File:product.dao.DaoImpl.java
 * 
 * History:
 * ----------------------------------------------------------------------------------------------------
 * Author                   | Date                |        Description                                |
 * ----------------------------------------------------------------------------------------------------
 *  Vishal Joshi            |Feb 06, 2015         | Creation                                          |
 * ----------------------------------------------------------------------------------------------------
 */
package product.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mongodb.WriteResult;

import product.dto.Product;
@Repository("DaoImpl")
public class DaoImpl implements Dao{
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /**
     * searching based on product Id
     * 
     */
    @Override
    public List<Product> findProduct(int id){
        
        BasicQuery searchQuery = new BasicQuery("{$where:'/"+id+"/.test(this.id)'}");
        searchQuery.limit(10);
        
        List<Product> result = mongoTemplate.find(searchQuery, Product.class);
        
         return result;       
    }
    
    /**
     * get the product  based on product Id
     * 
     */
    public Product getProduct(int id){
        BasicQuery searchQuery = new BasicQuery("{id:"+id+"}");
       
        Product result = mongoTemplate.findOne(searchQuery, Product.class);
       
       
         return result;
    }
    
    /**
     * updating product fields based on product Id
     * 
     */
    @Override
    public boolean updateProduct(int id,String field,final Double  doublevalue,final String  Stringvalue ){
        
        if(!StringUtils.isEmpty(Stringvalue)){
        WriteResult wr =mongoTemplate.updateFirst(query(where("id").is(id)), update(field, Stringvalue), Product.class);
        System.out.println(wr);
        }else{
            WriteResult wr =mongoTemplate.updateFirst(query(where("id").is(id)), update(field, doublevalue), Product.class);
            System.out.println(wr);
        }
        return true;
        
    }
}
