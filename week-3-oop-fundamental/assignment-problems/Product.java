public class Product {
    private String productId;
    private String productName;
    private double price;
    private String category;
    private int stockQuantity;

    public static int totalProducts = 0;
    public static String[] categories = {"Electronics", "Books", "Clothing", "Home"};

    public Product(String productId, String productName, double price, String category, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        totalProducts++;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int qty) { this.stockQuantity = qty; }

    public static Product findProductById(Product[] products, String productId) {
        for (Product p : products) {
            if (p != null && p.getProductId().equalsIgnoreCase(productId)) {
                return p;
            }
        }
        return null;
    }

    public static Product[] getProductsByCategory(Product[] products, String category) {
        int count = 0;
        for (Product p : products) {
            if (p != null && p.getCategory().equalsIgnoreCase(category)) {
                count++;
            }
        }
        Product[] result = new Product[count];
        int index = 0;
        for (Product p : products) {
            if (p != null && p.getCategory().equalsIgnoreCase(category)) {
                result[index++] = p;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - $%.2f - Category: %s - Stock: %d",
                productId, productName, price, category, stockQuantity);
    }
}
import java.util.Arrays;

public class ShoppingCart {
    private String cartId;
    private String customerName;
    private Product[] products;
    private int[] quantities;
    private double cartTotal;

    private int productCount;

    public ShoppingCart(String cartId, String customerName) {
        this.cartId = cartId;
        this.customerName = customerName;
        this.products = new Product[20];
        this.quantities = new int[20];
        this.cartTotal = 0.0;
        this.productCount = 0;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            System.out.println("Product does not exist.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }
        if (product.getStockQuantity() < quantity) {
            System.out.println("Insufficient stock for " + product.getProductName());
            return;
        }

        for (int i = 0; i < productCount; i++) {
            if (products[i].getProductId().equalsIgnoreCase(product.getProductId())) {
                quantities[i] += quantity;
                product.setStockQuantity(product.getStockQuantity() - quantity);
                System.out.println(quantity + " more added to " + product.getProductName());
                calculateTotal();
                return;
            }
        }

        products[productCount] = product;
        quantities[productCount] = quantity;
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productCount++;
        System.out.println(quantity + " " + product.getProductName() + "(s) added to cart.");
        calculateTotal();
    }

    public void removeProduct(String productId) {
        for (int i = 0; i < productCount; i++) {
            if (products[i].getProductId().equalsIgnoreCase(productId)) {
                Product removedProduct = products[i];
                int removedQty = quantities[i];
                removedProduct.setStockQuantity(removedProduct.getStockQuantity() + removedQty);

                for (int j = i; j < productCount - 1; j++) {
                    products[j] = products[j + 1];
                    quantities[j] = quantities[j + 1];
                }
                products[productCount - 1] = null;
                quantities[productCount - 1] = 0;
                productCount--;
                System.out.println(removedProduct.getProductName() + " removed from cart.");
                calculateTotal();
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public void calculateTotal() {
        cartTotal = 0;
        for (int i = 0; i < productCount; i++) {
            cartTotal += products[i].getPrice() * quantities[i];
        }
    }

    public void displayCart() {
        System.out.println("\nShopping Cart for " + customerName + ":");
        if (productCount == 0) {
            System.out.println("Cart is empty.");
            return;
        }
        for (int i = 0; i < productCount; i++) {
            System.out.printf("%s x%d - $%.2f each\n", products[i].getProductName(), quantities[i], products[i].getPrice());
        }
        System.out.printf("Total: $%.2f\n", cartTotal);
    }

    public void checkout() {
        if (productCount == 0) {
            System.out.println("Cart is empty, cannot checkout.");
            return;
        }
        System.out.println("Checkout complete for " + customerName + ". Total amount: $" + cartTotal);
        products = new Product[20];
        quantities = new int[20];
        productCount = 0;
        cartTotal = 0;
    }
}
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Product[] allProducts = new Product[10];
        allProducts[0] = new Product("P001", "Laptop", 1200.00, "Electronics", 5);
        allProducts[1] = new Product("P002", "Smartphone", 800.00, "Electronics", 10);
        allProducts[2] = new Product("P003", "Jeans", 50.00, "Clothing", 20);
        allProducts[3] = new Product("P004", "T-Shirt", 20.00, "Clothing", 30);
        allProducts[4] = new Product("P005", "Cookbook", 25.00, "Books", 15);
        allProducts[5] = new Product("P006", "Novel", 15.00, "Books", 10);
        allProducts[6] = new Product("P007", "Microwave", 150.00, "Home", 7);
        allProducts[7] = new Product("P008", "Blender", 80.00, "Home", 8);
        allProducts[8] = new Product("P009", "Headphones", 100.00, "Electronics", 12);
        allProducts[9] = new Product("P010", "Jacket", 120.00, "Clothing", 5);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        ShoppingCart cart = new ShoppingCart("CART001", customerName);

        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Browse all products");
            System.out.println("2. Browse products by category");
            System.out.println("3. Add product to cart");
            System.out.println("4. Remove product from cart");
            System.out.println("5. View cart");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } else {
                scanner.nextLine(); // consume invalid input
            }

            switch (choice) {
                case 1:
                    System.out.println("\nAll Products:");
                    for (Product p : allProducts) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Enter category (Electronics, Books, Clothing, Home): ");
                    String cat = scanner.nextLine();
                    Product[] filtered = Product.getProductsByCategory(allProducts, cat);
                    if (filtered.length == 0) {
                        System.out.println("No products found in this category.");
                    } else {
                        System.out.println("Products in " + cat + ":");
                        for (Product p : filtered) {
                            System.out.println(p);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter Product ID to add: ");
                    String pidAdd = scanner.nextLine();
                    Product productToAdd = Product.findProductById(allProducts, pidAdd);
                    if (productToAdd == null) {
                        System.out.println("Product not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int qtyAdd = scanner.nextInt();
                    scanner.nextLine();
                    cart.addProduct(productToAdd, qtyAdd);
                    break;
                case 4:
                    System.out.print("Enter Product ID to remove from cart: ");
                    String pidRemove = scanner.nextLine();
                    cart.removeProduct(pidRemove);
                    break;
                case 5:
                    cart.displayCart();
                    break;
                case 6:
                    cart.checkout();
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting. Thank you for shopping!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}