package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String sayIndex() { // 返回string代表view name
        return "index";
    }

    @RequestMapping("/login")
    // 可能有以下可能性
    // -> /login
    // -> /login?error
    // -> /login?logout
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        // ModelAndView的作用是create一个object，field是一个view和一个model，返回给DispatcherServlet
        // 来找view和model，glue together，server-side render然后发送给browser
        // setViewName告诉他去找login.JSP作为view的模版

        // @RequestParam(value = "error", required = false)
        // required=FALSE 就是error key不一定存在，因为正常login没有这个parameter

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        if (error != null) {
            modelAndView.addObject("error", "Invalid username and Password");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "You have logged out successfully");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public String sayAbout() {
        return "aboutUs";
    }
}
