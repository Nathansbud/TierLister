import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.File;
import java.util.ArrayList;

public class TierLister extends PApplet {
    public static TierLister app = new TierLister();
    public static String title = "Fruit Tier List";

    public static Tier[] tl = {
            new Tier("SS"),
            new Tier("S"),
            new Tier("A"),
            new Tier("B"),
            new Tier("C"),
            new Tier("D"),
            new Tier("E"),
            new Tier("F")
    };

    public static ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        float h = 75;
        float counter = 0;
        for(File f : new File("data").listFiles()) {
            if(!(f.getName().equals(".DS_Store") || f.getName().equals(".gitkeep"))) {
                items.add(new Item(f.getName(), f.getPath(), new float[]{width - 75, h*counter, 75, 75}));
                counter++;
            }
        }

        for(int i = 0; i < tl.length; i++) {
            tl[i].setCoordinates(0, height/12.0f*i, width/2.0f, height/12.0f);
        }
    }

    @Override
    public void draw() {
        for(Tier t : tl) {
            t.draw();
        }

        for(Item i : items) {
            i.draw();
        } //How to handle items being drawn as items but also as members of TLists?
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if(event.getKey() == 's') {
            app.saveFrame("output" + File.separator + title.toLowerCase().replaceAll(" ", "-") + ".jpg");
        }
    }

    public static void main(String[] args) {
        Tier.setGui(app);
        Item.setGui(app);

        String[] sketchArgs = {"TierLister"};
        PApplet.runSketch(sketchArgs, app);
    }
}
