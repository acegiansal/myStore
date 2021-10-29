//Giancarlo Salvador
//101139903

package store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;


/**
 * GUI class meant to control user experience
 * @author Giancarlo Salvador #101139903
 */
public class StoreView {

    /** Store manager object that controls inventory and shopping cart*/
    private StoreManager storeMan;
    /** int for id of user*/
    private int currentId;

    /** JFrame for the store */
    private JFrame frame;
    /** Overall contentPane */
    private JPanel contentPane;
    /** JLabel for the id */
    private JLabel idLabel;
    /** Hashmap for the strings and JLabels for the store */
    private HashMap<String, JLabel> stockLabel;
    /** Hashmap for the strings and JLabels for the shopping cart */
    private HashMap<String, JLabel> stockCartLabel;
    /** Label for displaying the cost of cart for the store */
    private JLabel cartCostLabelStore;
    /** Label for displaying the cost of the cart for the shopping cart */
    private JLabel cartCostLabelCart;
    /** CardLayout controlling the different store pages */
    private CardLayout card;

    /** String for the Store Name */
    private static final String STORENAME = "Group Name Store";
    /** Name for the check out page */
    private static final String CHECKOUT = "check";
    /** Name for the home page */
    private static final String HOME = "home";
    /** Name for the shopping cart */
    private static final String SHOPCART = "shopCart";

    /* -------- Swing Constants -------- */
    /** Primary Colour */
    private static final Color PRIMARY = new Color(255,132,101);
    /** Background Colour */
    private static final Color BACKGROUND = new Color(28,23,33);
    /** Secondary Background Colour */
    private static final Color BACKGROUND2 = new Color(61,42,45);
    /** White Colour */
    private static final Color WHITE = new Color(255,255,255);
    /** Font for titles */
    private static final Font TITLEFONT = new Font("Tahoma", Font.BOLD, 30);
    /** Font for header */
    private static final Font HEADERFONT = new Font("Tahoma", Font.BOLD, 22);
    /** Font for normal text */
    private static final Font NORMFONT = new Font("Tahoma", Font.BOLD, 14);

    /* -------- Image Assets -------- */
    /** Logo image */
    private ImageIcon logo = new ImageIcon("assets/gn.png");

    /**
     * Default constructor that instantiates and defines frame and pages of store
     */
    public StoreView(){

        frame = new JFrame(STORENAME);
        frame.setPreferredSize(new Dimension(640,600));
        contentPane = new JPanel();
        card = new CardLayout();
        contentPane.setLayout(card);
        frame.add(contentPane);
        createMainMenu();

        createStore(5, false);
    }

    /**
     * Displays GUI by packing.
     */
    public void displayGUI(){
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // the frame is not visible until we set it to be so
        frame.setVisible(true);
    }

    /**
     * Creates the main menu page for the user.
     * Is called everytime the user goes home
     */
    private void createMainMenu(){
        //MAKE STORE MANAGER HERE SO THAT YOU CAN RESET WITHOUT EXITING
        storeMan = new StoreManager();
        currentId = storeMan.getCartId();

        JPanel entryPane = new JPanel();
        entryPane.setLayout(new GridBagLayout());
        entryPane.setBackground(BACKGROUND);
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 30;

        c.gridx = 0;
        JLabel logoLabel = new JLabel();
        logo = new ImageIcon(logo.getImage().getScaledInstance(70,70, Image.SCALE_DEFAULT));
        logoLabel.setIcon(logo);
        entryPane.add(logoLabel, c);

        c.gridx=1;
        c.gridwidth = 2;
        JLabel shopTitle = new JLabel(STORENAME);
        shopTitle.setFont(TITLEFONT);
        shopTitle.setForeground(WHITE);
        entryPane.add(shopTitle,c);

        c.gridwidth = 1;
        c.ipadx = 100;
        c.gridx=3;
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        buttonPane.setBackground(BACKGROUND);
        GridBagConstraints bC = new GridBagConstraints();
        bC.fill = GridBagConstraints.HORIZONTAL;
        bC.ipadx = 50;

        bC.gridy = 0;
        JButton enterButton = new JButton("Shop");
        enterButton.setFont(NORMFONT);
        enterButton.setBackground(PRIMARY);
        enterButton.setBorder(BorderFactory.createRaisedBevelBorder());

        enterButton.addActionListener(new ActionListener() {
            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                card = (CardLayout) contentPane.getLayout();
                idLabel.setText("User ID: " + currentId);
                card.show(contentPane, "PAGE0");
            }
        });
        buttonPane.add(enterButton, bC);

        bC.gridy = 1;
        bC.insets = new Insets(10,0,0,0);
        JButton closeButton = new JButton("Leave");
        closeButton.setFont(NORMFONT);
        closeButton.setBackground(BACKGROUND2);
        closeButton.setForeground(WHITE);
        closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        closeButton.addActionListener(ae -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        buttonPane.add(closeButton, bC);


        entryPane.add(buttonPane,c);
        contentPane.add(entryPane, HOME);
    }

    /**
     * Resets store values (empties cart and reverts all stocks to original amount)
     */
    private void resetStore(){
        double cost = storeMan.getCartCost();
        String costCart = String.format("Cost of cart: $%.2f", cost);
        cartCostLabelStore.setText(costCart);
        for(String name: stockLabel.keySet()){
            stockLabel.get(name).setText(String.valueOf(storeMan.getStock(storeMan.getProdIdInv(name))));
        }
    }

    /**
     * Creates each page with all available products for the user to view and interact with
     * @param itemPer int for number of items per page
     * @param isShopCart boolean for if you want to create shopping cart (true) or the inventory (false)
     * @return Array of JPanels that make up the pages with inventory products
     */
    private JPanel[] createStore(int itemPer, boolean isShopCart){
        int numPages;
        int chartSize;

        if(isShopCart){
            //System.out.println("\n\nCREATING NEW SHOPPING CART\n\n");
            chartSize = storeMan.currentCartInfo().length;
            this.stockCartLabel = new HashMap<>();
        } else {
            chartSize = storeMan.currentInvInfo().length;
            this.stockLabel = new HashMap<>();
        }
        numPages = chartSize/itemPer;
        if(chartSize%itemPer != 0){
            numPages++;
        }

        JPanel[] storePages = new JPanel[numPages];

        /* For each page that is required*/
        for(int i = 0; i<numPages; i++){
            storePages[i] = new JPanel();
            storePages[i].setLayout(new BorderLayout());
            storePages[i].setBackground(BACKGROUND);

            /*--------------- BEGINS THE HEADER SIDE ----------------*/
            JPanel headerPane = new JPanel();
            headerPane.setBackground(BACKGROUND);
            headerPane.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();


            if(isShopCart) {
                JButton goBrowse = createButton("< Browse", 0);
                c.insets = new Insets(0, 5, 0, 5);
                c.gridx = 0;
                c.ipady = 10;
                goBrowse.addActionListener(ae -> {
                    double cost = storeMan.getCartCost();
                    String costCart = String.format("Cost of cart: $%.2f", cost);
                    cartCostLabelStore.setText(costCart);
                    card = (CardLayout) contentPane.getLayout();
                    card.show(contentPane, "PAGE0");
                });
                headerPane.add(goBrowse, c);
            } else{
                JButton goHome = createButton("< Home", 0);
                c.insets = new Insets(0, 5, 0, 5);
                c.gridx = 0;
                c.ipady = 10;
                goHome.addActionListener(ae -> {
                    storeMan.emptyCart();
                    resetStore();
                    createMainMenu();
                    card = (CardLayout) contentPane.getLayout();
                    card.show(contentPane, HOME);
                });
                headerPane.add(goHome, c);
            }

            c.gridx = 1;
            c.gridwidth = 2;
            c.insets = new Insets(10,10,10,30);
            JLabel bTitle;
            if(isShopCart){
                bTitle = new JLabel("Shopping Cart");
            } else {
                bTitle = new JLabel("Browse");
            }
            bTitle.setFont(TITLEFONT);
            bTitle.setForeground(WHITE);
            headerPane.add(bTitle, c);

            c.gridx = 3;
            JPanel topButPane = new JPanel();
            topButPane.setBackground(BACKGROUND);
            topButPane.setLayout(new GridBagLayout());
            GridBagConstraints b = new GridBagConstraints();
            b.fill = GridBagConstraints.HORIZONTAL;
            b.insets = new Insets(10,10,0,5);

            //CHECKOUT CART BUTTON
            b.gridx=0;
            b.gridy=0;
            if(isShopCart){
                b.gridwidth = 2;
            }
            JButton checkCartBut = createButton("Checkout Shopping Cart", 1);
            checkCartBut.addActionListener(new ActionListener() {
                // this method will be called when we click the button
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(storeMan.currentCartInfo().length == 0){
                        JOptionPane.showMessageDialog(null, "Can not continue as cart is empty!");
                    } else {
                        createCheckout();
                        card = (CardLayout) contentPane.getLayout();
                        card.show(contentPane, CHECKOUT);
                    }
                }
            });
            topButPane.add(checkCartBut, b);

            if(!isShopCart) {
                b.gridx = 1;
                JButton editCartBut = createButton("Edit Shopping Cart >", 0);
                // this method will be called when we click the button
                editCartBut.addActionListener(ae -> {
                    if(storeMan.currentCartInfo().length == 0){
                        JOptionPane.showMessageDialog(null, "Can not continue as cart is empty!");
                    } else {
                        createStore(5, true);
                        card = (CardLayout) contentPane.getLayout();
                        card.show(contentPane, SHOPCART);
                    }
                });
                topButPane.add(editCartBut, b);
            }

            b.gridwidth = 1;
            b.gridx = 0;
            b.gridy = 1;
            idLabel = new JLabel("User ID: " + currentId);
            idLabel.setForeground(WHITE);
            idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            idLabel.setFont(NORMFONT);
            topButPane.add(idLabel, b);

            b.gridx = 1;
            b.gridy = 1;
            double cost = storeMan.getCartCost();
            String costCart = String.format("Cost of cart: $%.2f", cost);
            if(isShopCart) {
                cartCostLabelCart = createLabel(costCart);
                cartCostLabelCart.setHorizontalAlignment(SwingConstants.RIGHT);
                topButPane.add(cartCostLabelCart, b);
            } else {
                cartCostLabelStore = createLabel(costCart);
                cartCostLabelStore.setHorizontalAlignment(SwingConstants.RIGHT);
                topButPane.add(cartCostLabelStore, b);
            }

            headerPane.add(topButPane, c);
            storePages[i].add(headerPane, BorderLayout.NORTH);
            /*--------------- ENDS THE HEADER SIDE ----------------*/
            /*--------------- BEGINS BODY ----------------*/
            JPanel bodyPane = new JPanel();
            bodyPane.setBackground(BACKGROUND);
            bodyPane.setLayout(new GridBagLayout());
            GridBagConstraints in = new GridBagConstraints();
            in.fill = GridBagConstraints.BOTH;
            in.ipadx = 20;

            int numRows;
            if(i<numPages-1 || (i == numPages-1 && chartSize % itemPer == 0)){
                numRows = itemPer + 1;
            } else {
                numRows = chartSize % itemPer + 1;
            }

            in.gridx = 0;
            in.gridy = 0;
            JLabel imageLabel = createLabel("Image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bodyPane.add(imageLabel, in);

            in.gridx = 1;
            JLabel nameLabel = createLabel("Product Name");
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bodyPane.add(nameLabel, in);

            in.gridx=2;
            JLabel priceLabel = createLabel("Price");
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bodyPane.add(priceLabel, in);

            in.gridx=3;
            JLabel sLabel = createLabel("Stock");
            sLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bodyPane.add(sLabel, in);

            in.gridx=4;
            JLabel emptyLabel1 = createLabel(" ");
            emptyLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            bodyPane.add(emptyLabel1, in);

            in.gridx=5;
            JLabel emptyLabel2 = createLabel(" ");
            emptyLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            bodyPane.add(emptyLabel2, in);

            String[][] chartData;

            if(isShopCart){
                chartData = storeMan.currentCartInfo();
            } else {
                chartData = storeMan.currentInvInfo();
            }
            int pageMult = itemPer*i;
            int rowCounter = 0;

            /* Adds the actual items to the page*/
            for(int itemNum = 0; itemNum < numRows-1; itemNum++){
                int index = itemNum+pageMult;
                in.gridy = ++rowCounter;
                String stock = chartData[index][0];
                String name = chartData[index][1];
                double price = Double.parseDouble(chartData[index][2]);

                /* Image */
                in.gridx = 0;
                JPanel imagePane = createMiniPanel(itemNum%2);          //itemNum%2 allows for alternating table colours
                JLabel pImage = new JLabel();
                ImageIcon pIcon = new ImageIcon("assets/" + name + ".png");
                pIcon = new ImageIcon(pIcon.getImage().getScaledInstance(70,70, Image.SCALE_DEFAULT));
                pImage.setIcon(pIcon);
                imagePane.add(pImage);
                bodyPane.add(imagePane, in);

                /* Name */
                in.gridx = 1;
                JPanel namePane = createMiniPanel(itemNum%2);
                JLabel prodLabel = createLabel(name);
                prodLabel.setHorizontalAlignment(SwingConstants.CENTER);
                prodLabel.setVerticalAlignment(SwingConstants.CENTER);
                namePane.add(prodLabel, BorderLayout.CENTER);
                bodyPane.add(namePane, in);

                /* Price */
                in.gridx = 2;
                JPanel pricePane = createMiniPanel(itemNum%2);
                String priceStr = String.format("$%.2f", price);
                JLabel priceL = createLabel(priceStr);
                priceL.setHorizontalAlignment(SwingConstants.CENTER);
                priceL.setVerticalAlignment(SwingConstants.CENTER);
                pricePane.add(priceL, BorderLayout.CENTER);
                bodyPane.add(pricePane, in);

                /* Stock */
                in.gridx = 3;
                JPanel stockPanel = createMiniPanel(itemNum%2);
                if(isShopCart){
                    stockCartLabel.put(name, createLabel(stock));
                    stockCartLabel.get(name).setHorizontalAlignment(SwingConstants.CENTER);
                    stockCartLabel.get(name).setVerticalAlignment(SwingConstants.CENTER);
                    stockPanel.add(stockCartLabel.get(name), BorderLayout.CENTER);
                } else {
                    stockLabel.put(name,createLabel(stock));
                    stockLabel.get(name).setHorizontalAlignment(SwingConstants.CENTER);
                    stockLabel.get(name).setVerticalAlignment(SwingConstants.CENTER);
                    stockPanel.add(stockLabel.get(name), BorderLayout.CENTER);
                }
                bodyPane.add(stockPanel, in);


                /* NUMBER FIELD */
                in.gridx = 5;
                JPanel numPanel = createMiniPanel(itemNum%2);
                numPanel.setLayout(new FlowLayout());
                JTextField numToGet = new JTextField("0", 5);
                numToGet.setHorizontalAlignment(SwingConstants.CENTER);
                numPanel.add(numToGet);
                bodyPane.add(numPanel, in);

                /* ADD BUTTON */
                in.gridx = 4;
                JPanel addPanel = createMiniPanel(itemNum % 2);
                JButton changeStockBut;
                if(isShopCart){
                    changeStockBut = createButton("Remove from cart", 1);
                    changeStockBut.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            String id = storeMan.getProdIdInv(name);

                            try {
                                int stockToRemove = Integer.parseInt(numToGet.getText());
                                int numInCart = storeMan.removeFromCart(id, stockToRemove);
                                if (numInCart == -1) {
                                    JOptionPane.showMessageDialog(null, "ERROR IN JTEXTFIELD!\nINVALID stock amount!");
                                } else {
                                    double cost = storeMan.getCartCost();
                                    String costCart = String.format("Cost of cart: $%.2f", cost);
                                    cartCostLabelCart.setText(costCart);
                                    stockLabel.get(name).setText(String.valueOf(storeMan.getStock(id)));
                                    stockCartLabel.get(name).setText(String.valueOf(storeMan.getCartStock(id)));
                                }
                                if(storeMan.getCartStock(id) == 0){
                                    bodyPane.remove(imagePane);
                                    bodyPane.remove(namePane);
                                    bodyPane.remove(pricePane);
                                    bodyPane.remove(stockPanel);
                                    bodyPane.remove(numPanel);
                                    bodyPane.remove(addPanel);
                                    bodyPane.revalidate();
                                    bodyPane.repaint();
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "ERROR IN JTEXTFIELD!\nInput must be int");
                            }

                        }
                    });
                } else {
                    changeStockBut = createButton("Add to cart", 1);
                    changeStockBut.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            String id = storeMan.getProdIdInv(name);
                            //System.out.println("ID: " + id);
                            //System.out.println("Num to add: " + numToGet.getText());

                            try {
                                int stockToAdd = Integer.parseInt(numToGet.getText());
                                if (storeMan.addToCart(id, stockToAdd) == -1) {
                                    JOptionPane.showMessageDialog(null, "ERROR IN JTEXTFIELD!\nINVALID stock amount!");
                                } else {
                                    double cost = storeMan.getCartCost();
                                    String costCart = String.format("Cost of cart: $%.2f", cost);
                                    cartCostLabelStore.setText(costCart);
                                    stockLabel.get(name).setText(String.valueOf(storeMan.getStock(id)));
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "ERROR IN JTEXTFIELD!\nInput must be int");
                            }

                        }
                    });
                }
                addPanel.add(changeStockBut, BorderLayout.CENTER);
                bodyPane.add(addPanel, in);


            }
            storePages[i].add(bodyPane, BorderLayout.CENTER);
            /*--------------- ENDS BODY ----------------*/
            /*--------------- BEGINS BOTTOM ----------------*/

            JPanel botPane = new JPanel();
            botPane.setBackground(BACKGROUND);
            botPane.setLayout(new FlowLayout());

            //ADDS PREVIOUS BUTTON
            if(i!=0){
                int prevPage = i-1;
                JButton prevButton = createButton("< Previous Page", 0);
                prevButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        card = (CardLayout) contentPane.getLayout();
                        card.show(contentPane, "PAGE" + prevPage);
                    }
                });
                botPane.add(prevButton);

            }

            //ADDS NEXT BUTTON
            if(i<numPages-1){
                int nextPage = i+1;
                JButton nextButton = createButton("Next Page >", 1);
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        card = (CardLayout) contentPane.getLayout();
                        card.show(contentPane, "PAGE" + nextPage);
                    }
                });
                botPane.add(nextButton);
            }

            storePages[i].add(botPane, BorderLayout.SOUTH);
            /*--------------- ENDS BOTTOM ----------------*/
            String nameOfPage;
            if(isShopCart){
                nameOfPage = SHOPCART;
            } else {
                nameOfPage = "PAGE" + i;
            }

            contentPane.add(storePages[i], nameOfPage);
        }
        return storePages;
    }

    /**
     * Creates the checkout page.
     * Creates checkout page every time user tries to checkout.
     * Shows a reciept
     */
    private void createCheckout(){
        JPanel checkoutPane = new JPanel();
        checkoutPane.setLayout(new BorderLayout());

        /* Header */
        JPanel headerPane = new JPanel();
        headerPane.setBackground(BACKGROUND);
        headerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel thankTitle = new JLabel("Thank You");
        thankTitle.setForeground(WHITE);
        thankTitle.setFont(TITLEFONT);
        thankTitle.setBorder(BorderFactory.createEmptyBorder(10,20,0,50));
        headerPane.add(thankTitle);
        checkoutPane.add(headerPane, BorderLayout.NORTH);


        /* BODY */
        JPanel intermPane = new JPanel();
        intermPane.setBackground(BACKGROUND);
        intermPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel bodyPane = new JPanel();
        bodyPane.setBorder(BorderFactory.createEmptyBorder(20,20,70,50));
        bodyPane.setBackground(BACKGROUND);
        bodyPane.setLayout(new GridBagLayout());
        intermPane.add(bodyPane);
        GridBagConstraints bC = new GridBagConstraints();
        bC.fill = GridBagConstraints.HORIZONTAL;
        bC.gridx = 0;

        bC.gridy = 0;
        JLabel recLabel = new JLabel("Receipt");
        recLabel.setForeground(PRIMARY);
        recLabel.setFont(HEADERFONT);
        bodyPane.add(recLabel, bC);

        //ITEM LABEL
        bC.insets = new Insets(10,20,10,10);
        bC.gridy = 1;
        String cartString = "<html>";
        String[][] cartData = storeMan.currentCartInfo();
        for(String[] itemInfo: cartData){
            double totalForItem = Double.parseDouble(itemInfo[0]) * Double.parseDouble(itemInfo[2]);
            cartString += String.format("%s %s: $%.2f<br>", itemInfo[0], itemInfo[1], totalForItem);
        }
        cartString += "</html>";
        JLabel itemLabel = createLabel(cartString);
        bodyPane.add(itemLabel, bC);

        //TOTAL LABEL
        bC.gridy = 2;
        String total = String.format("Total: $%.2f", storeMan.getCartCost());
        JLabel totLabel = createLabel(total);
        bodyPane.add(totLabel, bC);

        bC.gridy = 3;
        JLabel taxLabel = createLabel("HST: 13%");
        bodyPane.add(taxLabel, bC);

        bC.gridy = 4;
        String grandTotal = String.format("Total: $%.2f", (storeMan.getCartCost()*1.13));
        JLabel grandLabel = createLabel(grandTotal);
        bodyPane.add(grandLabel, bC);

        checkoutPane.add(intermPane, BorderLayout.CENTER);

        JPanel botPanel = new JPanel();
        botPanel.setBackground(BACKGROUND);
        checkoutPane.add(botPanel, BorderLayout.SOUTH);
        botPanel.setLayout(new FlowLayout());

        JButton back = createButton("< Back to store", 0);
        back.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                card = (CardLayout) contentPane.getLayout();
                card.show(contentPane, "PAGE0");
            }
        });
        botPanel.add(back);

        JButton finish = createButton("Finish", 1);
        finish.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        finish.addActionListener(ae -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        botPanel.add(finish);

        contentPane.add(checkoutPane, CHECKOUT);
    }

    /**
     * Creates a JPanel with BorderLayout and a Background colour
     * @param type 1 for first BACKGROUND, 0 or other for BACKGROUND2
     * @return A JPanel with specified background colour
     */
    private JPanel createMiniPanel(int type){
        JPanel temp = new JPanel();
        if(type == 1){
            temp.setBackground(BACKGROUND);
        } else {
            temp.setBackground(BACKGROUND2);
        }
        temp.setLayout(new BorderLayout());
        return temp;
    }

    /**
     * Creates a JLabel element with normal font (NORMFONT) and white text
     * @param text String for the label
     * @return JLabel with normal store font and colour
     */
    private JLabel createLabel(String text){
        JLabel temp = new JLabel(text);
        temp.setForeground(WHITE);
        temp.setFont(NORMFONT);
        return temp;
    }

    /**
     * Creates a JButton element with normal font (NORMFONT) and background colour
     * @param text Text for the JButton
     * @param type 1 for primary coloured, other for background2 coloured
     * @return JButton with specified text and background colour
     */
    private JButton createButton(String text, int type){
        JButton temp = new JButton(text);

        if(type == 1) {
            temp.setBackground(PRIMARY);
        } else {
            temp.setBackground(BACKGROUND2);
            temp.setForeground(WHITE);
        }
        temp.setFont(NORMFONT);
        temp.setBorder(BorderFactory.createRaisedBevelBorder());
        return temp;
    }

    /**
     * Main function that starts program
     * @param args Arguments provided to code
     */
    public static void main(String[] args) {
        StoreView test = new StoreView();
        test.displayGUI();
    }
}
