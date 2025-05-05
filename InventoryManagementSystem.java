
	
	package com.example.inventory;	

	import java.util.*;

	class Item {
	    private String name;
	    private String company;
	    private String description;
	    private int stockQuantity;
	    private String category;

	    public Item(String name, String company, String description, int stockQuantity, String category) {
	        this.name = name;
	        this.company = company;
	        this.description = description;
	        this.category = category;
	        setStockQuantity(stockQuantity);
	    }

	    public void setStockQuantity(int stockQuantity) {
	        if (stockQuantity < 0) {
	            throw new IllegalArgumentException("Stock quantity cannot be negative.");
	        }
	        this.stockQuantity = stockQuantity;
	    }

	    public void displayItem() {
	        System.out.println("Category: " + category);
	        System.out.println("Company: " + company);
	        System.out.println("Model: " + name);
	        System.out.println("Description: " + description);
	        System.out.println("Stock Quantity: " + stockQuantity);
	        System.out.println("-------------------------");
	    }

	    public int getStockQuantity() {
	        return stockQuantity;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getCompany() {
	        return company;
	    }

	    public String getCategory() {
	        return category;
	    }

	    public void updateStock(int quantityChange) {
	        if (quantityChange < 0) {
	            int soldQuantity = Math.abs(quantityChange);
	            if (soldQuantity > stockQuantity) {
	                System.out.println("Not enough stock available.");
	            } else {
	                setStockQuantity(stockQuantity - soldQuantity);
	                System.out.println("Stock updated successfully. " + soldQuantity + " items sold.");
	            }
	        } else {
	            setStockQuantity(stockQuantity + quantityChange);
	            System.out.println("Stock updated successfully. " + quantityChange + " items added.");
	        }
	    }
	}

	public class InventoryManagementSystem {

	    // Helper method for validated number input
	    private static int getValidatedInput(Scanner scanner, int min, int max, String prompt) {
	        int choice = -1;
	        while (true) {
	            System.out.print(prompt);
	            try {
	                choice = scanner.nextInt();
	                if (choice >= min && choice <= max) {
	                    break;
	                } else {
	                    System.out.println("Please enter a number between " + min + " and " + max + ".");
	                }
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter a valid number.");
	                scanner.next(); // Clear the invalid input
	            }
	        }
	        return choice;
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        ArrayList<Item> inventory = new ArrayList<>();

	        // Adding Items to Inventory with Categories
	        inventory.add(new Item("iPhone 14 Pro", "Apple", "256GB storage", 15, "Phone"));
	        inventory.add(new Item("Galaxy S23 Ultra", "Samsung", "512GB storage", 20, "Phone"));
	        inventory.add(new Item("OnePlus 11 5G", "OnePlus", "256GB storage", 18, "Phone"));
	        inventory.add(new Item("Xperia Pro", "Sony", "Professional smartphone", 10, "Phone"));

	        inventory.add(new Item("Gaming Laptop", "Dell", "High-performance gaming laptop", 10, "Laptop"));
	        inventory.add(new Item("Business Laptop", "HP", "Long battery life for professionals", 8, "Laptop"));

	        inventory.add(new Item("Noise-Canceling Headphones", "Sony", "Wireless headphones", 25, "Accessory"));
	        inventory.add(new Item("Fitness Tracker", "Garmin", "Heart rate monitor smartwatch", 22, "Accessory"));

	        while (true) {
	            // Group items by category
	            Map<String, List<Item>> categoryMap = new HashMap<>();
	            for (Item item : inventory) {
	                categoryMap.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
	            }

	            System.out.println("\nCategories Available:");
	            List<String> categories = new ArrayList<>(categoryMap.keySet());
	            for (int i = 0; i < categories.size(); i++) {
	                System.out.println((i + 1) + ". " + categories.get(i));
	            }

	            int categoryChoice = getValidatedInput(scanner, 0, categories.size(),
	                    "Enter the number of the category to view available companies (0 to exit): ");

	            if (categoryChoice == 0) {
	                System.out.println("Exiting...");
	                break;
	            }

	            String selectedCategory = categories.get(categoryChoice - 1);
	            List<Item> itemsInCategory = categoryMap.get(selectedCategory);

	            // Get unique companies
	            Map<String, List<Item>> companyMap = new LinkedHashMap<>();
	            for (Item item : itemsInCategory) {
	                companyMap.computeIfAbsent(item.getCompany(), k -> new ArrayList<>()).add(item);
	            }

	            System.out.println("\nAvailable Companies in " + selectedCategory + ":");
	            List<String> companies = new ArrayList<>(companyMap.keySet());
	            for (int i = 0; i < companies.size(); i++) {
	                System.out.println((i + 1) + ". " + companies.get(i));
	            }

	            int companyChoice = getValidatedInput(scanner, 0, companies.size(),
	                    "Enter the number of the company to view its items (0 to go back): ");

	            if (companyChoice == 0) {
	                continue;
	            }

	            String selectedCompany = companies.get(companyChoice - 1);
	            List<Item> companyItems = companyMap.get(selectedCompany);

	            System.out.println("\nItems from " + selectedCompany + ":");
	            for (int i = 0; i < companyItems.size(); i++) {
	                System.out.println((i + 1) + ". " + companyItems.get(i).getName());
	            }

	            int itemChoice = getValidatedInput(scanner, 0, companyItems.size(),
	                    "Enter the number of the item to view details (0 to go back): ");

	            if (itemChoice == 0) {
	                continue;
	            }

	            Item selectedItem = companyItems.get(itemChoice - 1);
	            selectedItem.displayItem();

	            System.out.println("Current Stock: " + selectedItem.getStockQuantity());

	            int quantityChange = 0;
	            while (true) {
	                System.out.print("Enter quantity to add/remove (negative for sold items, positive to add stock): ");
	                try {
	                    quantityChange = scanner.nextInt();
	                    break;
	                } catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Please enter a valid number.");
	                    scanner.next(); // clear buffer
	                }
	            }

	            selectedItem.updateStock(quantityChange);
	            System.out.println("Updated Stock: " + selectedItem.getStockQuantity());

	            System.out.println("Exiting after stock update...");
	            System.out.println("Thank you for visting our inventory");
	            break; // Exit after update
	        }

	        scanner.close();
	    }
	}
