package org.rbaygildin.launcher.utils;

import static java.lang.Math.sqrt;

public class Correlation {

    void setX_Total(double x_total) {
        this.x_total_ = x_total;
    }

    void setY_Total(double y_total) {
        this.y_total_ = y_total;
    }

    void setXSQ_Total(double xsq_total) {
        this.xsq_total_ = xsq_total;
    }

    void setYSQ_Total(double ysq_total) {
        this.ysq_total_ = ysq_total;
    }

    void setXY_Total(double xy_total) {
        this.xy_total_ = xy_total;
    }

    double getX_Total() {
        return this.x_total_;
    }

    double getY_Total() {
        return this.y_total_;
    }

    double getXSQ_Total() {
        return this.xsq_total_;
    }

    double getYSQ_Total() {
        return this.ysq_total_;
    }

    double getXY_Total() {
        return this.xy_total_;
    }

    int size(){
        return n_;
    }

    private double x_total_, y_total_, xsq_total_, ysq_total_, xy_total_;
    private int n_;

    Correlation(int size)
    {
        x_total_ = y_total_ = xsq_total_ = ysq_total_ = xy_total_ = 0;
        this.n_ = size;
    }

    public double calculate()
    {
        return (n_ * xy_total_ - x_total_ * y_total_)
                / (sqrt(n_ * xsq_total_ - x_total_ * x_total_)
                * sqrt(n_ * ysq_total_ - y_total_ * y_total_));
    }
}
