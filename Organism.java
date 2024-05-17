import java.awt.*;

public abstract class Organism {
    protected String name;
    protected int strength;
    protected int initiative;
    protected int x;
    protected int y;
    protected int age;
    protected boolean life;
    protected World world;

    public Organism(String name, int strength, int initiative, int x, int y, World world) {
        this.name = name;
        this.strength = strength;
        this.initiative = initiative;
        this.x = x;
        this.y = y;
        this.world = world;
        this.age = 0;
        this.life = true;
    }

    public abstract int action();

    public abstract int collision(Organism otherOrganism);

    public abstract void draw(Graphics g, GamePanel panel);

    // Getters & Setters
    public String getSymbol() {
        return name.charAt(0) + "";
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean alive() {
        return life;
    }

    public void die() {
        this.life = false;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
