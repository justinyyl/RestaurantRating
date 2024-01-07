package ui;


import model.Dish;
import model.EventLog;
import model.Event;
import model.ResApp;
import model.Restaurant;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// RestaurantRating application
public class RestaurantRating extends JFrame {
    private static final String JSON_STORE = "./data/ResApp.json";
    private ResApp ra;
    private Restaurant res;
    private Dish dish;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JLabel notationLabel;

    // EFFECTS: runs the rating application
    public RestaurantRating() throws FileNotFoundException {
        runRating();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRating() {
        init();
        displayNotation();
        setTitle("Restaurant Rating");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        showRatingScreen();
        try {
            Thread.sleep(1000); // Wait for 3 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        upperProcess();
    }

    // EFFECTS: process commend determine quit or processing main
    private void upperProcess() {
        resAppPanel();

    }

    // MODIFIES: this
    // EFFECTS: initializes restaurants
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        ra = new ResApp();
        res = new Restaurant("restaurant");
        dish = new Dish("dish");
        notationLabel = new JLabel();
        ra.addRestaurant("bruce");
        ra.getRestaurants().get(0).addDish("honey moon");
        ra.getRestaurants().get(0).addGradedDish("spicy sun", 5,7,8);

    }

    // EFFECTS: displays some notations for user
    private void displayNotation() {
        notationLabel.setText("<html>Note: <br>1. Each (int)input in [1, 9]<br>");
        notationLabel.setText(notationLabel.getText() + "2. All add-score methods need three (int)inputs<br>");
        notationLabel.setText(notationLabel.getText() + "3. There must exist a space between two words</html>");
    }

    // MODIFIES: this
    // EFFECTS: processes user based on main menu
    private void processingMain(String command) {
        if (command.equals("vr")) {
            viewRestaurants();
        } else if (command.equals("ar")) {
            addRestaurant();
        } else if (command.equals("tor")) {
            haveRestaurant();
        } else if (command.equals("s")) {
            saveResApp();
        } else if (command.equals("l")) {
            loadResApp();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: if the restaurant app has no restaurants,
    //              print no dishes and go back to upperProcess(),
    //          else toRestaurant()
    private void haveRestaurant() {
        if (ra.getRestaurants().size() == 0) {
            JOptionPane.showMessageDialog(RestaurantRating.this, "No restaurant exists!");
            upperProcess();
        } else {
            toRestaurant();
        }
    }


    // EFFECTS: select a restaurant and display restaurant-level menu
    private void toRestaurant() {
        selectRestaurant();
    }

    // EFFECTS: process restaurant-level application
    private void resProcess() {
        resPanel();
    }

    // MODIFIES: this
    // EFFECTS: processes user based on restaurant menu
    private void processingRes(String commend) {
        if (commend.equals("vd")) {
            viewDishes();
        } else if (commend.equals("ad")) {
            addDish();
        } else if (commend.equals("agd")) {
            addGradedDish();
        } else if (commend.equals("ae")) {
            addEnvironmentScore();
        } else if (commend.equals("rs")) {
            viewRestaurantScore();
        } else if (commend.equals("dss")) {
            viewDishesScore();
        } else if (commend.equals("ess")) {
            viewEnvironmentScore();
        } else if (commend.equals("tod")) {
            haveDishes();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: if the restaurant has no dishes,
    //              print no dishes and go back to resProcess(),
    //          else toDish()
    private void haveDishes() {
        if (res.getDishes().size() == 0) {
            JOptionPane.showMessageDialog(RestaurantRating.this,
                    "This restaurants hasn't have any dishes yet!");
            resProcess();
        } else {
            toDish();
        }
    }

    // EFFECTS: select a dish and display dish-level menu
    private void toDish() {
        selectDish();
    }

    // EFFECTS: process dish-level application
    private void dishProcess() {
        dishPanel();
    }

    // MODIFIES: this
    // EFFECTS: processes user based on dish menu
    private void processingDish(String command) {
        if (command.equals("as")) {
            addScore();
        } else if (command.equals("ds")) {
            viewDishScore();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: str
    // EFFECTS: if no dish score, return no score,
    //          else return the current total score of that dish
    private String viewDishScore() {
        String str = "";
        if (dish.rateDish() == -1) {
            str = "This dish hasn't have score yet!";
        } else {
            str = "Dish score: " + dish.rateDish();
        }
        return str;
    }

    // MODIFIES: this
    // EFFECTS: add score to this dish by input values
    @SuppressWarnings("methodlength")
    private void addScore() {
        JPanel addScore = new JPanel();
        addScore.setLayout(new GridLayout(3,1));
        JTextField tasteField = new JTextField(10);
        JTextField appearanceField = new JTextField(10);
        JTextField qualityField = new JTextField(10);
        JLabel tasteLabel = new JLabel("taste: ");
        JLabel appearanceLabel = new JLabel("appearance: ");
        JLabel qualityLabel = new JLabel("quality: ");
        addScore.add(tasteLabel);
        addScore.add(tasteField);
        addScore.add(appearanceLabel);
        addScore.add(appearanceField);
        addScore.add(qualityLabel);
        addScore.add(qualityField);
        int result = JOptionPane.showOptionDialog(
                null, addScore, "Add Score", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            int taste = Integer.parseInt(tasteField.getText());
            int appearance = Integer.parseInt(appearanceField.getText());
            int quality = Integer.parseInt(qualityField.getText());
            dish.addScore(taste, appearance, quality);
            JOptionPane.showMessageDialog(null, "Successfully grade the dish!");
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
        }
    }

    // MODIFIES: this, dh
    // EFFECTS: let user enter the name of the dish and selected
    //          doesn't find dish, re-enter the name
    @SuppressWarnings("methodlength")
    private void selectDish() {
        Dish dh = new Dish("");
        boolean exist = false;
        JPanel selectDish = new JPanel();
        selectDish.setLayout(new GridLayout(3,2));
        JTextField textField = new JTextField(10);
        JLabel label = new JLabel("Select a dish");
        JLabel cur = new JLabel("Current " + viewDishes());
        selectDish.add(cur);
        selectDish.add(label);
        selectDish.add(textField);
        int result = JOptionPane.showOptionDialog(
                null, selectDish, "To Dish", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            for (Dish d : res.getDishes()) {
                if (d.getName().equals(textField.getText().toLowerCase())) {
                    dh = d;
                    exist = true;
                }
            }
            if (exist) {
                dish = dh;
                JOptionPane.showMessageDialog(null, "Selected: " + dish.getName());
                dishProcess();
            } else {
                JOptionPane.showMessageDialog(null, "Selected dish doesn't exist!");
                selectDish();
            }
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
            resProcess();
        }
    }

    // MODIFIES: str
    // EFFECTS: if no average environment score, return no score,
    //          else return the current average environment score of that restaurant
    private String viewEnvironmentScore() {
        String str = "";
        if (res.rateEnvironmentScores() == -1) {
            str = "This restaurant hasn't have average environment score yet!";
        } else {
            str = "Average environment score: " + res.rateEnvironmentScores();
        }
        return str;
    }

    // MODIFIES: str
    // EFFECTS: if no average dish score, return no score,
    //          else return the current average dishes score of the restaurant
    private String viewDishesScore() {
        String str = "";
        if (res.rateDishScores() == -1) {
            str = "This restaurant hasn't have average dish score yet!";
        } else {
            str = "Average dish score: " + res.rateDishScores();
        }
        return str;
    }

    // MODIFIES: str
    // EFFECTS: if no restaurant score, return no score,
    //          else return the current total score of that restaurant
    private String viewRestaurantScore() {
        String str = "";
        if (res.rateRestaurant() == -1) {
            str = "This restaurant hasn't have score yet!";
        } else {
            str = "Restaurant score: " + res.rateRestaurant();
        }
        return str;
    }

    // MODIFIES: this
    // EFFECTS: add environment score with input values
    @SuppressWarnings("methodlength")
    private void addEnvironmentScore() {
        JPanel newEnvironmentScore = new JPanel();
        newEnvironmentScore.setLayout(new GridLayout(3,2));
        JTextField ambienceField = new JTextField(10);
        JTextField serviceQualityField = new JTextField(10);
        JTextField cleanlinessField = new JTextField(10);
        JLabel ambienceLabel = new JLabel("ambience: ");
        JLabel serviceQualityLabel = new JLabel("service quality: ");
        JLabel cleanlinessLabel = new JLabel("cleanliness: ");
        newEnvironmentScore.add(ambienceLabel);
        newEnvironmentScore.add(ambienceField);
        newEnvironmentScore.add(serviceQualityLabel);
        newEnvironmentScore.add(serviceQualityField);
        newEnvironmentScore.add(cleanlinessLabel);
        newEnvironmentScore.add(cleanlinessField);
        int result = JOptionPane.showOptionDialog(
                null, newEnvironmentScore, "Add Environment Score", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            int ambience = Integer.parseInt(ambienceField.getText());
            int serviceQuality = Integer.parseInt(serviceQualityField.getText());
            int cleanliness = Integer.parseInt(cleanlinessField.getText());
            res.addEnvironmentScore(ambience, serviceQuality, cleanliness);
            JOptionPane.showMessageDialog(null, "Successfully grade the environment!");
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a graded dish with input name an values
    @SuppressWarnings("methodlength")
    private void addGradedDish() {
        JPanel newGradedDish = new JPanel();
        newGradedDish.setLayout(new GridLayout(9,1));
        JTextField nameField = new JTextField(10);
        JTextField tasteField = new JTextField(10);
        JTextField appearanceField = new JTextField(10);
        JTextField qualityField = new JTextField(10);
        JLabel nameLabel = new JLabel("name: ");
        JLabel tasteLabel = new JLabel("taste: ");
        JLabel appearanceLabel = new JLabel("appearance: ");
        JLabel qualityLabel = new JLabel("quality: ");
        JLabel cur = new JLabel("Current " + viewDishes());
        newGradedDish.add(cur);
        newGradedDish.add(nameLabel);
        newGradedDish.add(nameField);
        newGradedDish.add(tasteLabel);
        newGradedDish.add(tasteField);
        newGradedDish.add(appearanceLabel);
        newGradedDish.add(appearanceField);
        newGradedDish.add(qualityLabel);
        newGradedDish.add(qualityField);
        int result = JOptionPane.showOptionDialog(
                null, newGradedDish, "Add Graded Dish", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().toLowerCase();
            int taste = Integer.parseInt(tasteField.getText());
            int appearance = Integer.parseInt(appearanceField.getText());
            int quality = Integer.parseInt(qualityField.getText());
            res.addGradedDish(name, taste, appearance, quality);
            JOptionPane.showMessageDialog(null, "New " + viewDishes());
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a dish with input name
    private void addDish() {
        JPanel newDish = new JPanel();
        newDish.setLayout(new GridLayout(3,2));
        JTextField textField = new JTextField(10);
        JLabel label = new JLabel("Enter new dish");
        JLabel cur = new JLabel("Current " + viewDishes());
        newDish.add(cur);
        newDish.add(label);
        newDish.add(textField);
        int result = JOptionPane.showOptionDialog(
                null, newDish, "Add Dish", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            String name = textField.getText().toLowerCase();
            res.addDish(name);
            JOptionPane.showMessageDialog(null, "New " + viewDishes());
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
        }
    }

    // MODIFIES: str
    // EFFECTS: if there is no dishes in the restaurant,
    //              return no dishes,
    //          else return all the name of dish in that restaurant
    private String viewDishes() {
        String str = "";
        if (res.getDishes().size() == 0) {
            str = "There is no dishes in the restaurant!";
        } else {
            str = "Dishes:\n";
            for (Dish d : res.getDishes()) {
                str = str + d.getName() + "\n";
            }
        }
        return str;
    }

    // MODIFIES: this, re
    // EFFECTS: let user enter the name of the restaurant and selected,
    //          doesn't find the restaurant, re-enter the name
    @SuppressWarnings("methodlength")
    private void selectRestaurant() {
        Restaurant re = new Restaurant("");
        boolean exist = false;
        JPanel selectRestaurant = new JPanel();
        selectRestaurant.setLayout(new GridLayout(3,2));
        JTextField textField = new JTextField(10);
        JLabel label = new JLabel("Select a restaurant");
        JLabel cur = new JLabel("Current " + viewRestaurants());
        selectRestaurant.add(cur);
        selectRestaurant.add(label);
        selectRestaurant.add(textField);
        int result = JOptionPane.showOptionDialog(
                null, selectRestaurant, "To Restaurant", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            for (Restaurant r : ra.getRestaurants()) {
                if (r.getName().equals(textField.getText().toLowerCase())) {
                    re = r;
                    exist = true;
                }
            }
            if (exist) {
                res = re;
                JOptionPane.showMessageDialog(null, "Selected: " + res.getName());
                resProcess();
            } else {
                JOptionPane.showMessageDialog(null, "Selected restaurant doesn't exist!");
                selectRestaurant();
            }
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
            upperProcess();
        }

    }

    // MODIFIES: this
    // EFFECTS: add a restaurant with input name
    private void addRestaurant() {
        JPanel newRestaurant = new JPanel();
        newRestaurant.setLayout(new GridLayout(3,2));
        JTextField textField = new JTextField(10);
        JLabel label = new JLabel("Enter new restaurant");
        JLabel cur = new JLabel("Current " + viewRestaurants());
        newRestaurant.add(cur);
        newRestaurant.add(label);
        newRestaurant.add(textField);
        int result = JOptionPane.showOptionDialog(
                null, newRestaurant, "Add Restaurant", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            String name = textField.getText().toLowerCase();
            ra.addRestaurant(name);
            JOptionPane.showMessageDialog(null, "New " + viewRestaurants());
        } else {
            JOptionPane.showMessageDialog(null, "You canceled or entered nothing. Goodbye!");
        }
    }

    // MODIFIES: str
    // EFFECTS: if there is no Restaurant Rating App,
    //              return no restaurants,
    //           else return all the name of the restaurants in the list
    private String viewRestaurants() {
        String str = "";
        if (ra.getRestaurants().size() == 0) {
            str = "There is no restaurants in Restaurant Rating App!";
        } else {
            str = "Restaurants:\n";
            for (Restaurant r : ra.getRestaurants()) {
                str = str + r.getName() + "\n";
            }
        }
        return str;
    }

    // MODIFIES: this, str
    // EFFECTS: saves the ResApp to file and return a message
    private String saveResApp() {
        String str;
        try {
            jsonWriter.open();
            jsonWriter.write(ra);
            jsonWriter.close();
            str = "Saved " + "restaurants" + " to " + JSON_STORE;
        } catch (FileNotFoundException e) {
            str = "Unable to write to file: " + JSON_STORE;
        }
        return str;
    }

    // MODIFIES: this, str
    // EFFECTS: loads ResApp from file and return a message
    private String loadResApp() {
        String str;
        try {
            ra = jsonReader.read();
            str = "Loaded " + "restaurants" + " from " + JSON_STORE;
        } catch (IOException e) {
            str = "Unable to read from file: " + JSON_STORE;
        }
        return str;
    }

    // MODIFIES: this
    // EFFECTS: display ResApp main panel
    @SuppressWarnings("methodlength")
    private void resAppPanel() {
        JPanel raPanel = new JPanel();
        raPanel.setLayout(new GridLayout(7, 1));
        raPanel.add(notationLabel);
        JButton viewRestaurantsButton = new JButton("View Restaurants");
        JButton addRestaurantButton = new JButton("Add Restaurant");
        JButton getIntoRestaurantButton = new JButton("Get Into Restaurant");
        JButton saveButton = new JButton("Save restaurants to File");
        JButton loadButton = new JButton("Load restaurants from File");
        JButton quitButton = new JButton("Quit");
        viewRestaurantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingMain("vr");
                JOptionPane.showMessageDialog(RestaurantRating.this, viewRestaurants());
            }
        });
        addRestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingMain("ar");
            }
        });
        getIntoRestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingMain("tor");
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingMain("s");
                JOptionPane.showMessageDialog(RestaurantRating.this, saveResApp());
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingMain("l");
                JOptionPane.showMessageDialog(RestaurantRating.this, loadResApp());
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(RestaurantRating.this, "Have a good day!");
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
        raPanel.add(viewRestaurantsButton);
        raPanel.add(addRestaurantButton);
        raPanel.add(getIntoRestaurantButton);
        raPanel.add(saveButton);
        raPanel.add(loadButton);
        raPanel.add(quitButton);
        raPanel.setVisible(true);
        setContentPane(raPanel);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: display restaurant panel
    @SuppressWarnings("methodlength")
    private void resPanel() {
        JPanel resPanel = new JPanel();
        resPanel.setLayout(new GridLayout(10, 1));
        resPanel.add(notationLabel);
        JButton viewDishesButton = new JButton("View Dishes");
        JButton addDishButton = new JButton("Add Dish");
        JButton addGradedDishButton = new JButton("Add Graded Dish");
        JButton addEnvironmentScoreButton = new JButton("Add Environment Score");
        JButton viewRestaurantScoreButton = new JButton("View Restaurant Score");
        JButton viewDishesScoreButton = new JButton("View Dishes Score");
        JButton viewEnvironmentScoreButton = new JButton("View Environment Score");
        JButton getIntoDishButton = new JButton("Get into dish");
        JButton backButton = new JButton("Back");
        viewDishesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("vd");
                JOptionPane.showMessageDialog(RestaurantRating.this, viewDishes());
            }
        });
        addDishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("ad");
            }
        });
        addGradedDishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("agd");
            }
        });
        addEnvironmentScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("ae");
            }
        });
        viewRestaurantScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("rs");
                JOptionPane.showMessageDialog(RestaurantRating.this, viewRestaurantScore());
            }
        });
        viewDishesScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("dss");
                JOptionPane.showMessageDialog(RestaurantRating.this, viewDishesScore());
            }
        });
        viewEnvironmentScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("ess");
                JOptionPane.showMessageDialog(RestaurantRating.this, viewEnvironmentScore());
            }
        });
        getIntoDishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingRes("tod");
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upperProcess();
            }
        });
        resPanel.add(viewDishesButton);
        resPanel.add(addDishButton);
        resPanel.add(addGradedDishButton);
        resPanel.add(addEnvironmentScoreButton);
        resPanel.add(viewRestaurantScoreButton);
        resPanel.add(viewDishesScoreButton);
        resPanel.add(viewEnvironmentScoreButton);
        resPanel.add(getIntoDishButton);
        resPanel.add(backButton);
        resPanel.setVisible(true);
        setContentPane(resPanel);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: display dish panel
    @SuppressWarnings("methodlength")
    private void dishPanel() {
        JPanel dishPanel = new JPanel();
        dishPanel.setLayout(new GridLayout(4, 1));
        dishPanel.add(notationLabel);
        JButton addScoreButton = new JButton("Add score to dish");
        JButton viewScoreButton = new JButton("View dish score");
        JButton backButton = new JButton("Back");
        addScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingDish("as");
            }
        });
        viewScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processingDish("ds");
                JOptionPane.showMessageDialog(RestaurantRating.this, viewDishScore());
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resProcess();
            }
        });
        dishPanel.add(addScoreButton);
        dishPanel.add(viewScoreButton);
        dishPanel.add(backButton);
        dishPanel.setVisible(true);
        setContentPane(dishPanel);
        revalidate();
        repaint();
    }

    // EFFECTS: show a rating image for 1 seconds
    private void showRatingScreen() {
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BorderLayout());
        ImageIcon ratingImageIcon = new ImageIcon("data/Rating.jpg");
        JLabel ratingLabel = new JLabel(ratingImageIcon);
        ratingPanel.add(ratingLabel, BorderLayout.CENTER);
        ratingPanel.setVisible(true);
        setContentPane(ratingPanel);
        revalidate();
        repaint();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false); // Execute only once
        timer.start();
    }

    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

}
