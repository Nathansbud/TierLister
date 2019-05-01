import processing.core.PApplet;
import processing.core.PImage;

public class Item {
    static private PApplet gui;
    private String name;
    private PImage img = null;

    private float x;
    private float y;
    private float width;
    private float height;

    private static Item chosen = null;
    private Tier tier = null;

    Item(String _name, String _path, float[] _coords) {
        name = _name;
        if(_coords.length == 4) {
            x = _coords[0];
            y = _coords[1];
            width = _coords[2];
            height = _coords[3];
        } else {
            width = _coords[0];
            height = _coords[1];
        }
        img = gui.loadImage(_path);
    }

    Item(String _name, float[] _coords) {
        name = _name;
        x = _coords[0];
        y = _coords[1];
        width = _coords[2];
        height = _coords[3];
    }

    public void draw() {
        gui.strokeWeight(2);

        if(isChosen()) {
            gui.stroke(255, 255, 1); //1 so intellij stops yelling at me :((( should be 0 tho
        } else if(isTouched()) {
            gui.stroke(255, 0, 0);
        } else {
            gui.noStroke();
        }
        gui.noFill();
        if(hasImg()) {
            gui.image(img, x, y, width, height);
            gui.rect(x, y, width, height);
        } else {
            gui.rect(x, y, width, height);
        }
        gui.strokeWeight(1);
    }

    public void drawTooltip() {
        gui.strokeWeight(2);
        if(isChosen()) {
            gui.stroke(255, 255, 0);
        } else {
            gui.stroke(255, 0, 0);
        }
        gui.line(x+width, y, x+1.25f*width, y-0.25f*height);
        gui.fill(255);
        gui.rect(x+1.25f*width, y-0.5f*height,1.25f*width, 0.25f*height);
        gui.textSize(12);
        gui.fill(0);
        gui.text(name, x + 1.25f*width + 0.5f*1.25f*width - 0.5f*gui.textWidth(name), y-0.5f*height+gui.textAscent());
    }

    public boolean isTouched() {
        return gui.mouseX <= x+width && gui.mouseX >= x && gui.mouseY >= y && gui.mouseY <= y+height;
    }

    public boolean isChosen() {
        return chosen != null && chosen.equals(this);
    }
    public static Item getChosen() {
        return chosen;
    }
    public static void setChosen(Item item) {
        chosen = item;
    }
    public void makeChosen() {
        if(isChosen()) {
            chosen = null;
        } else {
            chosen = this;
        }
    }

    public Tier getTier() {
        return tier;
    }
    public void setTier(Tier _tier) {
        tier = _tier;
    }
    public boolean hasTier() {
        return tier != null;
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public PImage getImg() {
        return img;
    }
    public void setImg(PImage _img) {
        img = _img;
    }
    public boolean hasImg() {
        return img != null;
    }

    //Coordinates & Dimensions

    public float[] getCoords() {
        return new float[]{x, y, width, height};
    }
    public void setCoords(float _x, float _y, float _width, float _height) {
        x = _x;
        y = _y;
        width = _width;
        height = _height;
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
}
