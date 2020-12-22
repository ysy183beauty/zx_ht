package com.npt.bridge.map.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/4/28 15:36
 * 描述：
 */
public class NptBaseMap implements Serializable{


    private NptMapPoint rootPoint;

    private List<NptMapSegment> segments;


    public NptMapPoint getRootPoint() {
        return rootPoint;
    }

    public void setRootPoint(NptMapPoint rootPoint) {
        this.rootPoint = rootPoint;
    }

    public List<NptMapSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<NptMapSegment> segments) {
        this.segments = segments;
    }

    public void addSegment(NptMapSegment segment){
        if (null != segment) {

            this.getSegments().add(segment);
        }
    }

    public List<NptMapSegment> getSegmentsByFromPoint(NptMapPoint from){
        List<NptMapSegment> result = new ArrayList<>();

        if(null != this.getSegments() && !this.getSegments().isEmpty()){
            for(NptMapSegment seg:this.getSegments()){

                if(seg.getFromPoint().equals(from)){
                    result.add(seg);
                }

            }
        }

        return result;
    }


    public List<NptMapSegment> getSegmentsByToPoint(NptMapPoint to){
        List<NptMapSegment> result = new ArrayList<>();

        if(null != this.getSegments() && !this.getSegments().isEmpty()){
            for(NptMapSegment seg:this.getSegments()){

                if(seg.getToPoint().equals(to)){
                    result.add(seg);
                }

            }
        }

        return result;
    }


    public List<NptMapPoint> getHideTailPoint(){
        List<NptMapPoint> result = new ArrayList<>();

        if(null != this.getSegments() && !this.getSegments().isEmpty()){
            for(NptMapSegment seg:this.getSegments()){

                if(seg.getToPoint().getHide()){
                    result.add(seg.getToPoint());
                }

            }
        }

        return result;
    }


    public List<NptMapSegment> mergeByMiddlePoint(NptMapPoint mp){

        List<NptMapSegment> result = new ArrayList<>();

        if(null != mp){

            List<NptMapSegment> asFrom = this.getSegmentsByFromPoint(mp);
            List<NptMapSegment> asTo = this.getSegmentsByToPoint(mp);

            if(null != asFrom && null != asTo && !asFrom.isEmpty() && !asTo.isEmpty()){

                for(NptMapSegment sf:asFrom){
                    for(NptMapSegment st:asTo){

                        NptMapLine newLine = new NptMapLine();

                        newLine.setDirection(sf.getLinkLine().getDirection());

                        newLine.setTitle(sf.getLinkLine().getTitle() + " " + st.getLinkLine().getTitle());

                        NptMapSegment newSeg = new NptMapSegment(st.getFromPoint(),newLine,sf.getToPoint());
                        result.add(newSeg);
                    }
                }

                this.getSegments().removeAll(asFrom);
                this.getSegments().removeAll(asTo);
                this.getSegments().addAll(result);
            }
        }

        return result;

    }

    public void removeDuplication(){

        Map<String,NptMapSegment> uuSegMap = new HashMap<>();

        for(NptMapSegment seg:this.getSegments()){

            String ftID = seg.getFromPoint().getuId() + seg.getToPoint().getuId();

            if(uuSegMap.keySet().contains(ftID)){

                NptMapSegment existed = uuSegMap.get(ftID);
                String newTitle = existed.getLinkLine().getTitle() + " " + seg.getLinkLine().getTitle();
                existed.getLinkLine().setTitle(newTitle);

            }else {
                uuSegMap.put(ftID,seg);
            }
        }

        this.getSegments().clear();
        this.getSegments().addAll(uuSegMap.values());
    }
}
