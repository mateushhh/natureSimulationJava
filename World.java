import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class World {
    private ArrayList<Organism> organisms;
    private final int width;
    private final int height;
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
}
