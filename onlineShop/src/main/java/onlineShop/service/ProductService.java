
package onlineShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.ProductDao;
import onlineShop.entity.Product;

@Service
public class ProductService {
    // 这个service层没啥用，就是为了解耦合，也可以在这一层扩充功能，比如显示图片etc

    @Autowired
    private ProductDao productDao;

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    public void deleteProduct(int productId) {
        productDao.deleteProduct(productId);
    }

    public void addProduct(Product product){
        productDao.addProduct(product);
    }

    public void updateProduct(Product product){
        productDao.updateProduct(product);
    }
}
