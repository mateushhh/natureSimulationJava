import java.awt.*;
import java.util.Random;

public class Fox extends Animal {
    public Fox(int x, int y, World world) {
        super("Fox", 3, 7, x, y, world);
    }

    @Override
    public Fox clone() {
        Fox newFox = new Fox(this.x, this.y, this.world);
        newFox.setAge(0);
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(i >= 0 && j >= 0 && i < world.getWidth() && j < world.getHeight()) {
                    if(world.getOrganismAt(i,j)==null){
                        this.setX(i);
                        this.setY(j);
                        world.activities.add(this.getName() + " breed.");
                        System.out.println(this.getName() + " breed.");
                        return newFox;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int action() {
        Random rand = new Random();
        int preX = getX();
        int preY = getY();
        int newX = preX;
        int newY = preY;

        do {
            int direction = rand.nextInt(4) + 1;

            switch (direction) {
                case Constants.UP:
                    if (getY() > 0)
                        newY = (getY() - 1);
                    break;
                case Constants.LEFT:
                    if (getX() > 0)
                        newX = (getX() - 1);
                    break;
                case Constants.DOWN:
                    if (getY() < world.getHeight() - 1)
                        newY = (getY() + 1);
                    break;
                case Constants.RIGHT:
                    if (getX() < world.getWidth() - 1)
                        newX = (getX() + 1);
                    break;
            }
        }while (world.getOrganismAt(newX, newY) != null && world.getOrganismAt(newX, newY).getStrength() > this.getStrength());

        setX(newX);
        setY(newY);

        if (preX == getX() && preY == getY()) {
            return action();
        }

        this.setAge(this.getAge() + 1);
        return Constants.NOTHING;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.ORANGE);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
