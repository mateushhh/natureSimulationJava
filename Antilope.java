import java.awt.*;
import java.util.Random;

public class Antilope extends Animal {
    public Antilope(int x, int y, World world) {
        super("Antilope", 4, 4, x, y, world);
    }

    @Override
    public Antilope clone() {
        Antilope newAntilope = new Antilope(this.x, this.y, this.world);
        newAntilope.setAge(0);
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(i >= 0 && j >= 0 && i < world.getWidth() && j < world.getHeight()) {
                    if(world.getOrganismAt(i,j)==null){
                        this.setX(i);
                        this.setY(j);
                        world.activities.add(this.getName() + " breed.");
                        System.out.println(this.getName() + " breed.");
                        return newAntilope;
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
                if (getY() >= 2)
                    setY(getY() - 2);
                break;
            case Constants.LEFT:
                if (getX() >= 2)
                    setX(getX() - 2);
                break;
            case Constants.DOWN:
                if (getY() < world.getHeight() - 2)
                    setY(getY() + 2);
                break;
            case Constants.RIGHT:
                if (getX() < world.getWidth() - 2)
                    setX(getX() + 2);
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
        Random rand = new Random();
        int dodge = rand.nextInt(2);

        if (this.name.equals(otherOrganism.getName())) {
            return Constants.BREED;
        } else if (this.getStrength() >= otherOrganism.getStrength()) {
            world.activities.add(this.getName() + " killed " + otherOrganism.getName() + ".");
            System.out.println(this.getName() + " killed " + otherOrganism.getName() + ".");
            return Constants.KILL;
        } else if (this.getStrength() < otherOrganism.getStrength()) {
            if(dodge == 0) {
                world.activities.add(otherOrganism.getName() + " killed " + this.getName() + ".");
                System.out.println(otherOrganism.getName() + " killed " + this.getName() + ".");
                return Constants.DIES;
            }
            else{
                world.activities.add(this.getName() + " dodged " + otherOrganism.getName() + " attack.");
                System.out.println(this.getName() + " dodged " + otherOrganism.getName() + " attack.");
                for(int i = x-1; i <= x+1; i++) {
                    for(int j = y-1; j <= y+1; j++) {
                        if(i >= 0 && j >= 0 && i < world.getWidth() && j < world.getHeight()) {
                            if(world.getOrganismAt(i,j)==null){
                                this.setX(i);
                                this.setY(j);
                            }
                        }
                    }
                }
                return Constants.NOTHING;
            }
        }
        return Constants.NOTHING;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.YELLOW);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
