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
    
    @Override
    public List<Product> findProduct(int id){
        
        BasicQuery searchQuery = new BasicQuery("{$where:'/"+id+"/.test(this.id)'}");
        searchQuery.limit(10);
        System.out.println("id="+id);
        List<Product> result = mongoTemplate.find(searchQuery, Product.class);
        System.out.println("result"+result.size());
        System.out.println("result"+result);
         return result;       
    }
    public Product getProduct(int id){
        BasicQuery searchQuery = new BasicQuery("{id:"+id+"}");
        System.out.println("id="+id);
        Product result = mongoTemplate.findOne(searchQuery, Product.class);
       
        System.out.println("result"+result);
         return result;
    }
    
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
