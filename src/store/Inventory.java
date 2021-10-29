/* Giancarlo Salvador #101139903*/
package store;

import java.util.ArrayList;

/**
 * Class meant to represent the inventory of the store
 */
public class Inventory implements ProductStockContainer{


    /** Inventory object containing the info on the products*/
    private ArrayList<ProductInfo> inv;

    /**
     * Only constructor
     * Adds default "debug" values (apple, banana, orange)
     */
    public Inventory(){
        inv = new ArrayList<>();

        /* Debug values */
        addProduct(new Product("Empress", "01", 50), 50);
        addProduct(new Product("Wildfire", "02", 300), 30);
        addProduct(new Product("BlueSpruce", "03", 5), 80);
        addProduct(new Product("PointDisarray", "04", 25), 60);
    }


    /**
     * Getter method for inv arrayList
     * @return arrayList of productInfo objects
     */
    public ArrayList<ProductInfo> getInv() {
        return inv;
    }

    /**
     * Gets all information about a product
     * @param pr desired Product object
     * @return ProductInfo object containing all product information and product
     */
    public ProductInfo getInfo(Product pr){
        ProductInfo ret = new ProductInfo();
        for(ProductInfo info: inv){
            if(info.getId().equals(pr.getId())){
                ret = info;
            }
        }
        return ret;
    }

    /**
     * returns the amount of stock for a specified product
     * @param pr String for the id of the desired product
     * @return int containing the number of stock for the desired product
     */
    public int getProductQuantity(Product pr){
        return getInfo(pr).getStock();
    }

    /**
     * Checks whether a certain product is in the inventory
     * @param id String for the id of the desired product
     * @return boolean whether the product was found or not
     */
    public boolean productInList(String id){
        try {
            for (ProductInfo info : inv) {
                if (info.getId().equals(id)) {
                    return true;
                }
            }
        } catch(Exception e){
            System.out.print(e.toString());
            System.out.println("Error has occurred when looking for product");
        }
        return false;
    }

    /**
     * Gets the actual ProductInfo object for a product with a certain id
     * @param id String for the id of the desired product
     * @return The ProductInfo object containing information about the product
     */
    public ProductInfo getItem(String id){
        ProductInfo result = new ProductInfo();
        try {
            for (ProductInfo info : inv) {
                if (info.getId().equals(id)) {
                    result = info;
                    break;
                }
            }
        } catch (Exception e){
            System.out.println("Error when getting product");
        }
        return result;
    }

    /**
     * Finds the item index of the desired product
     * @param id String for the id of the desired product
     * @return int with the index in the arrayList containing the desired product
     */
    private int getItemIndex(String id){
        int index = -1;
        int counter = 0;
        try {
            for (ProductInfo info : inv) {
                if (info.getId().equals(id)) {
                    index = counter;
                    break;
                }
                counter++;
            }
        } catch (Exception e){
            System.out.println("Error when getting product index");
        }
        return index;
    }

    /**
     * Adds a product if it does not exist. If it does not, adds the product and the initial stock
     * @param newProd assets.Product object to be added
     * @param stock initial number of stock
     * @return Whether the stock was able to be added or not
     */
    public boolean addProduct(Product newProd, int stock){

        try {
            if (productInList(newProd.getId())) {
                System.out.println("assets.Product already in stock!");
                return false;
            } else {
                ProductInfo temp = new ProductInfo(newProd.getId(), stock, newProd);
                inv.add(temp);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error when adding product!\n\n");
            return false;
        }
    }

    /**
     * Adds a product if it does not exist. If it does not, adds the product and the initial stock
     * @param list ProductInfo Object containing all important info on object
     * @return Whether the stock was successfully added or not
     */
    public boolean addProductQuantity(ProductInfo list){
        try{
            if(productInList(list.getId())){
                System.out.println("assets.Product already in stock!");
                return false;
            } else{
                inv.add(list);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error when adding product!");
            return false;
        }
    }

    /**
     * Adds certain number of stock if the product exists or not,
     * display error if product does not exist
     * @param pr String for the id of the desired product
     * @param numToAdd int for the number of stock to add
     * @return Boolean for if adding stock was successful or not (true if yes, false if not)
     */
    public void addProductQuantity(Product pr, int numToAdd){
        try {
            ProductInfo info = getInfo(pr);
            if (!info.getId().equals(null) && numToAdd>=0) {
                info.setStock(info.getStock() + numToAdd);
            } else if(numToAdd <0){
                System.out.println("Can not add negative stock!");
            } else {
                System.out.println("Error: Product not found!");
            }
        } catch (Exception e) {
            System.out.println("Error when adding stock!");
        }
    }

    @Override
    public int getNumOfProducts() {
        return inv.size();
    }

    /**
     * Removes stock from the inventory.
     * Will return error if there is not enough stock available
     * It is assumed that the stock exists and that this has been checked before this is called
     * @param pr String for the id of the desired product
     * @param numBought int for the number of stock to be removed
     * @return Whether or not removing the stock was successful or not
     */
    public boolean removeProductQuantity(Product pr, int numBought){

        try {
            ProductInfo info = getInfo(pr);
            int curStock = info.getStock();

            if (curStock >= numBought) {
                info.setStock(curStock - numBought);
                return true;
            } else {
                System.out.printf("ERROR: Not enough stock:\nAvailable: {%d}, ToRemove: {%d}\n",
                        curStock, numBought);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error when removing stock!");
            return false;
        }
    }

    @Override
    /**
     * Returns a string representation of the inventory
     * @return string containing the inventory list
     */
    public String toString() {
        return "Inventory{" +
                "inv=" + inv +
                '}';
    }
}
