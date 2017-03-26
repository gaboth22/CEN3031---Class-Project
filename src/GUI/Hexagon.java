package GUI;

import processing.core.PApplet;

public class Hexagon {
    private int centerX;
    private int centerY;
    private int size;
    private int color;
    private PApplet parent;
    private String terrain;
    private String level;
    private String piece;

    public Hexagon(int centerX, int centerY, int size) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.size = size;
        terrain = "";
        level = "";
        piece = "";
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPiece(String piece) {
        this.piece = piece;
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
        parent.fill(this.color);
        renderPolygon(centerX, centerY, size, 6);
        String data = "" + level + " " + terrain + " " + piece + "";
        parent.fill(0,100,255);
        parent.textSize(9);
        parent.text(data, centerX - 25, centerY);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hexagon hex = (Hexagon) o;

        return centerX == hex.centerX && centerY == hex.centerY;
    }

}
