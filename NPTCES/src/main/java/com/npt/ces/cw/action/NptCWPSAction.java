package com.npt.ces.cw.action;

import com.npt.bridge.dict.NptDict;
import org.springframework.stereotype.Controller;

@Controller("npt.ces.cwps")
public class NptCWPSAction extends NptCwAction {


    @Override
    public NptDict getCreditEntityType() {
        return NptDict.BMH_WARN_PS;
    }
}
