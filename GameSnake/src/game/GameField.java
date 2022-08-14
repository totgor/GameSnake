package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int DOT_SIZE = 32;
    private final int SPEED = 250;
    private final int MAX_SIZE_SNAKE = 400; // 20x20
    private final int SIZE_FIELD = 640;
    private boolean gameOver = false;

    private Image apple;
    private Image dotHead;
    private Image dotBody;

    private int dots = 3;

    private int appleX;
    private int appleY;

    private int[] dotX = new int[MAX_SIZE_SNAKE];
    private int[] dotY = new int[MAX_SIZE_SNAKE];

    private Direction direction;// направление

    Timer timer;

    // конструктор класса
    // ################################################################################################################
    GameField() {
        setBackground(Color.BLACK);
        ImageIcon iia = new ImageIcon("src/game/apple.png"); // грузим картинки в объекты Image
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("src/game/dotHead.png");
        dotHead = iid.getImage();
        ImageIcon iidb = new ImageIcon("src/game/dotBody.png");
        dotBody = iidb.getImage();
        initGame();
        addKeyListener(new GameFieldKeyListener(direction)); // добавляем просулшиватель нажатия клавишь
        setFocusable(true); // устанавливаем фокусировку, иначе клавиши не прослушиваются на поле с игрой
    }

    // инициализация игры
    // ################################################################################################################
    public void initGame() {
        timer = new Timer(SPEED, this); // запускакем таймер
        timer.start();

        createRandomApple(); // первое рандомное положение яблока

        for (int i = 0; i < dots; i++) { // стартовое положение змейки
            dotX[i] = DOT_SIZE * 2 - DOT_SIZE * i;
            dotY[i] = 0;
        }

        direction = new Direction(); // класс для направления
    }

    // создаем рандомно яблоко
    // ################################################################################################################
    public void createRandomApple() {
        // boolean bodySnake = false;
        // do {
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;
        //     for (int i = 0; i < dots; i++) {
        //         if (dotX[i] == appleX && dotY[i] == appleY)
        //             bodySnake = true;
        //             continue;                    
        //     }
        // } while (bodySnake == true);
    }

    // встретили ли мы яблоко
    // ################################################################################################################
    public void checkBiteApple() {
        if (dotX[0] == appleX && dotY[0] == appleY) {
            dots++;
            createRandomApple();
        }
    }

    // проверка проигрыша
    // ################################################################################################################
    public boolean gameOver() {
        if (dotX[0] == SIZE_FIELD) // проверяем края полей
            return true;

        if (dotX[0] < 0)
            return true;

        if (dotY[0] == SIZE_FIELD)
            return true;

        if (dotY[0] < 0)
            return true;

        for (int i = dots; i > 0; i--) {
            if (dots >= 4 && dotX[0] == dotX[i] && dotY[0] == dotY[i])
                return true;
        }

        return false;
    }

    // ################################################################################################################
    // сдвинуть змейку
    public void shiftSnake() {
        for (int i = dots; i > 0; i--) { // остальные ячейки перезанимают места друг друга
            dotX[i] = dotX[i - 1];
            dotY[i] = dotY[i - 1];
        }

        switch (direction.where) { // на новое место перемещается только первая ячейка dot
            case RIGHT:
                dotX[0] += DOT_SIZE;
                break;
            case LEFT:
                dotX[0] -= DOT_SIZE;
                break;
            case UP:
                dotY[0] -= DOT_SIZE;
                break;
            case DOWN:
                dotY[0] += DOT_SIZE;
                break;
        }
    }

    // перересовывает компоненты каждые SPEED миллисекнуд, запуская paintComponent()
    // ################################################################################################################
    @Override
    public void actionPerformed(ActionEvent e) {
        gameOver = gameOver(); // игра проиграна
        if (gameOver == false) {
            checkBiteApple(); // встретили ли мы яблоко
            shiftSnake(); // сдвинуть змейку
        }
        repaint(); // перерисовываем
    }

    // рисует компоненты
    // ################################################################################################################
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        

        if (gameOver == false) {
            g.drawImage(apple, appleX, appleY, this); // рисуем картинку яблока

            g.drawImage(dotHead, dotX[0], dotY[0], this); // рисуем голову
            for (int i = 1; i < dots; i++) { // рисуем тело
                g.drawImage(dotBody, dotX[i], dotY[i], this);
            }
        } else {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(60f));
            g.drawString("Game Over.", (int) (SIZE_FIELD * 0.25), SIZE_FIELD / 2);
        }
    }
}
