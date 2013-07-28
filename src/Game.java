import javax.swing.*;

/**
 * @author: Ilya Lysenko
 * @since: 27.07.13
 */
public class Game {

    public static final String CAPTION = "Game \"Life\"";
    public static final int SIZE = 400;
    public static final int CELLS_COUNT = 40;
    public static final int CELL_SIZE = Game.SIZE / Game.CELLS_COUNT;
    public static final int TIME_INTERVAL = 200;

    public static void main(String[] args) {
        System.out.println("Cell size = " + CELL_SIZE);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame();
            }
        });
    }
}
