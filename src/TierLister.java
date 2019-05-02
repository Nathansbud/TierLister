import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.FileDialog;
import java.awt.Frame;

import java.io.File;
import java.util.ArrayList;

public class TierLister extends PApplet {
    private static TierLister app = new TierLister();
    private static String title = "Fruit Tier List";
//    private static boolean canShuffle = true;

    private static Tier[] tl = {
            new Tier("SS"),
            new Tier("S"),
            new Tier("A"),
            new Tier("B"),
            new Tier("C"),
            new Tier("D"),
            new Tier("E"),
            new Tier("F"),
            new Tier("Untiered", Tier.DrawDirection.DOWN)
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

        for(int i = 0; i < tl.length-1; i++) {
            tl[i].setCoordinates(0, height/11.5f*(i+1), width/1.5f, height/12.0f);
        }

        tl[tl.length - 1].setCoordinates(width - width/4.8f, 0, width/4.8f, height);

        for(int i = 0, n = 0; i < files.size(); i++) {
            if(i % 2 == 0) {
                n++;
            }

            items.add(new Item(cleanName(files.get(i).getName()), files.get(i).getPath(), new float[]{width/19.2f, width/19.2f}));//, new float[]{width - width/14.4f*(2*((i+1)%2) + (i)%2), height/9.0f*(n), width/19.2f, width/19.2f}));
            tl[tl.length - 1].addItem(items.get(i));
        }

    }



    @Override
    public void draw() {
        background(0);
        textSize(24);
        strokeWeight(2);
        fill(255, 0, 0);
        text(title, width/2.0f - 0.5f*textWidth(title), height/30.0f);

        for(Tier t : tl) {
            t.draw();
        }

        for(Item i : items) {
            i.draw();
        }

        for(Item i : items) {
            if(i.isTouched()) {
                i.drawTooltip();
            }
        } //stop being layered over, this could prob be done more elegantly with a static touched method
    }

    @Override
    public void keyTyped(KeyEvent event) {
        switch(event.getKey()) {
            case 's':
            case 'S':
                app.saveFrame("output" + File.separator + title.toLowerCase().replaceAll(" ", "-") + ".png");
                break;
            case 'i':
            case 'I':
                Item.setChosen(null);
                break;
            case 't':
            case 'T':
                Tier.setChosen(null);
                break;
            case 'd':
            case 'D':
                Item.setChosen(null);
                Tier.setChosen(null);
                break;
            case 'a':
            case 'A':
                for(File f : getImagePaths()) {
                    if(f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg")) {
                        Item toAdd = new Item(cleanName(f.getName()), f.getPath(), new float[]{width / 19.2f, width / 19.2f});
                        items.add(toAdd);
                        tl[tl.length - 1].addItem(toAdd);
                    }
                    f.renameTo(new File("data" + File.separator + f.getName()));
                }
                break;
        }
    }

    @Override
    public void mouseClicked() {
        for(Item i : items) {
            if(i.isTouched()) {
                i.makeChosen();
                moveChosen();
                break;
            }
        }

        for(Tier t : tl) {
            if(t.isTouched()) {
                t.makeChosen();
                moveChosen();
                break;
            }
        }
    }

    private static void moveChosen() {
        if(Tier.getChosen() != null && Item.getChosen() != null) {
            if(Item.getChosen().hasTier()) {
                Tier prev = Item.getChosen().getTier();
                prev.removeItem(Item.getChosen());
            }
            Tier.getChosen().addItem(Item.getChosen());
            Item.setChosen(null);
        }
    }

    private static File[] getImagePaths() {
        FileDialog fd = new FileDialog(new Frame(), "Item Select", FileDialog.LOAD); //Believe you me, I'd rather use JFileChooser than this...but JFileChooser crashes on me 24/7
        fd.setVisible(true);
        fd.setFile("*.jpg;");
        fd.setMultipleMode(true);
        return fd.getFiles();
    }

    private static String cleanName(String name) {
        String[] nameParts = name.split(" ");
        String actualName = "";
        for(int r = 0; r < nameParts.length; r++) {
            nameParts[r] = nameParts[r].substring(0, 1).toUpperCase() + nameParts[r].substring(1);
        }
        for(String part : nameParts) {
            actualName += part;
        }
        return actualName.substring(0, actualName.lastIndexOf("."));
    }

    public static void main(String[] args) {
        Tier.setGui(app);
        Item.setGui(app);
        String[] sketchArgs = {"TierLister"};
        PApplet.runSketch(sketchArgs, app);
    }
}
