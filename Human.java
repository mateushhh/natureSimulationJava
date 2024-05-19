import java.awt.*;
public class Human extends Animal {
    public Human(int x, int y, World world) {
        super("Human", 5, 4, x, y, world);
    }
    public boolean movementLock;

    @Override
    public int action() {
        return Constants.NOTHING;
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

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.BLACK);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.WHITE);
        g.drawString(getSymbol(), x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin() + panel.getCellSize());
    }
}
