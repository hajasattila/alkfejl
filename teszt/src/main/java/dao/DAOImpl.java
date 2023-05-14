package dao;

import config.ConfigurationHelper;
import model.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO{
    public List<Order> getOrders() {
         List<Order> result = new ArrayList<>();
         String path = ConfigurationHelper.getValue("db.url"); // obtaining DB URL

                      try(Connection c = DriverManager.getConnection(path);
                                 Statement stmt = c.createStatement();
                                 ResultSet rs = stmt.executeQuery("SELECT * FROM asd")
                             ){

                                 while(rs.next()) {
                                     result.add(new Order(
                                     rs.getInt("ID"),
                                     rs.getString("customer"),
                                     rs.getString("title"),
                                     rs.getInt("portion"),
                                     LocalDate.parse(rs.getString("delivery")),
                                     rs.getString("payment")));
                                 }

                             } catch (SQLException throwables) {
                                 throwables.printStackTrace();
                             }
                             return result;
                         }

    @Override
    public Order save(Order order) {
        String path = ConfigurationHelper.getValue("db.url"); // obtaining DB URL
        String sqlcommand = "INSERT INTO test(customer, title, portion, delivery, payment  VALUES (?,?,?,?,?)";
        try(Connection c = DriverManager.getConnection(path);
            PreparedStatement stmt = c.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, order.getCustomer());
            stmt.setString(2, order.getTitle());
            stmt.setInt(3, order.getPortion());
            stmt.setString(4, order.getDelivery().toString());
            stmt.setString(5, order.getPayment());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return null;
            }

                ResultSet genKeys = stmt.getGeneratedKeys();
                if(genKeys.next()){
                    order.setID(genKeys.getInt(1));
                }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
          
        return order;
    }
}
