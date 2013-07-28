import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 * @author: Ilya Lysenko
 * @since: 28.07.13
 */

public class GameCanvas extends Canvas {

    public static final int CELL_SIZE = Game.CELL_SIZE;

    boolean repaintInProgress = false;
    Random random = new Random();
    Cell cells[][] = new Cell[Game.CELLS_COUNT][Game.CELLS_COUNT];
    boolean firstStep = true;

    GameCanvas() {
        for (int i = 0; i < Game.CELLS_COUNT; i++) {
            for (int j = 0; j < Game.CELLS_COUNT; j++) {
                Cell cell = new Cell();
                cell.setAlive(random.nextInt() % 2 == 0);
                cells[i][j] = cell;
            }
        }
        setIgnoreRepaint(true);
        TimerListener timerListener = new TimerListener(this);
        new Timer(Game.TIME_INTERVAL, timerListener).start();
    }

    public void repaintCanvas() {
        if (repaintInProgress)
            return;
        repaintInProgress = true;

        BufferStrategy strategy = getBufferStrategy();
        Graphics g = strategy.getDrawGraphics();

        if (!firstStep) {
            updateCells();
        }

        draw(g);

        if (g != null)
            g.dispose();
        strategy.show();
        Toolkit.getDefaultToolkit().sync();

        firstStep = false;
        repaintInProgress = false;
    }

    private void updateCells() {
        for (int i = 0; i < Game.CELLS_COUNT; i++) {
            for (int j = 0; j < Game.CELLS_COUNT; j++) {
                int aliveNeighbours = 0;

                Cell cell = cells[i][j];

                Cell leftNeighbour = getLeftNeighbour(i, j);
                Cell rightNeighbour = getRightNeighbour(i, j);
                Cell topNeighbour = getTopNeighbour(i, j);
                Cell topLeftNeighbour = getTopLeftNeighbour(i, j);
                Cell topRightNeighbour = getTopRightNeighbour(i, j);
                Cell bottomNeighbour = getBottomNeighbour(i, j);
                Cell bottomLeftNeighbour = getBottomLeftNeighbour(i, j);
                Cell bottomRightNeighbour = getBottomRightNeighbour(i, j);

                if (leftNeighbour != null && leftNeighbour.isAlive())
                    aliveNeighbours++;
                if (rightNeighbour != null && rightNeighbour.isAlive())
                    aliveNeighbours++;
                if (topNeighbour != null && topNeighbour.isAlive())
                    aliveNeighbours++;
                if (topLeftNeighbour != null && topLeftNeighbour.isAlive())
                    aliveNeighbours++;
                if (topRightNeighbour != null && topRightNeighbour.isAlive())
                    aliveNeighbours++;
                if (bottomNeighbour != null && bottomNeighbour.isAlive())
                    aliveNeighbours++;
                if (bottomLeftNeighbour != null && bottomLeftNeighbour.isAlive())
                    aliveNeighbours++;
                if (bottomRightNeighbour != null && bottomRightNeighbour.isAlive())
                    aliveNeighbours++;

                if (!cell.isAlive())
                    cell.setAlive(aliveNeighbours == 3);
                else if (cell.isAlive())
                    cell.setAlive(aliveNeighbours == 2 || aliveNeighbours == 3);
            }
        }
    }

    private Cell getTopNeighbour(int i, int j) {
        Cell cell = null;
        if (i > 0) {
            cell = cells[i - 1][j];
        } else if (i == 0)
            cell = cells[Game.CELLS_COUNT - 1][j];
        return cell;
    }

    private Cell getBottomNeighbour(int i, int j) {
        Cell cell = null;
        if (i < Game.CELLS_COUNT - 1)
            cell = cells[i + 1][j];
        else if (i == Game.CELLS_COUNT - 1)
            cell = cells[0][j];
        return cell;
    }

    private Cell getLeftNeighbour(int i, int j) {
        Cell cell = null;
        if (j > 0)
            cell = cells[i][j - 1];
        else if (j == 0)
            cell = cells[i][Game.CELLS_COUNT - 1];
        return cell;
    }

    private Cell getRightNeighbour(int i, int j) {
        Cell cell = null;
        if (j < Game.CELLS_COUNT - 1)
            cell = cells[i][j + 1];
        else if (j == Game.CELLS_COUNT - 1)
            cell = cells[i][0];
        return cell;
    }

    private Cell getTopLeftNeighbour(int i, int j) {
        Cell cell = null;
        if (i > 0 && j > 0)
            cell = cells[i - 1][j - 1];
        else if (i == 0 && j > 0)
            cell = cells[Game.CELLS_COUNT - 1][j - 1];
        else if (i > 0 && j == 0)
            cell = cells[i - 1][Game.CELLS_COUNT - 1];
        else if (i == 0 && j == 0)
            cell = cells[Game.CELLS_COUNT - 1][Game.CELLS_COUNT - 1];
        return cell;
    }

    private Cell getTopRightNeighbour(int i, int j) {
        Cell cell = null;
        if (i > 0 && j < Game.CELLS_COUNT - 1)
            cell = cells[i - 1][j + 1];
        else if (i == 0 && j < Game.CELLS_COUNT - 1)
            cell = cells[Game.CELLS_COUNT - 1][j + 1];
        else if (i > 0 && j == Game.CELLS_COUNT - 1)
            cell = cells[i - 1][0];
        else if (i == 0 && j == Game.CELLS_COUNT - 1)
            cell = cells[Game.CELLS_COUNT - 1][0];
        return cell;
    }

    private Cell getBottomLeftNeighbour(int i, int j) {
        Cell cell = null;
        if (i < Game.CELLS_COUNT - 1 && j > 0)
            cell = cells[i + 1][j - 1];
        else if (i == Game.CELLS_COUNT - 1 && j > 0)
            cell = cells[0][j - 1];
        else if (i < Game.CELLS_COUNT - 1 && j == 0)
            cell = cells[i + 1][Game.CELLS_COUNT - 1];
        else if (i == Game.CELLS_COUNT - 1 && j == 0)
            cell = cells[0][Game.CELLS_COUNT - 1];
        return cell;
    }

    private Cell getBottomRightNeighbour(int i, int j) {
        Cell cell = null;
        if (i < Game.CELLS_COUNT - 1 && j < Game.CELLS_COUNT - 1)
            cell = cells[i + 1][j + 1];
        else if (i == Game.CELLS_COUNT - 1 && j < Game.CELLS_COUNT - 1)
            cell = cells[0][j + 1];
        else if (i < Game.CELLS_COUNT - 1 && j == Game.CELLS_COUNT - 1)
            cell = cells[i + 1][0];
        else if (i == Game.CELLS_COUNT - 1 && j == Game.CELLS_COUNT - 1)
            cell = cells[0][0];
        return cell;
    }

    private void draw(Graphics g) {
        clearCanvas(g);
        drawGrid(g);
        drawCells(g);
    }

    private void drawCells(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < Game.CELLS_COUNT; i++) {
            for (int j = 0; j < Game.CELLS_COUNT; j++) {
                Cell cell = cells[i][j];
                if (cell.isAlive()) {
                    int x = i * CELL_SIZE;
                    int y = j * CELL_SIZE;
                    g.fillRect(x + 1, y + 1, Game.CELL_SIZE - 1, Game.CELL_SIZE - 1);
                }
            }
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);

        for (int i = 0; i < Game.CELLS_COUNT; i++) {
            int pos = i * CELL_SIZE;
            g.drawLine(0, pos, Game.SIZE, pos);
            g.drawLine(pos, 0, pos, Game.SIZE);
        }
    }

    private void clearCanvas(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Game.SIZE, Game.SIZE);
    }
}
