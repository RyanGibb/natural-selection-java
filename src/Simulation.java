import java.awt.*;

public class Simulation {
    public static final int SLEEP_MS_BASE = 20;

    public boolean run = true;
    public int sleep_ms = SLEEP_MS_BASE;
    public World world;
    public SimGUI gui;

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        World world = new World(new Point(screenSize.getHeight(), screenSize.getWidth()));
        Simulation sim = new Simulation(world);
        sim.gui = new SimGUI(sim);
        sim.run();
    }

    public Simulation(World world) {
        this.world = world;
    }

    public void pause () {
        if (run) {
            run = false;
        }
        else {
            run = true;
            sleep_ms = SLEEP_MS_BASE;
            run();
        }
    }

    public void run() {
        while(run) {
            gui.display();
            world.tick();
            try {
                Thread.sleep(sleep_ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
