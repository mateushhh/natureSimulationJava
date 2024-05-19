import java.awt.*;
import java.util.Random;

public class Plant extends Organism {

    public Plant(String name, int strength, int x, int y, World world) {
        super(name, strength, 0, x, y, world);
    }

    @Override
    public Plant clone() {
        Random rand = new Random();
        int direction = rand.nextInt(4) + 1;
        switch (direction) {
            case Constants.UP:
                if (getY() > 0)
                    if(world.getOrganismAt(getX(),getY()-1)==null)
                        return spread(getX(),getY()-1);
                break;
            case Constants.LEFT:
                if (getX() > 0)
                    if(world.getOrganismAt(getX() - 1,getY())==null)
                        return spread(getX() - 1,getY());
                break;
            case Constants.DOWN:
                if (getY() < world.getHeight() - 1)
                    if(world.getOrganismAt(getX(),getY() + 1)==null)
                        return spread(getX(),getY() + 1);
                break;
            case Constants.RIGHT:
                if (getX() < world.getWidth() - 1)
                    if(world.getOrganismAt(getX() + 1,getY())==null)
                        return spread(getX() + 1,getY());
                break;
        }
        return null;
    }

    public Plant spread(int x, int y) {
        Plant newPlant = new Plant(this.name, this.strength, x, y, this.world);
        newPlant.setAge(0);
    return newPlant;
    }

    @Override
    public int action() {
        Random rand = new Random();
        int spreadChance = rand.nextInt(100);

        if(spreadChance<5){
            this.setAge(this.getAge() + 1);
            return Constants.BREED;
        }

        this.setAge(this.getAge() + 1);
        return Constants.NOTHING;
    }

    @Override
    public int collision(Organism otherOrganism) {
        if (this.name.equals(otherOrganism.getName())) {
            return Constants.BREED;
        } else if (this.getStrength() >= otherOrganism.getStrength()) {
            world.activities.add(this.getName() + " killed " + otherOrganism.getName() + ".");
            System.out.println(this.getName() + " killed " + otherOrganism.getName() + ".");
            return Constants.KILL;
        } else if (this.getStrength() < otherOrganism.getStrength()) {
            return Constants.DIES;
        }
        return 0;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.GREEN);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.WHITE);
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
