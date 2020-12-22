package com.npt.ces.cw.action;


import com.npt.bridge.dict.NptDict;
import org.springframework.stereotype.Controller;

@Controller("npt.ces.cwbs")
public class NptCWBSAction extends NptCwAction{


    @Override
    public NptDict getCreditEntityType() {
        return NptDict.BMH_WARN_BS;
    }
}
