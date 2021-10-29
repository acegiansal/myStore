/* Giancarlo Salvador #101139903 */
package store;

import java.util.Objects;

/**
 * Class meant to represent the info that is often used by the storeManager
 * ShoppingCart, Inventory, Storemanager frequently use:
 * <ul>
 *     <li>assets.Product ID</li>
 *     <li>Stock of assets.Product</li>
 *     <li>assets.Product object itself</li>
 * </ul>
 * Thus it may be more simple to create a dedicated object in order to store this information
 * @author Giancarlo Salvador #101139903
 */
public class ProductInfo {
    /**
     * String for the ID of the product
     */
    private String id;

    /**
     * int for the amount of stock of the product
     */
    private int stock;

    /**
     * The product object itself that the previous information is about
     */
    private Product product;

    /**
     * Default constructor
     * Sets id to null
     */
    public ProductInfo(){
        id = null;
    }

    /**
     * Alternate constructor
     * @param id String for the ID of the object
     * @param stock
     * @param product
     */
    public ProductInfo(String id, int stock, Product product) {
        this.id = id;
        this.stock = stock;
        this.product = product;
    }

    @Override
    /**
     * Overrides equals method
     * Decides if 2 products are the same by looking at stock, id and actual product
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return stock == that.stock && Objects.equals(id, that.id) && Objects.equals(product, that.product);
    }

    @Override
    /**
     * overrides hashcode method
     * Provides a hash code for the id, stock and product itself
     */
    public int hashCode() {
        return Objects.hash(id, stock, product);
    }

    /**
     * Getter method for the id
     * @return String for the id of product
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the product
     * @param id String for the id of product
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for stock
     * @return int for the number of stock of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the number of stock for the product
     * @param stock int for the stock to be added
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter method for the product
     * @return assets.Product object in question
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product object
     * @param product assets.Product object that the previous information is about
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    /**
     * String representation of the object
     * @return Returns string with stock, id, product information
     */
    public String toString() {
        return "ProductInfo{" +
                "id='" + id + '\'' +
                ", stock=" + stock +
                ", product=" + product +
                '}';
    }
}
