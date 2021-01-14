package onlineShop.controller;

import onlineShop.entity.Product;
import onlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ModelAndView("productList", "products", products);
    }

    @RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)
    public ModelAndView getProductById(@PathVariable(value = "productId") int productId) {
        Product product = productService.getProductById(productId);
        return new ModelAndView("productPage", "product", product);
    }

    // create empty form, new Product()是个空object， 为了bind表上的数据到Product的class
    // 这个是由navbar.JSP的33行触发的。就是click addProduct button。只有admin才能看到这个button
    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)
    public ModelAndView getProductForm() {
        return new ModelAndView("addProduct", "productForm", new Product());
        // 最后product是为了自动查看能否bind到Product object

    }

    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST) // addProduct.jsp的26行
    public String addProduct(@ModelAttribute Product product, BindingResult result) {
        // 前端modelAttribute=productForm，就可以直接可通过@ModelAttribute把form convert成Product object作为参数
        // BindingResult是存bind成Product class有没有成功
        if (result.hasErrors()) {
            return "addProduct"; // 没成功的话就回到addProduct这个addProduct.jsp这个view上
        }
        productService.addProduct(product);
        return "redirect:/getAllProducts"; // 添加完，redirect到product list
    }

    @RequestMapping(value = "/admin/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") int productId) {
        productService.deleteProduct(productId);
        return "redirect:/getAllProducts";
    }

    @RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
        Product product = productService.getProductById(productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editProduct"); // link modelAndView到editProduct.JSP这个view
        modelAndView.addObject("editProductObj", product); // supply第一个参数
        modelAndView.addObject("productId", productId); // supply第二个参数

        return modelAndView;
    }

    @RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute Product product,
                              @PathVariable(value = "productId") int productId) {
        product.setId(productId);
        productService.updateProduct(product);
        return "redirect:/getAllProducts";
    }
}
