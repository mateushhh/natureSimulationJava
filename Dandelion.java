import java.awt.*;
import java.util.Random;

public class Dandelion extends Plant {
    public Dandelion(int x, int y, World world) {
        super("Dandelion", 0, x, y, world);
    }

    @Override
    public Dandelion clone() {
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

    @Override
    public Dandelion spread(int x, int y) {
        Dandelion newDandelion = new Dandelion(x, y, world);
        newDandelion.setAge(0);
        return newDandelion;
    }

    @Override
    public int action() {
        Random rand = new Random();
        int spreadChance;

        for(int i = 0; i < 3; i++) {
            spreadChance = rand.nextInt(100);
            if (spreadChance < 5) {
                this.setAge(this.getAge() + 1);
                return Constants.BREED;
            }
        }

        this.setAge(this.getAge() + 1);
        return Constants.NOTHING;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.ORANGE);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getTopMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getTopMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.WHITE);
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin() + panel.getTopMargin());

    }
}
