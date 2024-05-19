import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JFrame {
    public MenuPanel() {
        setTitle("GAME MENU - Mateusz Grzonka s198023");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton newGameButton = new JButton("NEW GAME");
        JButton loadGameButton = new JButton("LOAD GAME");
        JButton quitButton = new JButton("QUIT");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                startNewGame();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loadGame();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(newGameButton);
        panel.add(loadGameButton);
        panel.add(quitButton);

        add(panel);
        setVisible(true);
    }

    private void startNewGame() {
        String widthInput = JOptionPane.showInputDialog("Enter the width of the world:");
        String heightInput = JOptionPane.showInputDialog("Enter the height of the world:");
        int width, height;
        try {
            width = Integer.parseInt(widthInput);
            height = Integer.parseInt(heightInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid integers.");
            return;
        }

        if (width <= 0 || height <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter positive integers.");
            return;
        }

        GamePanel gamePanel = new GamePanel(width, height,true);
        JFrame gameFrame = new JFrame("GAME - Mateusz Grzonka s198023");
        gameFrame.add(gamePanel);
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gamePanel.requestFocus();
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(MenuPanel.this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getName();
            GamePanel gamePanel = new GamePanel(1, 1, false);
            gamePanel.getWorld().loadGame(fileName, gamePanel.getPlayer());
            JFrame gameFrame = new JFrame("GAME - Mateusz Grzonka s198023");
            gameFrame.add(gamePanel);
            gameFrame.setSize(800, 600);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            gamePanel.requestFocus();
            dispose();
        }
    }

}
