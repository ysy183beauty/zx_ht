package com.npt.bridge.map.graph;

import com.npt.bridge.map.bean.NptBaseMap;
import com.npt.bridge.map.bean.NptMapSegment;

/**
 * author: owen
 * date:   2017/4/12 下午5:38
 * note:
 */
public class NptMapUIConverter {

    /**
     * author: owen
     * date:   2017/4/12 下午5:52
     * note:
     * 将图谱数据转换为UI数据
     */
    public static void convertForEchart2(NptBaseMap map, NptGraphData data) {

        if(null != map && null != data){

            if(null != map.getSegments() && !map.getSegments().isEmpty()){

                for(NptMapSegment seg:map.getSegments()){
                    NptGraphNode fn = new NptGraphNode();
                    NptGraphNode tn = new NptGraphNode();

                    fn.setId(seg.getFromPoint().getuId());
                    fn.setCategory(1);
                    fn.setContent(seg.getFromPoint().getTitle());
                    fn.setLabel(seg.getFromPoint().getTitle());
                    fn.setFlag(true);
                    fn.setIgnore(true);
                    fn.setName(seg.getFromPoint().getuId());
                    fn.setSymbolSize(35);

                    fn.setPoolId(seg.getFromPoint().getPoolId());
                    fn.setUkValue(seg.getFromPoint().getUkValue());



                    tn.setId(seg.getToPoint().getuId());
                    tn.setCategory(1);
                    tn.setContent(seg.getToPoint().getTitle());
                    tn.setLabel(seg.getToPoint().getTitle());
                    tn.setFlag(true);
                    tn.setIgnore(true);
                    tn.setName(seg.getToPoint().getuId());
                    tn.setSymbolSize(35);

                    tn.setPoolId(seg.getToPoint().getPoolId());
                    tn.setUkValue(seg.getToPoint().getUkValue());


                    NptGraphLink lk = new NptGraphLink();
                    lk.setSource(fn.getId());
                    lk.setTarget(tn.getId());
                    lk.setText(seg.getLinkLine().getTitle());


                    data.getNodes().add(fn);
                    data.getNodes().add(tn);
                    data.getLinks().add(lk);
                }

            }

        }
    }

}
