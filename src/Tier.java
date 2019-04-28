import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Arrays;


public class Tier {
    static private PApplet gui;

    private ArrayList<Item> items = new ArrayList<Item>();
    private String name;

    private float x;
    private float y;
    private float width;
    private float height;

    private float boxX;

    private static Tier chosen = null;

    public Tier(String _name, float[] _coords) {
        name = _name;

        x = _coords[0];
        y = _coords[1];
        width = _coords[2];
        height = _coords[3];
    }

    public Tier(String _name) {
        name = _name;
    }

    public void draw() {
        gui.strokeWeight(2);

        if(isChosen()) {
            gui.stroke(255, 255, 0);
        } else if(isTouched()) {
            gui.stroke(255, 0, 0);
        } else {
            gui.stroke(0);
        }
        gui.fill(50);
        gui.rect(x, y, width, height);
        gui.fill(0, 255, 255);
        gui.rect(x, y, width/10.0f, height);
        gui.fill(255, 0, 0);
        gui.text(name, x + 0.5f*width/10.0f - 0.5f*gui.textWidth(name), y+0.5f*height);
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> _items) {
        items = _items;
    }

    public Item getItem(int index) {
        return items.get(index);
    }
    public void setItem(Item item, int index) {
        items.set(index, item);
    }

    public void addItem(Item item) {
        items.add(item);
        item.setPosition(x+width/10.0f*items.size(), y); //TD: Need to handle overflow from tier
        item.setTier(this);
    }
    public void removeItem(Item item) {
        item.setTier(null);
        items.remove(item);
        updateItems();
    }

    public void updateItems() {
        for(int i = 0; i < items.size(); i++) {
            items.get(i).setPosition(x + width/10.0f*(i+1), y);
        }
    }


    public boolean isTouched() {
        return gui.mouseX <= x + boxX && gui.mouseX >= x && gui.mouseY >= y && gui.mouseY <= y + height;
    }

    public static Tier getChosen() {
        return chosen;
    }
    public boolean isChosen() {
        return chosen != null && chosen.equals(this);
    }
    public void makeChosen() {
        if(isChosen()) {
            chosen = null;
        } else {
            chosen = this;
        }
    }

    public float[] getCoordinates() {
        return new float[]{x, y, width, height};
    }
    public void setCoordinates(float _x, float _y, float _width, float _height) {
        x = _x;
        y = _y;
        width = _width;
        height = _height;

        boxX = width/10.0f;
    }

    public float[] getPosition() {
        return new float[]{x, y};
    }
    public void setPosition(float _x, float _y) {
        x = _x;
        y = _y;
    }

    public float[] getDimensions() {
        return new float[]{width, height};
    }
    public void setDimensions(float _width, float _height) {
        width = _width;
        height = _height;
    }

    public float getX() {
        return x;
    }
    public void setX(float _x) {
        x = _x;
    }

    public float getY() {
        return y;
    }
    public void setY(float _y) {
        y = _y;
    }

    public float getWidth() {
        return width;
    }
    public void setWidth(float _width) {
        width = _width;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float _height) {
        height = _height;
    }

    public static void setGui(PApplet _gui) {
        gui = _gui;
    }
    public static PApplet getGui() {
        return gui;
    }
}
