package edu.hawaii.ics435;

import java.awt.*;
import java.util.ArrayList;

public class PerceptronDataSet {
    public final Point[] initialPoints;
    public final Byte[] initialLabels;

    public ArrayList<Point> addedPoints;
    public ArrayList<Byte> addedLabels;


    public final Point realLineP1;
    public final Point realLineP2;
    public Point learnedLineP1;
    public Point learnedLineP2;

    public PerceptronDataSet(Point[] initialPoints, Byte[] initialLabels, Point realLineP1, Point realLineP2) {
        this.initialPoints = initialPoints;
        this.initialLabels = initialLabels;

        this.realLineP1 = realLineP1;
        this.realLineP2 = realLineP2;
    }

}
