package cTest;

import java.sql.*;
import java.util.Scanner;

public class Products {
    public void create(){
        System.out.println("Enter product name");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        System.out.println("Enter product price");
        int price = scanner.nextInt();

        System.out.println("Enter product status(out_of_stock, in_stock, running_low)");
        String status = scanner.next();

        try{
            Connection con = new SqlCon().getSqlConnection();

            String sql = "INSERT INTO products(`name`, `price`, `status`, `created_at`)" +
                    " VALUES (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.setString(3, status);
            stmt.setDate(4, new Date(System.currentTimeMillis()));

            stmt.execute();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void getAll(){
        try{

            Connection con = new SqlCon().getSqlConnection();
            Statement stmt = con.createStatement();
            ResultSet allProducts = stmt.executeQuery("select * from products");

            System.out.println("id | name | price | status");
            while(allProducts.next())
                System.out.println(allProducts.getInt(1)+" | " +
                    " "+allProducts.getString(2)+" | " +
                    " "+allProducts.getString(3)+" | "+
                    " "+allProducts.getString(4));


        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void getOrdered(){
        try{

            Connection con = new SqlCon().getSqlConnection();
            Statement stmt = con.createStatement();
            ResultSet allProducts = stmt.executeQuery("SELECT id, name, price, status, " +
                    "SUM(oi.quantity) as sum_of_quantity FROM products p\n" +
                    "right join order_items oi ON p.id = oi.product_id\n" +
                    "WHERE oi.quantity > 0\n" +
                    "GROUP BY p.id\n" +
                    "ORDER BY sum_of_quantity desc");

            System.out.println("id | name | price | status | sum of quantity");
            while(allProducts.next())
                System.out.println(allProducts.getInt(1)+" | " +
                        " "+allProducts.getString(2)+" | " +
                        " "+allProducts.getString(3)+" | "+
                        " "+allProducts.getString(4)+" | "+
                        " "+allProducts.getString(5));


        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void deleteProduct(){
        try{
            System.out.println("Enter the id of the product you want to remove");
            Connection con = new SqlCon().getSqlConnection();
            Scanner id_del = new Scanner(System.in);
            int id_from_user = id_del.nextInt();

            if (id_from_user != -1111) {
                String sql = "DELETE FROM products WHERE id = ?";

                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, id_from_user);
                stmt.execute();
            }else{
                Statement stmt = con.createStatement();
                stmt.executeUpdate("TRUNCATE TABLE products");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
