import java.awt.*;

public class Wolf extends Animal {
    public Wolf(String name, int x, int y, World world) {
        super(name, 9, 5, x, y, world);
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.GRAY);
        g.fillRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + panel.getMargin(), panel.getCellSize(), panel.getCellSize());
        g.drawString(getSymbol(),1+x * panel.getCellSize() + panel.getMargin(), y * panel.getCellSize() + 2*panel.getMargin());
    }
}
