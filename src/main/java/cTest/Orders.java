package cTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Orders {
    public void create(){
        int a = 0;
        int b = 999999;
        System.out.println("List of goods:");
        new Products().getAll();

        System.out.println("Enter the id of the product(s) you want to buy (enter 0 to complete)");
        Scanner scanner = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<Integer>();
        while(scanner.hasNext()){
            int id = scanner.nextInt();
            if(id == 0){
                break;
            }
            numbers.add(id);
        }

        int user_id = a + (int) (Math.random() * b);

        try{
            Connection con = new SqlCon().getSqlConnection();

            String sql = "INSERT INTO orders(`user_id`, `status`, `created_at`) VALUES (?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, user_id);
            stmt.setString(2, "new");
            stmt.setDate(3, new Date(System.currentTimeMillis()));

            stmt.execute();

            ResultSet lastInserted = stmt.getGeneratedKeys();

            if (lastInserted.next()){
                int lastInsertedID = lastInserted.getInt(1);
                for(Integer number:numbers){
                    this.fillOrderItems(lastInsertedID, number);
                    System.out.println( "enter quantity for product " + number + ":");
                    int quantity = scanner.nextInt();
                    updateQuantity(quantity, lastInsertedID, number);
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }

   public void fillOrderItems(int order_id, int product_id){
       try{
           Connection con = new SqlCon().getSqlConnection();

           String sql = "INSERT INTO order_items(`order_id`, `product_id`) VALUES (?,?)";

           PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
           stmt.setInt(1, order_id);
           stmt.setInt(2, product_id);

           stmt.execute();
       }catch(Exception e){
           System.out.println(e);
       }
   }

   public void updateQuantity(int quantity, int lastInsertedID, int number){
       try{
           Connection con = new SqlCon().getSqlConnection();

           String sql = "UPDATE order_items " +
                   "SET `quantity` = ? " +
                   "WHERE `order_id` = ? AND `product_id` = ? ";

           PreparedStatement stmt = con.prepareStatement(sql);
           stmt.setInt(1, quantity);
           stmt.setInt(2, lastInsertedID);
           stmt.setInt(3, number);

           stmt.execute();
       }catch(Exception e){
           System.out.println(e);
       }
   }

   public void ordersData(){
       try{

           Connection con = new SqlCon().getSqlConnection();
           Statement stmt = con.createStatement();
           ResultSet allProducts = stmt.executeQuery("SELECT o.id, SUM(p.price)*oi.quantity as full_price, " +
                   "p.name,oi.quantity, o.created_at FROM orders o\n" +
                   "JOIN order_items oi on o.id = oi.order_id\n" +
                   "JOIN products p on oi.product_id = p.id\n" +
                   "GROUP BY oi.order_id, oi.product_id");

           System.out.println("Order ID| Products total Price | Product Name |" +
                   "Products Quantity| Order Created Date ");
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

    public void getAll(){
        try{

            Connection con = new SqlCon().getSqlConnection();
            Statement stmt = con.createStatement();
            ResultSet allOrder = stmt.executeQuery("select * from orders");

            System.out.println("id | user id | status | created at");
            while(allOrder.next())
                System.out.println(allOrder.getInt(1)+" | " +
                        " "+allOrder.getString(2)+" | " +
                        " "+allOrder.getString(3)+" | "+
                        " "+allOrder.getString(4));


        }catch(Exception e){
            System.out.println(e);
        }
    }
}
