package by.kustarev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBCClient {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/shop";
        String user = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("База данных успешно подключена!");

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from `order`");
            while (rs.next())
                System.out.printf("id: %d, product_id: %d, count: %d\n",
                        rs.getLong(1), rs.getLong(2), rs.getLong(3));
            rs.close();
            statement.close();

            System.out.println("\n\n\n");

            PreparedStatement prepareStatement = conn.prepareStatement("select * from `order` where id > ?");
            prepareStatement.setInt(1, 2);
            ResultSet prepRS = prepareStatement.executeQuery();
            while (prepRS.next())
                System.out.printf("id: %d, product_id: %d, count: %d\n",
                        prepRS.getLong(1), prepRS.getLong(2), prepRS.getLong(3));
            prepRS.close();
            prepareStatement.close();
        } catch (SQLException e) {
            System.out.println("База данных подключена не успешно!");
            e.printStackTrace();
        }
    }

}
