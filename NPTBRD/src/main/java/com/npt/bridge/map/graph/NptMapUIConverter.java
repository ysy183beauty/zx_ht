package com.npt.bridge.map.graph;

import com.npt.bridge.map.bean.NptBaseMap;
import com.npt.bridge.map.bean.NptMapSegment;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * author: owen
 * date:   2017/4/12 下午5:38
 * note:
 */
public class NptMapUIConverter {

    private static Integer ROOT_NODE_WEIGHT = 15;
    private static Integer LINK_NODE_WEIGHT = 7;
    private static Integer SIMPLE_NODE_WEIGHT = 5;

    private static Integer ROOT_LINK_WEIGHT = 2;
    private static Integer SIMPLE_LINK_WEIGHT = 3;

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
                    fn.setDataHost(NptCommonUtil.getMapDataHost(seg.getFromPoint().getDataHost()));
                    fn.setContent(seg.getFromPoint().getTitle());
                    fn.setLabel(seg.getFromPoint().getTitle());
                    fn.setFlag(true);
                    fn.setIgnore(true);
                    fn.setName(seg.getFromPoint().getuId());
                    fn.setSymbolSize(35);

                    fn.setPoolId(seg.getFromPoint().getPoolId());
                    fn.setUkValue(seg.getFromPoint().getUkValue());



                    tn.setId(seg.getToPoint().getuId());
                    tn.setDataHost(NptCommonUtil.getMapDataHost(seg.getToPoint().getDataHost()));
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

    /**
     *  author: zhanglei
     *  date:   2017/05/23 16:44
     *  note: 
     *          d3
     */
    public static void convertForD3(NptBaseMap map, NptGraphData data) {

        // link去重
        Map<String,String> linkTitle = new HashMap<>();

        if(null != map && null != data){

            if(null != map.getSegments() && !map.getSegments().isEmpty()){

                for(NptMapSegment seg:map.getSegments()){
                    NptGraphNode fn = new NptGraphNode();
                    NptGraphNode tn = new NptGraphNode();


                    fn.setId(seg.getFromPoint().getuId());
                    if(seg.getFromPoint().getuId().equals(map.getRootPoint().getuId())){
                        fn.setWeight(ROOT_NODE_WEIGHT);
                    }else{
                        fn.setWeight(SIMPLE_NODE_WEIGHT);
                    }
                    fn.setDataHost(NptCommonUtil.getMapDataHost(seg.getFromPoint().getDataHost()));
                    fn.setContent(seg.getFromPoint().getTitle());
                    fn.setLabel(seg.getFromPoint().getTitle());
                    fn.setFlag(true);
                    fn.setIgnore(true);
                    fn.setName(seg.getFromPoint().getuId());
                    fn.setSymbolSize(35);

                    fn.setPoolId(seg.getFromPoint().getPoolId());
                    fn.setUkValue(seg.getFromPoint().getUkValue());



                    tn.setId(seg.getToPoint().getuId());
                    if(seg.getToPoint().getuId().equals(map.getRootPoint().getuId())){
                        tn.setWeight(ROOT_NODE_WEIGHT);
                    }else{
                        tn.setWeight(SIMPLE_NODE_WEIGHT);
                    }
                    tn.setDataHost(NptCommonUtil.getMapDataHost(seg.getToPoint().getDataHost()));
                    tn.setContent(seg.getToPoint().getTitle());
                    tn.setLabel(seg.getToPoint().getTitle());
                    tn.setFlag(true);
                    tn.setIgnore(true);
                    tn.setName(seg.getToPoint().getuId());
                    tn.setSymbolSize(35);

                    tn.setPoolId(seg.getToPoint().getPoolId());
                    tn.setUkValue(seg.getToPoint().getUkValue());

                    // 将关系作为一个节点
                    for (String title : seg.getLinkLine().getTitle().trim().split("\\s+")) {
                        if(StringUtils.isNotEmpty(title)){
                            NptGraphNode ln = new NptGraphNode();
                            NptGraphLink flk = new NptGraphLink();
                            NptGraphLink tlk = new NptGraphLink();

                            linkTitle.computeIfAbsent(title, k -> UUID.randomUUID().toString().replace("-", ""));

                            ln.setId(linkTitle.get(title));
                            ln.setWeight(LINK_NODE_WEIGHT);
                            ln.setDataHost(NptCommonUtil.getMapDataHost_Connection());
                            ln.setContent(seg.getToPoint().getTitle());
                            ln.setLabel(title);
                            ln.setFlag(true);
                            ln.setIgnore(true);
                            ln.setName(linkTitle.get(title));
                            ln.setSymbolSize(35);

                            ln.setPoolId(seg.getToPoint().getPoolId());
                            ln.setUkValue(seg.getToPoint().getUkValue());


                            flk.setSource(fn.getId());
                            flk.setTarget(ln.getId());
                            flk.setWeight(ROOT_LINK_WEIGHT);


                            tlk.setSource(ln.getId());
                            tlk.setTarget(tn.getId());
                            tlk.setWeight(SIMPLE_LINK_WEIGHT);

                            data.getNodes().add(ln);
                            data.getLinks().add(flk);
                            data.getLinks().add(tlk);
                        }
                    }




                    data.getNodes().add(fn);
                    data.getNodes().add(tn);

                }

            }

        }
    }

}
