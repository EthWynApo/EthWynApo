package com.mycompany.exec2p8;

import java.util.Scanner;

class Product {
private String code;
private String name;
private double price;
private int stock;

public Product(String code, String name, double price, int stock) {
    this.code = code;
    this.name = name;
    this.price = price;
    this.stock = stock;
}

public String getCode() {
    return code;
}

public String getName() {
    return name;
}

public double getPrice() {
    return price;
}

public int getStock() {
    return stock;
}

public boolean reduceStock(int quantity) {
    if (quantity > 0 && quantity <= stock) {
        stock = stock - quantity;
        return true;
    }
    return false;
}

}

class CartItem {
private Product product;
private int quantity;

public CartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
}

public Product getProduct() {
    return product;
}

public int getQuantity() {
    return quantity;
}

public void addQuantity(int quantity) {
    this.quantity = this.quantity + quantity;
}

public double getLineTotal() {
    return product.getPrice() * quantity;
}

}

class ShoppingCart {
private CartItem[] items;
private int itemCount;

public ShoppingCart(int size) {
    items = new CartItem[size];
    itemCount = 0;
}

public boolean addItem(Product product, int quantity) {
    if (product == null || quantity <= 0) {
        return false;
    }

    if (quantity > product.getStock()) {
        return false;
    }

    for (int i = 0; i < itemCount; i++) {
        if (items[i].getProduct().getCode().equals(product.getCode())) {
            product.reduceStock(quantity);
            items[i].addQuantity(quantity);
            return true;
        }
    }

    if (itemCount < items.length) {
        product.reduceStock(quantity);
        items[itemCount] = new CartItem(product, quantity);
        itemCount++;
        return true;
    }

    return false;
}

public double getSubtotal() {
    double subtotal = 0;

    for (int i = 0; i < itemCount; i++) {
        subtotal = subtotal + items[i].getLineTotal();
    }

    return subtotal;
}

public double getDiscount() {
    double subtotal = getSubtotal();

    if (subtotal >= 5000) {
        return subtotal * 0.10;
    } else if (subtotal >= 2000) {
        return subtotal * 0.05;
    }

    return 0;
}

public double getVAT() {
    double amountAfterDiscount = getSubtotal() - getDiscount();
    return amountAfterDiscount * 0.12;
}

public double getFinalTotal() {
    double discountedAmount = getSubtotal() - getDiscount();
    return discountedAmount + getVAT();
}

public void displayCart() {
    for (int i = 0; i < itemCount; i++) {
        System.out.println(
            items[i].getProduct().getName()
            + " x " + items[i].getQuantity()
            + " = PHP " + items[i].getLineTotal()
        );
    }
}

public int getItemCount() {
    return itemCount;
}

}

public class Exec2p8 {

public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    System.out.println("SHOPPING CART AND CHECKOUT SYSTEM");

    System.out.print("Enter number of products: ");
    int productCount = input.nextInt();
    input.nextLine();

    Product[] products = new Product[productCount];

    for (int i = 0; i < productCount; i++) {

        System.out.println();
        System.out.println("Product " + (i + 1));

        System.out.print("Enter product code: ");
        String code = input.nextLine();

        System.out.print("Enter product name: ");
        String name = input.nextLine();

        System.out.print("Enter price: ");
        double price = input.nextDouble();

        System.out.print("Enter stock: ");
        int stock = input.nextInt();
        input.nextLine();

        products[i] = new Product(code, name, price, stock);
    }

    ShoppingCart cart = new ShoppingCart(productCount);

    System.out.println();

    System.out.print("Enter number of add-to-cart actions: ");
    int actionCount = input.nextInt();
    input.nextLine();

    for (int i = 0; i < actionCount; i++) {

        System.out.println();
        System.out.println("Add to Cart " + (i + 1));

        System.out.print("Enter product code: ");
        String code = input.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();
        input.nextLine();

        Product selectedProduct = null;

        for (int j = 0; j < products.length; j++) {

            if (products[j].getCode().equals(code)) {
                selectedProduct = products[j];
                break;
            }
        }

        if (selectedProduct == null) {
            System.out.println("Addition rejected: Product not found.");
        } else if (quantity <= 0) {
            System.out.println("Addition rejected: Quantity must be greater than 0.");
        } else if (quantity > selectedProduct.getStock()) {
            System.out.println("Addition rejected: Not enough stock.");
        } else if (cart.addItem(selectedProduct, quantity)) {
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Addition rejected.");
        }
    }

    System.out.println();
    System.out.println("=== RECEIPT ===");

    if (cart.getItemCount() == 0) {
        System.out.println("Cart is empty.");
    } else {
        cart.displayCart();
    }

    double subtotal = cart.getSubtotal();
    double discount = cart.getDiscount();
    double vat = cart.getVAT();
    double finalTotal = cart.getFinalTotal();

    System.out.println();
    System.out.println("Subtotal: PHP " + subtotal);
    System.out.println("Discount: PHP " + discount);
    System.out.println("VAT: PHP " + vat);
    System.out.println("Final Total: PHP " + finalTotal);

    System.out.println();
    System.out.println("=== REMAINING STOCK ===");

    for (int i = 0; i < products.length; i++) {

        System.out.println(
            products[i].getCode()
            + " - " + products[i].getName()
            + " - Stock: " + products[i].getStock()
        );
    }

    input.close();
}

}
