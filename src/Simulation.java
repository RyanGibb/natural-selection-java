import java.awt.*;

public class Simulation {
    public static final int SLEEP_MS_BASE = 17;

    public boolean run = true;
    public int sleep_ms = SLEEP_MS_BASE;
    public World world;
    public SimGUI gui;

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        World world = new World(new Point(screenSize.getHeight(), screenSize.getWidth()));
        Simulation sim = new Simulation(world);
        sim.gui = new SimGUI(sim);
        sim.start();
    }

    public Simulation(World world) {
        this.world = world;
    }

    public void run() {
        while(run) {
            long start = System.currentTimeMillis();
            gui.display();
            world.tick();
            long end = System.currentTimeMillis();
            long diff = end - start;
            long remaining = sleep_ms - diff;
            if (remaining > 0) {
                try {
                    Thread.sleep(remaining);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this::run);
        thread.start();
    }

    public void pause () {
        if (run) {
            run = false;
        }
        else {
            run = true;
            start();
        }
    }

}
