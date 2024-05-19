import java.awt.*;
public class Human extends Animal {
    public boolean movementLock;
    public int specialCooldown = 0;

    public Human(int x, int y, World world) {
        super("Human", 5, 4, x, y, world);
        life = true;
    }

    public void setWorld(World w){
        this.world = w;
    }

    public void setLife(boolean l){
        life = l;
    }

    @Override
    public int action() {
        System.out.println("specialCooldown" + specialCooldown);
        specialCooldown--;
        return Constants.NOTHING;
    }

    @Override
    public int collision(Organism otherOrganism) {
        if(specialCooldown>5){
            if(otherOrganism instanceof Animal){
                world.activities.add(this.getName() + " dodged "+ otherOrganism.getName() + " attack with Alzur's Shield.");
                otherOrganism.action();
                return Constants.DODGE;
            }
        }

        if (this.getStrength() >= otherOrganism.getStrength()) {
            world.activities.add(this.getName() + " killed " + otherOrganism.getName() + ".");
            System.out.println(this.getName() + " killed " + otherOrganism.getName() + ".");
            return Constants.KILL;
        } else if (this.getStrength() < otherOrganism.getStrength()) {
            return Constants.DIES;
        }
        return 0;
    }

    public void move(int direction) {
        switch (direction) {
            case Constants.UP:
                if (y > 0) y--;
                break;
            case Constants.LEFT:
                if (x > 0) x--;
                break;
            case Constants.DOWN:
                if (y < world.getHeight() - 1) y++;
                break;
            case Constants.RIGHT:
                if (x < world.getWidth() - 1) x++;
                break;
        }
        movementLock = false;
    }

    public void special(){
        world.activities.add(this.getName() + " activated Alzur's Shield.");
        specialCooldown = 10;
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.BLACK);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getTopMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getTopMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.WHITE);
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin() + panel.getTopMargin());
    }
}
