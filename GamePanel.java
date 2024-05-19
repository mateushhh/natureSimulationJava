import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

class GamePanel extends JPanel implements KeyListener {
    private World world;
    private final int margin = 10;
    private final int topMargin = 32;
    private int cellSize;
    private Human player;
    private JButton nextTurnButton;
    private JButton loadGameButton;
    private JButton saveGameButton;
    private JButton quitGameButton;

    public GamePanel(int worldWidth, int worldHeight, boolean newGame) {
        if(newGame) {
            world = new World(worldWidth, worldHeight);

            Random rand = new Random();
            int posX = rand.nextInt(worldWidth);
            int posY = rand.nextInt(worldHeight);
            player = new Human(posX, posY, world);
            world.addOrganism(player);

            int organismType;

            //Fill world with Animals
            for(int i = 0; i < worldWidth*worldHeight*0.07 ; i++) {
                posX = rand.nextInt(worldWidth);
                posY = rand.nextInt(worldHeight);

                organismType = rand.nextInt(5);
                Organism newOrganism = null;

                if(world.getOrganismAt(posX,posY)==null){
                    if(organismType==0)
                        newOrganism = new Antilope(posX,posY,world);
                    else if(organismType==1)
                        newOrganism = new Fox(posX,posY,world);
                    else if(organismType==2)
                        newOrganism = new Sheep(posX,posY,world);
                    else if(organismType==3)
                        newOrganism = new Turtle(posX,posY,world);
                    else if(organismType==4)
                        newOrganism = new Wolf(posX,posY,world);

                    world.addOrganism(newOrganism);
                }
            }
            for(int i = 0; i < worldWidth*worldHeight*0.03 ; i++) {
                posX = rand.nextInt(worldWidth);
                posY = rand.nextInt(worldHeight);

                organismType = rand.nextInt(5);
                Organism newOrganism = null;

                if(world.getOrganismAt(posX,posY)==null){
                    if(organismType==0)
                        newOrganism = new Borsch(posX,posY,world);
                    else if(organismType==1)
                        newOrganism = new Dandelion(posX,posY,world);
                    else if(organismType==2)
                        newOrganism = new Grass(posX,posY,world);
                    else if(organismType==3)
                        newOrganism = new Guarana(posX,posY,world);
                    else if(organismType==4)
                        newOrganism = new Wolfberries(posX,posY,world);

                    world.addOrganism(newOrganism);
                }
            }
        } else if (newGame==false) {
            world = new World(worldWidth, worldHeight);
            player = new Human(0,0,world);
        }

        setLayout(new FlowLayout(FlowLayout.LEFT));

        nextTurnButton = new JButton("NEXT TURN");
        saveGameButton = new JButton("SAVE GAME");
        loadGameButton = new JButton("LOAD GAME");
        quitGameButton = new JButton("QUIT GAME");

        nextTurnButton.addKeyListener(this);
        saveGameButton.addKeyListener(this);
        loadGameButton.addKeyListener(this);

        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeTurn();
            }
        });
        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(GamePanel.this);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getName();
                    world.saveGame(fileName);
                }
            }
        });
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(GamePanel.this);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getName();
                    world.loadGame(fileName, player);
                    repaint();
                }
            }
        });
        quitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(nextTurnButton);
        add(loadGameButton);
        add(saveGameButton);
        add(quitGameButton);

        setFocusable(true);
        addKeyListener(this);
    }

    public World getWorld() {
        return world;
    }

    public Human getPlayer(){
        return player;
    }

    public int getMargin() {
        return margin;
    }

    public int getTopMargin(){
        return topMargin;
    }

    public int getCellSize() {
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
        int panelHeight = getHeight() - 2 * margin - topMargin;

        cellSize = Math.min(panelWidth / 2 / world.getWidth(), panelHeight / world.getHeight());

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                g.setColor(Color.WHITE);
                g.fillRect(i * cellSize + margin, j * cellSize + margin + topMargin, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(i * cellSize + margin, j * cellSize + margin + topMargin, cellSize, cellSize);
            }
        }

        world.drawOrganisms(g, this);
    }

    private void drawTurnCounter(Graphics g) {
        int margin = 10;
        int panelWidth = getWidth() - 2 * margin;

        g.setColor(Color.RED);
        g.drawString("Activity Log:", panelWidth / 2 + 2 * margin, 2 * margin + topMargin);
        g.drawString("Turn: " + world.turnCounter, panelWidth / 2 + 2 * margin, 2 * margin + 16 + topMargin);
        if(player.alive()){
            if(player.specialCooldown > 5)
                g.drawString("Special Ability: ACTIVE", panelWidth / 2 + 2 * margin, 2 * margin + 16*2 + topMargin);
            else if(player.specialCooldown <= 5 && player.specialCooldown > 0){
                g.drawString("Special Ability: CHARGING (Wait " + player.specialCooldown + " turns to use it)", panelWidth / 2 + 2 * margin, 2 * margin + 16*2 + topMargin);
            }
            else{
                g.drawString("Special Ability: READY (Press E to use it)", panelWidth / 2 + 2 * margin, 2 * margin + 16*2 + topMargin);
            }
        }
        if (world.getActivities() != null) {
            for (int i = 0; i < world.getActivities().size(); i++) {
                g.drawString(world.getActivities().get(i), panelWidth / 2 + 2 * margin, 2 * margin + 16 * (i + 3) + topMargin);
            }
        }
    }

    private void executeTurn() {
        System.out.println(world.turnCounter);
        world.executeTurn();
        world.turnCounter++;
        repaint();
        player.movementLock = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            executeTurn();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (!player.movementLock) {
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
            else if (e.getKeyCode() == KeyEvent.VK_E && player.specialCooldown <= 0) {
                player.special();
            }
        }
    }

        @Override
        public void keyReleased(KeyEvent e) {}
}
