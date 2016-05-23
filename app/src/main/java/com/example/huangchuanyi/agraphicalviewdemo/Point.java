package com.example.huangchuanyi.agraphicalviewdemo;

/**
 * Created by xianglei on 15/2/5.
 */
public class Point implements Comparable {

    public int x;
    public int y;

    public int x2;
    public int y2;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the point's x and y coordinates
     */
    public void set(int x, int y,int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    private int getMix() {
        return (x << 8) + y;
    }

    @Override
    public int compareTo(Object o) {
        Point other = (Point) o;
        return getMix() - other.getMix();
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
