import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.File;
import java.util.ArrayList;

public class TierLister extends PApplet {
    private static TierLister app = new TierLister();
    private static String title = "Fruit Tier List";

    private static Tier[] tl = {
            new Tier("SS"),
            new Tier("S"),
            new Tier("A"),
            new Tier("B"),
            new Tier("C"),
            new Tier("D"),
            new Tier("E"),
            new Tier("F")
    };

    private static ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        ArrayList<File> files = new ArrayList<>();

        for(File f : new File("data").listFiles()) {
            if(!(f.getName().equals(".DS_Store") || f.getName().equals(".gitkeep"))) {
                files.add(f);
            }
        }
        for(int i = 0, n = 0; i < files.size(); i++) {
            if(i % 2 == 0) {
                n++;
            }
            items.add(new Item(files.get(i).getName(), files.get(i).getPath(), new float[]{width - width/14.4f*(2*((i+1)%2) + (i)%2), height/9.0f*(n), width/19.2f, width/19.2f}));
        }

        for(int i = 0; i < tl.length; i++) {
            tl[i].setCoordinates(0, height/12.0f*(i+1), width/1.5f, height/12.0f);
        }
    }

    @Override
    public void draw() {
        background(0);
        textSize(18);
        text(title, width/2.0f - 0.5f*textWidth(title), height/60.0f);
        for(Tier t : tl) {
            t.draw();
        }

        for(Item i : items) {
            i.draw();
        }

        if(Item.getChosen() != null && Tier.getChosen() != null) {
            if(Item.getChosen().hasTier()) {
                if(Item.getChosen().getTier() != Tier.getChosen()) {
                    Tier prev = Item.getChosen().getTier();
                    prev.removeItem(Item.getChosen());
                }
            } else {
                Tier.getChosen().addItem(Item.getChosen());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if(event.getKey() == 's') {
            app.saveFrame("output" + File.separator + title.toLowerCase().replaceAll(" ", "-") + ".jpg");
        }
    }

    @Override
    public void mouseClicked() {
        for(Item i : items) {
            if(i.isTouched()) {
                i.makeChosen();
            }
        }

        for(Tier t : tl) {
            if(t.isTouched()) {
                t.makeChosen();
                if(Tier.getChosen() != null && Item.getChosen() != null) {
                    if(Item.getChosen().hasTier()) {
                        Tier prev = Item.getChosen().getTier();
                        prev.removeItem(Item.getChosen());
                    }
                    Tier.getChosen().addItem(Item.getChosen());
                }
            }
        }
    }

    public static void main(String[] args) {
        Tier.setGui(app);
        Item.setGui(app);

        String[] sketchArgs = {"TierLister"};
        PApplet.runSketch(sketchArgs, app);
    }
}
