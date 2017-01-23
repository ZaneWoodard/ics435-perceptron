package edu.hawaii.ics435;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PerceptronDataSet {
    public final Point[] initialPoints;
    public final Byte[] initialLabels;

    public ArrayList<Point> addedPoints;
    public ArrayList<Byte> addedLabels;


    public final Point2D.Double realLineP1;
    public final Point2D.Double realLineP2;
    public Point2D.Double learnedLineP1;
    public Point2D.Double learnedLineP2;

    public final Integer maxX;
    public final Integer maxY;

    public PerceptronDataSet(Point[] initialPoints, Byte[] initialLabels, Point2D.Double realLineP1, Point2D.Double realLineP2, Integer maxX, Integer maxY) {
        this.initialPoints = initialPoints;
        this.initialLabels = initialLabels;

        this.realLineP1 = realLineP1;
        this.realLineP2 = realLineP2;

        this.maxX = maxX;
        this.maxY = maxY;

        addedPoints = new ArrayList<>();
        addedLabels = new ArrayList<>();
    }

}
