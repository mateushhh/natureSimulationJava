import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulation - Mateusz Grzonka s198023");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1200, 900);
        frame.add(new GamePanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
