import javax.swing.*;
import java.awt.*;

/**
 * @author: Ilya Lysenko
 * @since: 28.07.13
 */
public class GameFrame extends JFrame {

    GameFrame() {
        super(Game.CAPTION);

        GameCanvas canvas = new GameCanvas();
        canvas.setSize(Game.SIZE, Game.SIZE);
        add(canvas);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screen_width = screenSize.getWidth();
        double screen_height = screenSize.getHeight();
        int x = (int) screen_width / 2 - Game.SIZE / 2;
        int y = (int) screen_height / 2 - Game.SIZE / 2;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        //Использовать двойной буфер для перерисовки канвы
        canvas.createBufferStrategy(2);
    }
}
