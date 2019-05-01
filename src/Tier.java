import processing.core.PApplet;
import java.util.ArrayList;

public class Tier {
    public enum DrawDirection {
        UP(),
        DOWN(),
        LEFT(),
        RIGHT()
    }

    static private PApplet gui;

    private ArrayList<Item> items = new ArrayList<Item>();
    private String name;

    private float x;
    private float y;
    private float width;
    private float height;

    private float boxX;
    private float boxY;
    private float boxWidth;
    private float boxHeight;

    private static Tier chosen = null;

    private boolean drawBound = true; //"Tier" area; clicking this area does not count as clicking tier
    private boolean drawBox = true; //"Header" area; clicking this area does count as clicking tier

    private DrawDirection drawDirection;

    public Tier(String _name, float[] _coords) {
        name = _name;
        x = _coords[0];
        y = _coords[1];
        width = _coords[2];
        height = _coords[3];
    }

    public Tier(String _name) {
        name = _name;
        drawDirection = DrawDirection.RIGHT;
    }

    public Tier(String _name, DrawDirection _drawDirection) {
        name = _name;
        drawDirection = _drawDirection;
    }

    public Tier(String _name, DrawDirection _drawDirection, boolean _drawBound, boolean _drawBox) {
        name = _name;
        drawDirection = _drawDirection;
        drawBound = _drawBound;
        drawBox = _drawBox;
    }

    public void draw() {
        gui.strokeWeight(2);

        if (isChosen()) {
            gui.stroke(255, 255, 0);
        } else if(isTouched()) {
            gui.stroke(255, 0, 0);
        } else {
            gui.noStroke();
        }

        if(drawBound) {
            gui.fill(50);
            gui.rect(x, y, width, height);
        }

        if(drawBox) {
            gui.fill(0, 255, 255);
            gui.rect(boxX, boxY, boxWidth, boxHeight);
        }

        gui.fill(255, 0, 0);
        gui.text(name, boxX + 0.5f*boxWidth - 0.5f*gui.textWidth(name), boxY+0.5f*boxHeight);
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
        switch(drawDirection) {
            case RIGHT:
                item.setPosition(x+width/10.0f+(gui.width/19.2f*(items.size()-1)), y); //TD: Need to handle overflow from tier
                break;
            case DOWN:
                item.setPosition(x + gui.width/14.4f*((items.size()-1)%3), boxY+boxHeight+gui.width/14.4f*(((items.size()-1)/3)));
                break;
        }
        item.setTier(this);
    }
    public void removeItem(Item item) {
        item.setTier(null);
        items.remove(item);
        updateItems();
    }

    public void updateItems() {
        for(int i = 0; i < items.size(); i++) {
            switch(drawDirection) {
                case RIGHT:
                    items.get(i).setPosition(x + width/10.0f+gui.width/19.2f*(i), y);
                    break;
                case DOWN:
                    items.get(i).setPosition(x + gui.width/14.4f*((i)%3), boxY+boxHeight+gui.width/14.4f*(i/3));
                    break;
            }
        }
    }

    public DrawDirection getDrawDirection() {
        return drawDirection;
    }
    public void setDrawDirection(DrawDirection _drawDirection) {
        drawDirection = _drawDirection;
    }

    public boolean isTouched() {
        return gui.mouseX <= boxX + boxWidth && gui.mouseX >= boxX && gui.mouseY >= boxY && gui.mouseY <= boxY + boxHeight;
    }

    public boolean isChosen() {
        return chosen != null && chosen.equals(this);
    }
    public static Tier getChosen() {
        return chosen;
    }
    public static void setChosen(Tier tier) {
        chosen = tier;
    }
    public void makeChosen() {
        if(isChosen()) {
            chosen = null;
        } else {
            chosen = this;
        }
    }

    public void setDefaultBoxCoordinates() {
        switch(drawDirection) {
            case RIGHT:
                boxX = x;
                boxY = y;
                boxWidth = width / 10;
                boxHeight = height;
                break;
            case DOWN:
                boxX = x;
                boxY = y;
                boxWidth = width;
                boxHeight = height/10;
                break;
            default:
                break;
        }
    }

    public boolean hasBox() {
        return drawBox;
    }
    public void showBox(boolean _drawBox) {
        drawBox = _drawBox;
    }

    public float[] getCoordinates() {
        return new float[]{x, y, width, height};
    }
    public void setCoordinates(float _x, float _y, float _width, float _height) {
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        setDefaultBoxCoordinates();
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

    public float[] getBoxCoordinates() {
        return new float[]{boxX, boxY, boxWidth, boxHeight};
    }
    public void setBoxCoordinates(float _boxX, float _boxY, float _boxWidth, float _boxHeight) {
        boxX = _boxX;
        boxY = _boxY;
        boxWidth = _boxWidth;
        boxHeight = boxHeight;
    }

    public float[] getBoxPosition() {
        return new float[]{x, y};
    }
    public void setBoxPosition(float _x, float _y) {
        x = _x;
        y = _y;
    }

    public float[] getBoxDimensions() {
        return new float[]{boxWidth, boxHeight};
    }
    public void setBoxDimensions(float _boxWidth, float _boxHeight) {
        boxWidth = _boxWidth;
        boxHeight = _boxHeight;
    }

    public float getBoxX() {
        return boxX;
    }
    public void setBoxX(float _boxX) {
        boxX = _boxX;
    }

    public float getBoxY() {
        return boxY;
    }
    public void setBoxY(float _boxY) {
        boxY = _boxY;
    }

    public float getBoxWidth() {
        return boxWidth;
    }
    public void setBoxWidth(float _boxWidth) {
        boxWidth = _boxWidth;
    }

    public float getBoxHeight() {
        return boxHeight;
    }
    public void setBoxHeight(float _boxHeight) {
        boxHeight = _boxHeight;
    }



    public static void setGui(PApplet _gui) {
        gui = _gui;
    }
    public static PApplet getGui() {
        return gui;
    }
}
