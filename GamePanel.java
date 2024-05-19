import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePanel extends JPanel implements KeyListener {
    private World world;
    private int turnCounter = 0;
    private final int margin = 10;
    private int cellSize;
    private Human player;

    public GamePanel() {
        world = new World(10, 10);
        player = new Human(3,8,world);
        world.addOrganism(player);
        world.addOrganism(new Wolf(3, 4, world));
        world.addOrganism(new Wolf(4, 4, world));
        world.addOrganism(new Sheep(1, 1, world));
        world.addOrganism(new Sheep(0, 0, world));
        world.addOrganism(new Fox(3, 2, world));
        world.addOrganism(new Fox(4, 0, world));
        world.addOrganism(new Turtle(7, 4, world));
        world.addOrganism(new Turtle(9, 5, world));
        world.addOrganism(new Antilope(4, 6, world));
        world.addOrganism(new Antilope(5, 6, world));
        world.addOrganism(new Grass( 1,9, world));
        world.addOrganism(new Dandelion(8,6, world));
        world.addOrganism(new Guarana(5,5, world));
        world.addOrganism(new Wolfberries(0,5, world));
        world.addOrganism(new Borsch(7, 9, world));

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

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                g.setColor(Color.WHITE);
                g.fillRect(i * cellSize + margin, j * cellSize + margin, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(i * cellSize + margin, j * cellSize + margin, cellSize, cellSize);
            }
        }

        world.drawOrganisms(g, this);
    }

    private void drawTurnCounter(Graphics g) {
        int margin = 10;
        int panelWidth = getWidth() - 2 * margin;

        g.setColor(Color.RED);
        g.drawString("Activity Log:", panelWidth / 2 + 2 * margin, 2 * margin);
        g.drawString("Turn: " + turnCounter, panelWidth / 2 + 2 * margin, 2 * margin + 16);
        if(world.getActivities()!=null){
            for(int i = 0; i < world.getActivities().size(); i++){
                g.drawString(world.getActivities().get(i), panelWidth / 2 + 2 * margin, 2 * margin + 16*(i+2));
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println(turnCounter);
            world.executeTurn();
            turnCounter++;
            repaint();
            player.movementLock = false;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (!player.movementLock){
            if (e.getKeyCode() == KeyEvent.VK_W) {
                player.move(Constants.UP);
                player.movementLock = true;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                player.move(Constants.LEFT);
                player.movementLock = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                player.move(Constants.DOWN);
                player.movementLock = true;
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                player.move(Constants.RIGHT);
                player.movementLock = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}