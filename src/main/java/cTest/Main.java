package cTest;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner end_scanner = new Scanner(System.in);
        String answer;

        do {
            System.out.print("What would you like to do? (Insert number) \n" +
                    "1.Create product \n" +
                    "2.Create order \n" +
                    "3.Show products list \n" +
                    "4.List of ordered goods \n" +
                    "5.Orders list \n" +
                    "6.Show orders list \n" +
                    "7.Removing a product \n");
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();

            switch (number){
                case 1:
                    new Products().create();
                    break;
                case 2:
                    new Orders().create();
                    break;
                case 3:
                    new Products().getAll();
                    break;
                case 4:
                    new Products().getOrdered();
                    break;
                case 5:
                    new Orders().ordersData();
                    break;
                case 6:
                    new Orders().getAll();
                    break;
                case 7:
                    new Products().deleteProduct();
                    break;
            }

            System.out.println("Do you want to continue? (y / n)");
            answer = end_scanner.next();
        }while (!answer.equals("n"));

    }


}
