import java.awt.*;
import java.util.Random;

public class Wolfberries extends Plant {
    public Wolfberries(int x, int y, World world) {
        super("Wolfberries", 99, x, y, world);
    }

    @Override
    public Wolfberries clone() {
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
    public Wolfberries spread(int x, int y) {
        Wolfberries newWolfberries = new Wolfberries(x, y, world);
        newWolfberries.setAge(0);
        return newWolfberries;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.BLUE);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.WHITE);
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
