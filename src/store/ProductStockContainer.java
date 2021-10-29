/* Giancarlo Salvador #101139903*/
package store;

import java.util.ArrayList;

/**
 * Abstract class for classes that contain product stocks
 * @author Giancarlo Salvador
 */
public interface ProductStockContainer {

    /**
     * returns the amount of stock for a specified product
     * @param pr Desired product
     * @return int containing the number of stock for the desired product
     */
    int getProductQuantity(Product pr);

    /**
     * Adds certain number of stock if the product exists or not,
     * display error if product does not exist
     * @param pr String for the id of the desired product
     * @param numToAdd int for the number of stock to add
     * @return void for if adding stock was successful or not (true if yes, false if not)
     */
    void addProductQuantity(Product pr, int numToAdd);

    /**
     * Removes stock from the object.
     * @param pr String for the id of the desired product
     * @param numBought int for the number of stock to be removed
     * @return Whether or not removing the stock was successful or not
     */
    boolean removeProductQuantity(Product pr, int numBought);

    /**
     *  Gets the number of products in arrayList of ProductInfo
     * @return Returns the number of products in object
     */
    int getNumOfProducts();


}
