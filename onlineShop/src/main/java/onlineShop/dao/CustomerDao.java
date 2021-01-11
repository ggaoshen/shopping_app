package onlineShop.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Authorities;
import onlineShop.entity.Customer;
import onlineShop.entity.User;

@Repository // 这个是个特殊的@Component，specific to db操作
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addCustomer(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmailId(customer.getUser().getEmailId());
        Session session = null;

        try { // transaction db 操作，如果其中任何一个中间操作出错，整个transaciton就回到初始状态
            session = sessionFactory.openSession();
            session.beginTransaction(); //打开transaction
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit(); // commit transaction
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback(); // 出错了就回滚
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Customer getCustomerByUserName(String userName) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            // session这一系列都是Hibernate的功能

            Criteria criteria = session.createCriteria(User.class); // 这个相当于sql的where clasuse
            user = (User) criteria.add(Restrictions.eq("emailId", userName)).uniqueResult();
            // 选取和userName的user
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null)
            return user.getCustomer();
        return null;
    }
}
