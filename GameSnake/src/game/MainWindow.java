package game;

import javax.swing.*;

public class MainWindow extends JFrame {

    MainWindow() {
        setTitle("GameSnake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        
        setSize(640+16, 640+39);
        setLocation(1800, 900);

        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}