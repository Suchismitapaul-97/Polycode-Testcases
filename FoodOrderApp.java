import java.util.*;
import java.time.LocalDateTime;

// Main application
public class FoodOrderApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderService orderService = new OrderService();
        User user = new User("U1", "Alice", "123 Main Street");

        // Sample restaurants and menu
        Restaurant rest1 = new Restaurant("R1", "Pizza Palace");
        rest1.addMenuItem(new MenuItem("I1", "Margherita", 250));
        rest1.addMenuItem(new MenuItem("I2", "Farmhouse", 300));
        orderService.addRestaurant(rest1);

        Restaurant rest2 = new Restaurant("R2", "Burger Hub");
        rest2.addMenuItem(new MenuItem("I3", "Cheese Burger", 150));
        rest2.addMenuItem(new MenuItem("I4", "Veggie Burger", 130));
        orderService.addRestaurant(rest2);

        while (true) {
            System.out.println("\n--- Food Order System ---");
            System.out.println("1. View Restaurants");
            System.out.println("2. Place Order");
            System.out.println("3. View My Orders");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    orderService.viewRestaurants();
                    break;
                case 2:
                    orderService.viewRestaurants();
                    System.out.print("Enter Restaurant ID: ");
                    String restId = scanner.nextLine();
                    Restaurant selectedRest = orderService.getRestaurantById(restId);
                    if (selectedRest == null) {
                        System.out.println("Invalid restaurant ID.");
                        break;
                    }

                    selectedRest.printMenu();
                    System.out.print("Enter comma-separated item IDs to order: ");
                    String[] itemIds = scanner.nextLine().split(",");
                    List<MenuItem> selectedItems = new ArrayList<>();
                    for (String id : itemIds) {
                        MenuItem item = selectedRest.getMenuItemById(id.trim());
                        if (item != null) {
                            selectedItems.add(item);
                        } else {
                            System.out.println("Invalid item ID: " + id);
                        }
                    }

                    String orderId = orderService.placeOrder(user, selectedRest, selectedItems);
                    System.out.println("Order placed successfully! Order ID: " + orderId);
                    break;

                case 3:
                    List<Order> orders = orderService.getOrdersByUser(user.getUserId());
                    if (orders.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        for (Order order : orders) {
                            System.out.println(order);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}

// Models
class User {
    private String userId;
    private String name;
    private String address;

    public User(String userId, String name, String address) {
        this.userId = userId;
        this.name = name;
        this.address = address;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
}

class Restaurant {
    private String id;
    private String name;
    private List<MenuItem> menu = new ArrayList<>();

    public Restaurant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void printMenu() {
        System.out.println("Menu of " + name + ":");
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }

    public MenuItem getMenuItemById(String id) {
        for (MenuItem item : menu) {
            if (item.getItemId().equalsIgnoreCase(id)) return item;
        }
        return null;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<MenuItem> getMenu() { return menu; }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

class MenuItem {
    private String itemId;
    private String name;
    private double price;

    public MenuItem(String itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return itemId + ": " + name + " - ₹" + price;
    }
}

enum OrderStatus {
    PLACED, DELIVERED
}

class Order {
    private String orderId;
    private User user;
    private Restaurant restaurant;
    private List<MenuItem> items;
    private OrderStatus status;
    private LocalDateTime orderTime;

    public Order(String orderId, User user, Restaurant restaurant, List<MenuItem> items) {
        this.orderId = orderId;
        this.user = user;
        this.restaurant = restaurant;
        this.items = items;
        this.status = OrderStatus.PLACED;
        this.orderTime = LocalDateTime.now();
    }

    public String getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
               "\nRestaurant: " + restaurant.getName() +
               "\nItems: " + items +
               "\nStatus: " + status +
               "\nTime: " + orderTime + "\n";
    }
}

// Service
class OrderService {
    private Map<String, Restaurant> restaurantMap = new HashMap<>();
    private Map<String, List<Order>> userOrders = new HashMap<>();
    private int orderCounter = 1;

    public void addRestaurant(Restaurant restaurant) {
        restaurantMap.put(restaurant.getId(), restaurant);
    }

    public void viewRestaurants() {
        System.out.println("Available Restaurants:");
        for (Restaurant r : restaurantMap.values()) {
            System.out.println(r);
        }
    }

    public Restaurant getRestaurantById(String id) {
        return restaurantMap.get(id);
    }

    public String placeOrder(User user, Restaurant restaurant, List<MenuItem> items) {
        String orderId = "ORD" + orderCounter++;
        Order order = new Order(orderId, user, restaurant, items);
        userOrders.computeIfAbsent(user.getUserId(), k -> new ArrayList<>()).add(order);
        return orderId;
    }

    public List<Order> getOrdersByUser(String userId) {
        return userOrders.getOrDefault(userId, new ArrayList<>());
    }
}
