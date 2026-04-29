package food;

import java.sql.*;
import java.util.Scanner;

public class FoodOrderApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DB.getCon();

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            while(true) {
                System.out.println("\n1. View Food");
                System.out.println("2. Place Order");
                System.out.println("3. Exit");
                System.out.print("Choose: ");

                int ch = sc.nextInt();

                if(ch == 1) {
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM food");

                    System.out.println("\nMenu:");
                    while(rs.next()) {
                        System.out.println(
                            rs.getInt("id") + " " +
                            rs.getString("name") + " ₹" +
                            rs.getDouble("price")
                        );
                    }
                }

                else if(ch == 2) {
                    System.out.print("Enter Food ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    PreparedStatement ps1 = con.prepareStatement(
                        "SELECT price FROM food WHERE id=?"
                    );
                    ps1.setInt(1, id);

                    ResultSet rs = ps1.executeQuery();

                    double price = 0;

                    if(rs.next()) {
                        price = rs.getDouble("price");
                    } else {
                        System.out.println("Invalid Food ID!");
                        continue;
                    }

                    double total = price * qty;

                    PreparedStatement ps2 = con.prepareStatement(
                        "INSERT INTO orders(food_id, quantity, total) VALUES(?,?,?)"
                    );
                    ps2.setInt(1, id);
                    ps2.setInt(2, qty);
                    ps2.setDouble(3, total);

                    ps2.executeUpdate();

                    System.out.println("Order placed! Total = ₹" + total);
                }

                else if(ch == 3) {
                    System.out.println("Thank you!");
                    break;
                }

                else {
                    System.out.println("Invalid choice");
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}