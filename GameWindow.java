/**
 * Java 1. HomeWork #7. Feed the cats
 * @author Yury Mitroshin
 * @version dated Dec 15, 2017
 * @link https://github.com/yurchess/gb_homework7
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow {
    private Plate plate;
    private JProgressBar pb_Plate;
    private Cat[] cats = new Cat[5];
    private JProgressBar[] pb_CatsAppetites = new JProgressBar[5];

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.go();
    }

    private void go() {
        JFrame frame = new JFrame("Feed the Cat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton btnFeedCats = new JButton("Feed the cats");
        btnFeedCats.addActionListener(new FeedListener());

        JButton btnAddFood = new JButton("Add food");
        btnAddFood.addActionListener(new FoodListener());

        JButton btnStarveTheCats = new JButton("Starve the cats");
        btnStarveTheCats.addActionListener(new StarveBtnListener());

        JPanel pnlForButtons = new JPanel();
        frame.getContentPane().add(BorderLayout.NORTH, pnlForButtons);

        pnlForButtons.add(btnFeedCats);
        pnlForButtons.add(btnAddFood);
        pnlForButtons.add(btnStarveTheCats);

        plate = new Plate(300, 1000);
        pb_Plate = new JProgressBar(0, plate.getFoodCapacity());
        pb_Plate.setForeground(Color.GREEN);
        pb_Plate.setValue(plate.getFoodAmount());
        frame.getContentPane().add(BorderLayout.CENTER, pb_Plate);

        createCats();
        JPanel catsPanel = new JPanel();
        catsPanel.setLayout(new BoxLayout(catsPanel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(BorderLayout.SOUTH, catsPanel);
        createProgressBars(catsPanel);
        showCatsAppetites();

        frame.setLocation(200, 100);
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createCats() {
        for (int i = 0; i < cats.length; i++) {
            cats[i] = new Cat(500);
            cats[i].starveTheCat();
        }
    }

    private void showCatsAppetites() {
        for (int i = 0; i < pb_CatsAppetites.length; i++)
            pb_CatsAppetites[i].setValue(cats[i].getAppetite());
    }

    private void createProgressBars(JPanel parentPnl) {
        for (int i = 0; i < pb_CatsAppetites.length; i++) {
            pb_CatsAppetites[i] = new JProgressBar(0, cats[i].getMaxAppetite());
            pb_CatsAppetites[i].setForeground(Color.RED);
            parentPnl.add(pb_CatsAppetites[i]);
        }
    }

    class FeedListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (plate.getFoodAmount() > 0) {
                for (Cat cat : cats)
                    if (cat.wantToEat())
                        cat.Eat(plate);
                showCatsAppetites();
                pb_Plate.setValue(plate.getFoodAmount());
            }
        }
    }

    class FoodListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            plate.addFood(100);
            pb_Plate.setValue(plate.getFoodAmount());
        }
    }

    class StarveBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            for (Cat cat : cats) {
                cat.starveTheCat();
            }
            showCatsAppetites();
        }
    }
}

class Cat {
    private int appetite;
    private int maxAppetite;

    Cat(int maxAppetite) {
        this.maxAppetite = maxAppetite;
        starveTheCat();
    }

    public void Eat(Plate plate) {
        appetite -= plate.decreaseFood(appetite);
    }

    public int getAppetite() {
        return appetite;
    }

    public int getMaxAppetite() {
        return maxAppetite;
    }

    public void starveTheCat() {
        appetite = (int) (Math.random() * maxAppetite);
    }

    public boolean wantToEat() {
        return !(appetite == 0);
    }
}

class Plate {
    private int foodCapacity;
    private int foodAmount;

    Plate(int foodAmount, int foodCapacity) {
        this.foodAmount = foodAmount;
        this.foodCapacity = foodCapacity;
    }

    public void addFood(int foodAmount) {
        this.foodAmount += foodAmount;
        if (this.foodAmount > foodCapacity) this.foodAmount = foodCapacity;
    }

    public int decreaseFood(int foodAmount) {
        if (foodAmount < 0) return 0;
        if (foodAmount <= this.foodAmount) {
            this.foodAmount -= foodAmount;
            return foodAmount;
        } else {
            int decreasedFood = this.foodAmount;
            this.foodAmount = 0;
            return decreasedFood;
        }
    }

    public int getFoodCapacity() {
        return foodCapacity;
    }

    public int getFoodAmount() {
        return foodAmount;
    }
}