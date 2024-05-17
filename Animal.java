import java.awt.*;
import java.util.Random;

public class Animal extends Organism {

    public Animal(String name, int strength, int initiative, int x, int y, World world) {
        super(name, strength, initiative, x, y, world);
    }

    @Override
    public Animal clone() {
        Animal newAnimal = new Animal(this.name, this.strength, this.initiative, this.x, this.y, this.world);
        newAnimal.setAge(0);
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(i > 0 && j > 0 && i < world.getWidth() && j < world.getHeight()) {
                    if(world.getOrganismAt(i,j)==null){
                        this.setX(i);
                        this.setY(j);
                        world.activities.add(this.getName() + " breed.");
                        System.out.println(this.getName() + " breed.");
                        return newAnimal;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int action() {
        Random rand = new Random();
        int direction = rand.nextInt(4) + 1;
        int preX = getX();
        int preY = getY();

        switch (direction) {
            case Constants.UP:
                if (getY() > 0)
                    setY(getY() - 1);
                break;
            case Constants.LEFT:
                if (getX() > 0)
                    setX(getX() - 1);
                break;
            case Constants.DOWN:
                if (getY() < world.getHeight() - 1)
                    setY(getY() + 1);
                break;
            case Constants.RIGHT:
                if (getX() < world.getWidth() - 1)
                    setX(getX() + 1);
                break;
        }

        if (preX == getX() && preY == getY()) {
            return action();
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
            world.activities.add(otherOrganism.getName() + " killed " + this.getName() + ".");
            System.out.println(otherOrganism.getName() + " killed " + this.getName() + ".");
            return Constants.DIES;
        }
        return 0;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.RED);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
