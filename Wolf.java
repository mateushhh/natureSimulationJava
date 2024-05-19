import java.awt.*;

public class Wolf extends Animal {
    public Wolf(int x, int y, World world) {
        super("Wolf", 9, 5, x, y, world);
    }

    @Override
    public Wolf clone() {
        Wolf newWolf = new Wolf(this.x, this.y, this.world);
        newWolf.setAge(0);
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(i >= 0 && j >= 0 && i < world.getWidth() && j < world.getHeight()) {
                    if(world.getOrganismAt(i,j)==null){
                        this.setX(i);
                        this.setY(j);
                        world.activities.add(this.getName() + " breed.");
                        System.out.println(this.getName() + " breed.");
                        return newWolf;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.GRAY);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getTopMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getTopMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin() + panel.getTopMargin());
    }
}
