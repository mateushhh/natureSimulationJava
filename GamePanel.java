import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePanel extends JPanel implements KeyListener {
    private World world;
    private int turnCounter = 0;
    private final int margin = 10;
    private int cellSize;

    public GamePanel() {
        world = new World(5, 5);
        world.addOrganism(new Wolf("Wolf", 3, 4, world));
        world.addOrganism(new Animal("Test", 2, 2, 2, 2, world));

        setFocusable(true);
        addKeyListener(this);
    }

    public int getMargin(){
        return margin;
    }
    public int getCellSize(){
        return cellSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWorld(g);
        drawTurnCounter(g);
    }

    private void drawWorld(Graphics g) {
        int panelWidth = getWidth() - 2 * margin;
        int panelHeight = getHeight() - 2 * margin;

        cellSize = Math.min(panelWidth/2 / world.getWidth(), panelHeight / world.getHeight());

        //Draw board gridlines
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                g.setColor(Color.WHITE);
                g.fillRect(i * cellSize + margin, j * cellSize + margin, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(i * cellSize + margin, j * cellSize + margin, cellSize, cellSize);
            }
        }

        //Fill in board with organisms
        world.drawOrganisms(g, this);
    }

    private void drawTurnCounter(Graphics g) {
        int margin = 10;
        int panelWidth = getWidth() - 2 * margin;

        g.setColor(Color.RED);
        g.drawString("Activity Log:", panelWidth / 2 + 2 * margin, 2 * margin);
        g.drawString("Turn: " + turnCounter, panelWidth / 2 + 2 * margin, 2 * margin + 16);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            world.executeTurn();
            turnCounter++;
            repaint();


        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
