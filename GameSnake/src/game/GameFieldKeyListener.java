package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFieldKeyListener extends KeyAdapter {
    Direction direction;

    GameFieldKeyListener(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (direction.where != Where.LEFT) direction.where = Where.RIGHT;
                break;
            case KeyEvent.VK_LEFT:
            if (direction.where != Where.RIGHT) direction.where = Where.LEFT;
                break;
            case KeyEvent.VK_UP:
                if (direction.where != Where.DOWN) direction.where = Where.UP;
                break;
            case KeyEvent.VK_DOWN:
            if (direction.where != Where.UP) direction.where = Where.DOWN;
                break;
        }
    }

}
