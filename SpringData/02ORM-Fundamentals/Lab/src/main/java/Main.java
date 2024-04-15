import entities.Order;
import entities.Product;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector.createConnection("username", "password", "mini_orm");
        Connection connection = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
//        userEntityManager.doCreate(User.class);
        User pesho = new User("Martin", 35, LocalDate.now());
//        pesho.setId(1);
        userEntityManager.persist(pesho);
//
        User users = userEntityManager.findFirst(User.class);

        System.out.println(users);

//        EntityManager<Product> productEntityManager = new EntityManager<>(connection);
//        Product pen = new Product("pen", 2.34);
//        productEntityManager.persist(pen);

        EntityManager<Order> orderEntityManager = new EntityManager<>(connection);
//        orderEntityManager.doCreate(Order.class);
        Order order = new Order("15423m8", LocalDate.now());
        orderEntityManager.persist(order);
    }
}
