import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Ilya Lysenko
 * @since: 28.07.13
 */

public class TimerListener implements ActionListener {

    GameCanvas gc;

    TimerListener(GameCanvas gc) {
        this.gc = gc;
    }

    public void actionPerformed(ActionEvent arg0) {
        gc.repaintCanvas();
    }
}


