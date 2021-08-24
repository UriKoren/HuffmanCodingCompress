import java.awt.*;

public class Runner {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.getContentPane().setLayout(new GridLayout(10, 1));
        while (frame.file == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }
}
