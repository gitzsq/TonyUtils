package com.tony.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class TileLod {


    public static List<TileLod> getWebTileLods(){
        List<TileLod> lods=new ArrayList<>();
        lods.add(new TileLod(0,591657527.591555,156543.03392800014));
        lods.add(new TileLod(1,295828763.795777,78271.51696399994));
        lods.add(new TileLod(2,147914381.897889,39135.7584820059));
        lods.add(new TileLod(3,73957190.9489444,19567.8792410029));
        lods.add(new TileLod(4,36978595.4744722,9783.93962050147));
        lods.add(new TileLod(5,18489297.7372361,4891.96981025073));
        lods.add(new TileLod(6,9244648.86861805,2445.98490512537));
        lods.add(new TileLod(7,4622324.434309,1222.992452562495));
        lods.add(new TileLod(8,2311162.217155,611.4962262813797));
        lods.add(new TileLod(9,1155581.108577,305.74811314055756));
        lods.add(new TileLod(10,577790.554289,152.87405657041106));

        return lods;
    }


    private int level;
    private double scale;
    private double resolution;

    public TileLod(int _l,double _s,double _r){
        this.level=_l;
        this.scale=_s;
        this.resolution = _r;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }
}

