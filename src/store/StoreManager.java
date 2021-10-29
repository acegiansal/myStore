/* Giancarlo Salvador #101139903*/
package store;

import java.util.ArrayList;

/**
 * Class meant to control shopping carts and inventory
 */
public class StoreManager {

    /**
     * Inventor should remain the same no matter the controller
     */
    private static Inventory inventory = new Inventory();

    /**
     * The shop ID (starts at 0 when first object is created)
     */
    private static int totalShopId = 0;

    /**
     * Shopping cart object for the specific user
     */
    private ShoppingCart shoppingCart;

    /**
     * Only constructor
     * Creates new shopping cart and increases shoppingCart ID counter
     */
    public StoreManager(){
        shoppingCart = new ShoppingCart(totalShopId);
        totalShopId++;
    }

    /**
     * Returns the amount of stock remaining for a certain product in inventory
     * @param id The Product ID as a string
     * @return The amount of stock of the product (0 if not in inventory)
     */
    public int getStock(String id){
        if(inventory.productInList(id)){
            ProductInfo info = inventory.getItem(id);
            return inventory.getProductQuantity(info.getProduct());
        } else {
            System.out.println("Product not currently in inventory");
            return 0;
        }
    }

    /**
     * Returns the amount of stock remaining for a certain product in shopping cart
     * @param id The Product ID as a string
     * @return The amount of stock of the product (0 if not in shopping cart)
     */
    public int getCartStock(String id){
        if(shoppingCart.productInList(id)){
            ProductInfo info = shoppingCart.getItem(id);
            return shoppingCart.getProductQuantity(info.getProduct());
        } else {
            System.out.println("Product not currently in shopping cart");
            return 0;
        }
    }

    /**
     * Gets the assets.Product ID from the name of the item from the inventory
     * @param name String for the name of the product
     * @return String for the ID of the product with the same name
     */
    public String getProdIdInv(String name){
        try {
            for (ProductInfo info : inventory.getInv()) {
                if (info.getProduct().getName().equals(name)) {
                    return info.getId();
                }
            }
        } catch (Exception e){
            System.out.println("Error when looking for product ID from name!");
        }
        return "ITEM NOT FOUND";
    }


    /**
     * Returns the ID of the current shopping cart
     * @return int for containing the ID of the shopping cart
     */
    public int getCartId(){
        return shoppingCart.getId();
    }

    /**
     * Puts current inventory in string format for the UI
     * <ul>
     *     <li>[0] is stock #</li>
     *     <li>[1] is product name</li>
     *     <li>[2] is product price</li>
     * </ul>
     * @return UI friendly string array containing stock, name, price of product
     */
    public String[][] currentInvInfo(){
        String[][] result = new String[inventory.getInv().size()][3];
        try {
            int counter = 0;
            for (ProductInfo info : inventory.getInv()) {
                result[counter][0] = String.valueOf(info.getStock());
                result[counter][1] = info.getProduct().getName();
                result[counter][2] = String.valueOf(info.getProduct().getPrice());
                counter++;
            }
        } catch (Exception e){
            System.out.println("Error when converting inv info to string!");
        }
        return result;
    }

    /**
     * Puts current shoppingCart in string format for the UI
     * @return UI friendly string array containing stock, name, price of product
     */
    public String[][] currentCartInfo(){
        String[][] result = new String[shoppingCart.getInv().size()][3];
        try {
            int counter = 0;
            for (ProductInfo info : shoppingCart.getInv()) {
                result[counter][0] = String.valueOf(info.getStock());
                result[counter][1] = info.getProduct().getName();
                result[counter][2] = String.valueOf(info.getProduct().getPrice());
                counter++;
            }
        } catch (Exception e){
            System.out.println("Error when converting shoppingCart info to string!");
        }
        return result;
    }

    /**
     * Adds a product and an amount of stock to store
     * @param id String of id of product to add
     * @param toAdd int of number of stock to add
     * @return int for the current number of stock in the shopping cart
     */
    public int addToCart(String id, int toAdd){
        try {
            if (inventory.productInList(id)) {
                ProductInfo info = inventory.getItem(id);
                if(toAdd>0) {
                    if (shoppingCart.productInList(id)) {
                        if (info.getStock() - toAdd >= 0) {
                            inventory.removeProductQuantity(info.getProduct(), toAdd);
                            shoppingCart.addProductQuantity(info.getProduct(), toAdd);
                            return shoppingCart.getProductQuantity(info.getProduct());
                        } else {
                            System.out.println("Error: Not enough stock!");
                            return -1;
                        }
                    } else {
                        if (info.getStock() - toAdd >= 0) {
                            info.setStock(info.getStock() - toAdd);
                            shoppingCart.addProduct(info.getProduct(), toAdd);
                            return shoppingCart.getProductQuantity(info.getProduct());
                        } else {
                            System.out.println("Error: Not enough stock!");
                            return -1;
                        }
                    }
                } else {
                    System.out.println("Less than 1 stock can not be added");
                    return -1;
                }
            } else {
                System.out.println("Error: assets.Product not found!");
                return -1;
            }
        } catch (Exception e){
            System.out.println("Error when adding object to cart!");
            return -1;
        }
    }

    /**
     * Removes product stock and potentiall product from cart
     * @param id String for the id of the desired product
     * @param toRemove int for the number of stock to remove
     * @return int for new stock amount in the inventory
     */
    public int removeFromCart(String id, int toRemove){
        try {
            if (shoppingCart.productInList(id)) {
                if(toRemove != 0) {
                    ProductInfo info = shoppingCart.getItem(id);
                    if (inventory.productInList(id)) {
                        if (info.getStock() - toRemove >= 0) {
                            shoppingCart.removeProductQuantity(info.getProduct(), toRemove);
                            inventory.addProductQuantity(info.getProduct(), toRemove);
                            return inventory.getProductQuantity(info.getProduct());
                        } else {
                            System.out.println("Error: Not enough stock!");
                            return -1;
                        }
                    } else {
                        if (info.getStock() - toRemove >= 0) {
                            info.setStock(info.getStock() - toRemove);
                            inventory.addProduct(info.getProduct(), toRemove);
                            return inventory.getProductQuantity(info.getProduct());
                        } else {
                            System.out.println("Error: Not enough stock!");
                            return -1;
                        }
                    }
                } else {
                    System.out.println("Less than 1 stock can not be removed");
                    return -1;
                }
            } else {
                System.out.println("Error: assets.Product not found!");
                return -1;
            }
        } catch (Exception e){
            System.out.println("Error when removing item from cart!");
            return -1;
        }
    }

    /**
     * Empties the cart by putting all remaining stock back into the inventory from the shopping cart
     */
    public void emptyCart(){
        try {
            ArrayList<ProductInfo> info = shoppingCart.getInv();
            while (info.size() != 0) {
                removeFromCart(info.get(0).getId(), info.get(0).getStock());
            }
        } catch (Exception e){
            System.out.println("Error when emptying cart!");
        }
    }

    /**
     * Finds the total cost of the items in the shopping cart without tax
     * @return double for the total cost of all items in shoppingCart object
     */
    public double getCartCost(){
        double totalCost = 0.0;
        try {
            for (ProductInfo info : shoppingCart.getInv()) {
                totalCost += info.getProduct().getPrice() * info.getStock();
            }
        } catch (Exception e){
            System.out.println("Error when adding total cart cost!");
        }
        return totalCost;
    }

    /**
     * Resets the inventory.
     * Usually used for debugging and JUNIT tests
     */
    public void resetInventory(){
        inventory = new Inventory();
    }

    @Override
    /**
     * Returns a string representation of the inventory
     * @return string containing the inventory list
     */
    public String toString() {
        return "StoreManager{" +
                "shoppingCart=" + shoppingCart +
                '}';
    }
}
