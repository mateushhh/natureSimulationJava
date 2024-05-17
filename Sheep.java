import java.awt.*;

public class Sheep extends Animal {
    public Sheep(int x, int y, World world) {
        super("Sheep", 4, 4, x, y, world);
    }

    @Override
    public Sheep clone() {
        Sheep newSheep = new Sheep(this.x, this.y, this.world);
        newSheep.setAge(0);
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(i >= 0 && j >= 0 && i < world.getWidth() && j < world.getHeight()) {
                    if(world.getOrganismAt(i,j)==null){
                        this.setX(i);
                        this.setY(j);
                        world.activities.add(this.getName() + " breed.");
                        System.out.println(this.getName() + " breed.");
                        return newSheep;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.PINK);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
