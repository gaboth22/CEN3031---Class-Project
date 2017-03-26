package GUI;

import processing.core.PApplet;

public class Hexagon {
    private int centerX;
    private int centerY;
    private int size;
    private int color;
    private PApplet parent;

    public Hexagon(int centerX, int centerY, int size) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.size = size;
    }

    public void setParent(PApplet applet) {
        parent = applet;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void render() {
        parent.pushMatrix();
        parent.fill(color);
        renderPolygon(centerX, centerY, size, 6);
        parent.popMatrix();
    }

    private void renderPolygon(float x, float y, float radius, int npoints) {
        float angle = parent.TWO_PI / npoints;
        parent.beginShape();
        for (float a = 0; a < parent.TWO_PI; a += angle) {
            float sx = x + parent.cos(a) * radius;
            float sy = y + parent.sin(a) * radius;
            parent.vertex(sx, sy);
        }
        parent.endShape(parent.CLOSE);
    }

    public boolean isPointWithinHex(int x, int y) {
        return parent.dist(centerX + 10, centerY + 10, x, y) <= 25;
    }
}
