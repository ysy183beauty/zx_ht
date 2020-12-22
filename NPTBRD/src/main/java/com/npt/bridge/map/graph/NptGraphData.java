package com.npt.bridge.map.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * author: owen
 * date:   2017/4/12 下午5:50
 * note:
 */
public class NptGraphData implements Serializable{

    public NptGraphData(){
        this.nodes = new HashSet<>();
        this.links = new HashSet<>();
    }

    private Set<NptGraphNode> nodes;

    private Set<NptGraphLink> links;

    public Set<NptGraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<NptGraphNode> nodes) {
        this.nodes = nodes;
    }

    public Set<NptGraphLink> getLinks() {
        return links;
    }

    public void setLinks(Set<NptGraphLink> links) {
        this.links = links;
    }


    public String getNodesJSON(){
        StringBuffer sb = new StringBuffer();

        sb.append("[");

        Iterator<NptGraphNode> iterator = nodes.iterator();
        while (iterator.hasNext()){
            NptGraphNode node = iterator.next();
            sb.append("{")
                    .append("id:'").append(node.getName()).append("'").append(",")
                    .append("name:'").append(node.getName()).append("'").append(",")
                    .append("weight:").append(node.getWeight()).append(",")
                    .append("dataHost:'").append(node.getDataHost()).append("'").append(",")
                    .append("symbolSize:").append(node.getSymbolSize()).append(",")
                    .append("label:'").append(node.getLabel()).append("',")
                    .append("content:'").append(node.getContent()).append("',")
                    .append("poolId:'").append(node.getPoolId()).append("',")
                    .append("ukValue:'").append(node.getUkValue()).append("'")
                    .append("}");

            if(iterator.hasNext()){
                sb.append(",");
            }
        }

        sb.append("]");

        return sb.toString();
    }


    public String getLinksJSON(){
        StringBuffer sb = new StringBuffer();

        sb.append("[");

        Iterator<NptGraphLink> iterator = links.iterator();
        while (iterator.hasNext()) {
            NptGraphLink link = iterator.next();
            sb.append("{")
                    .append("source:'").append(link.getSource()).append("',")
                    .append("target:'").append(link.getTarget()).append("',")
                    .append("weight:").append(link.getWeight()).append(",")
                    .append("itemStyle:{normal:{text:'").append(link.getText()).append("',textPosition:'inside'}}")
                    .append("}");

            if(iterator.hasNext()){
                sb.append(",");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
