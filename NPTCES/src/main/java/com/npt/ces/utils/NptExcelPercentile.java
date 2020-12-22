package com.npt.ces.utils;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.util.KthSelector;
import org.apache.commons.math3.util.MedianOf3PivotingStrategy;

/**
 * author: owen
 * date:   2017/7/16 下午5:43
 * note:
 */
public class NptExcelPercentile extends Percentile{
    public NptExcelPercentile() throws MathIllegalArgumentException {

        super(20.0,
                EstimationType.R_7, // use excel style interpolation
                NaNStrategy.REMOVED,
                new KthSelector(new MedianOf3PivotingStrategy()));
    }
}
