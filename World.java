import java.awt.*;
import java.util.ArrayList;

public class World {
    private ArrayList<Organism> organisms;
    private final int width;
    private final int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.organisms = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //add breed and add Activity Log
    public void executeTurn() {
        int numberOfOrganisms = organisms.size();
        for (int i = 0; i < numberOfOrganisms; i++) {
            if(organisms.get(i) != null && organisms.get(i).alive()){
                organisms.get(i).action();
                for(int j = 0; j < numberOfOrganisms; j++) {
                    if(i!=j) {
                        if(organisms.get(i).getX() == organisms.get(j).getX() && organisms.get(i).getY() == organisms.get(j).getY()) {
                            int result = organisms.get(i).collision(organisms.get(j));
                            if(result == Constants.KILL)
                                organisms.get(j).die();
                            else if (result == Constants.DIES)
                                organisms.get(i).die();
                            else if (result == Constants.BREED){
                               //to do
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
    }

    public void addOrganism(Organism organism) {
        int x = organism.getX();
        int y = organism.getY();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            organisms.add(organism);
        } else {
            System.out.println("Can't add organism, x or y out of bounds");
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

    public void drawOrganisms(Graphics g, GamePanel panel){
        for (Organism organism : organisms) {
            organism.draw(g, panel);
        }
    }
}
