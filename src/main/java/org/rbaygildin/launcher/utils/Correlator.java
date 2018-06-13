package org.rbaygildin.launcher.utils;

import java.util.function.Consumer;

import static java.lang.Math.sqrt;

/**
 * Computes correlation between two data sets
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
public class Correlator implements Consumer<Pair<Double, Double>>{

    private int n_;
    private double x_total_, y_total_, xsq_total, ysq_total, xy_total;

    public Correlator()
    {
        n_ = 0;
        x_total_ = y_total_ = xsq_total = ysq_total = xy_total = 0d;
    }

    @Override
    public void accept(Pair<Double, Double> value)
    {
        n_++;
        x_total_ += value.getFirst();
        y_total_ += value.getSecond();
        xsq_total += value.getFirst() * value.getFirst();
        ysq_total += value.getSecond() * value.getSecond();
        xy_total += value.getFirst() * value.getSecond();
    }

    public void combine(Correlator other)
    {
        n_ += other.n_;
        x_total_ += other.x_total_;
        y_total_ += other.y_total_;
        xsq_total += other.xsq_total;
        ysq_total += other.ysq_total;
        xy_total += other.xy_total;
    }

    public double correlation()
    {
        return (n_ * xy_total - x_total_ * y_total_)
                / (sqrt(n_ * xsq_total - x_total_ * x_total_)
                * sqrt(n_ * ysq_total - y_total_ * y_total_));
    }
}
