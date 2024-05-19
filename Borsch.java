import java.awt.*;
import java.util.Random;

public class Borsch extends Plant {
    public Borsch(int x, int y, World world) {
        super("Borsch", 10, x, y, world);
    }

    @Override
    public Borsch clone() {
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
    public Borsch spread(int x, int y) {
        Borsch newBorsch = new Borsch(x, y, world);
        newBorsch.setAge(0);
        return newBorsch;
    }

    @Override
    public int action() {
        Random rand = new Random();
        int spreadChance = rand.nextInt(100);

        for(int i = x-1; i <= x+1; i++){
            for(int j = y-1; j <= y+1; j++){
                Organism o = world.getOrganismAt(i,j);
                if(o instanceof Animal && o.alive()){
                    o.die();
                    world.activities.add(this.getName() + " killed " + o.getName() + ".");
                }
            }
        }

        if (spreadChance < 3) {
            this.setAge(this.getAge() + 1);
            return Constants.BREED;
        }

        this.setAge(this.getAge() + 1);
        return Constants.NOTHING;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.WHITE);
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
