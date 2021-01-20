
package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

    @RequestMapping(value = "/order/{cartId}", method = RequestMethod.GET)
    public String createOrder(@PathVariable("cartId") int cartId) {
        return "redirect:/checkout?cartId=" + cartId;
        // 跳转到webflowConfig.XML第29行
        // id=checkout 就可以解析/checkout
        // 之后就由spring webflow handle剩下的
    }
}
