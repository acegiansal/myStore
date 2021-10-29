/* Giancarlo Salvador #101139903*/
package store;

import java.util.Objects;

/**
 * Class meant to represent a specific product
 * @author Giancarlo Salvador #101139903
 */
public class Product {
    private final String name;
    private final String id;
    private final double price;

    /**
     * Only constructor
     * @param name String for the name of the product
     * @param id String for the id of the product
     * @param price double for the price of the product
     */
    public Product(String name, String id, double price){
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     * Default constructor meant to show a "null" product
     */
    public Product(){
        id = "NULL";
        name = "NULL";
        price = 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, price);
    }

    /**
     * Getter method for the name of the product
     * @return String for the name of the product
     */
    public String getName(){ return name; }

    /**
     * Getter method for the id
     * @return String for the id of the product
     */
    public String getId(){ return id; }

    /**
     * Getter method for the price
     * @return double for the price of the product
     */
    public double getPrice(){ return price; }

    /**
     * Returns a string representation of the product
     * @return String containing name, id, price of product
     */
    public String toString(){
        return "assets.Product: " + name + ", id: " + id + ", Price: " + String.valueOf(price);
    }

}
