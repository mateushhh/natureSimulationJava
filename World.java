import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class World {
    private ArrayList<Organism> organisms;
    private int width;
    private int height;
    public int turnCounter = 0;
    public ArrayList<String> activities;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.organisms = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void executeTurn() {
        activities.clear();
        int numberOfOrganisms = organisms.size();
        for (int i = 0; i < numberOfOrganisms; i++) {
            System.out.println(organisms.get(i).getName() + "[" + organisms.get(i).getStrength() + "]");
            int preX = organisms.get(i).getX();
            int preY = organisms.get(i).getY();
            if(organisms.get(i) != null && organisms.get(i).alive()){
                if(organisms.get(i).action()==Constants.BREED)
                    addOrganism(organisms.get(i).clone());
                for(int j = 0; j < numberOfOrganisms; j++) {
                    if(i!=j) {
                        if(organisms.get(i).getX() == organisms.get(j).getX() && organisms.get(i).getY() == organisms.get(j).getY()) {
                            int result = organisms.get(i).collision(organisms.get(j));
                            if(result == Constants.KILL) {
                                if(organisms.get(j).collision(organisms.get(i)) == Constants.DIES) {
                                    organisms.get(j).die();
                                }
                            }
                            else if (result == Constants.DIES) {
                                organisms.get(i).die();
                            }
                            else if (result == Constants.BREED){
                                if(organisms.get(i).getAge()>10 && organisms.get(j).getAge()>10) {
                                    organisms.get(i).setX(preX);
                                    organisms.get(i).setY(preY);
                                    addOrganism(organisms.get(i).clone());
                                }
                            }
                            else if (result == Constants.DODGE){
                                organisms.get(i).setX(preX);
                                organisms.get(i).setY(preY);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < organisms.size(); i++) {
            if(!organisms.get(i).alive()){
                organisms.remove(i);
            }
        }
        Collections.sort(organisms);
    }

    public void addOrganism(Organism organism) {
        if(organism!=null){
            int x = organism.getX();
            int y = organism.getY();
            if (x >= 0 && x < width && y >= 0 && y < height) {
                organisms.add(organism);
            } else {
                System.out.println("Can't add organism, x or y out of bounds");
            }
        }
    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    public Organism getOrganismAt(int x, int y) {
        for (Organism organism : organisms) {
            if (organism.getX() == x && organism.getY() == y) {
                return organism;
            }
        }
        return null;
    }

    public ArrayList<Organism> getOrganisms() {
        return organisms;
    }

    public void drawOrganisms(Graphics g, GamePanel panel){
        for (Organism organism : organisms) {
            organism.draw(g, panel);
        }
    }

    public void saveGame(String filePath){
        String saveData =  width + ";" + height +";"+ turnCounter + "\n";

        for (int i = 0; i < organisms.size(); i++) {
            if(organisms.get(i).alive()) {
                Organism o = organisms.get(i);
                saveData += o.getName() + ';' + o.getStrength() + ';' + o.getX() + ';' + o.getY() + ';' + o.getAge() + "\n";
            }
        }

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(saveData);
            writer.close();
            System.out.println("Game Saved.");
        }catch (IOException e){
            System.out.println("Error while trying to save the game.");
        }
    }

    public void loadGame(String filePath, Human player) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String[] worldData = reader.readLine().split(";");
            int loadedWidth = Integer.parseInt(worldData[0]);
            int loadedHeight = Integer.parseInt(worldData[1]);
            int loadedTurnCounter = Integer.parseInt(worldData[2]);

            boolean playerFound = false;

            this.width = loadedWidth;
            this.height = loadedHeight;
            this.turnCounter = loadedTurnCounter;
            this.organisms.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] organismData = line.split(";");
                String name = organismData[0];
                int strength = Integer.parseInt(organismData[1]);
                int x = Integer.parseInt(organismData[2]);
                int y = Integer.parseInt(organismData[3]);
                int age = Integer.parseInt(organismData[4]);

                Organism organism = null;
                switch (name) {
                    case "Human":
                        playerFound = true;
                        player.setLife(true);
                        player.setWorld(this);
                        player.setX(x);
                        player.setY(y);
                        player.setStrength(strength);
                        player.setAge(age);
                        addOrganism(player);
                        break;
                    case "Wolf":
                        organism = new Wolf(x, y, this);
                        break;
                    case "Sheep":
                        organism = new Sheep(x, y, this);
                        break;
                    case "Fox":
                        organism = new Fox(x, y, this);
                        break;
                    case "Turtle":
                        organism = new Turtle(x, y, this);
                        break;
                    case "Antilope":
                        organism = new Antilope(x, y, this);
                        break;
                    case "Grass":
                        organism = new Grass(x, y, this);
                        break;
                    case "Dandelion":
                        organism = new Dandelion(x, y, this);
                        break;
                    case "Guarana":
                        organism = new Guarana(x, y, this);
                        break;
                    case "Wolfberries":
                        organism = new Wolfberries(x, y, this);
                        break;
                    case "Borsch":
                        organism = new Borsch(x, y, this);
                        break;
                    default:
                        System.out.println("Unknown organism type: " + name);
                        continue;
                }
                if(organism!=null) {
                    organism.setStrength(strength);
                    organism.setAge(age);
                    addOrganism(organism);
                }
            }

            if(!playerFound)
                player.die();

            System.out.println("Game Loaded.");
        } catch (IOException e) {
            System.out.println("Error while trying to load the game.");
        }
    }
}
