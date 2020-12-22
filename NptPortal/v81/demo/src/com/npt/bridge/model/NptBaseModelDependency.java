package com.npt.bridge.model;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptBusinessCode;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by owen on 2017/3/21.
 */
public class NptBaseModelDependency implements Serializable{

    private Collection<NptLogicDataProvider> providers;

    private Collection<NptBusinessCode> businessCodes;


    public Collection<NptLogicDataProvider> getProviders() {
        return providers;
    }

    public void setProviders(Collection<NptLogicDataProvider> providers) {
        this.providers = providers;
    }

    public Collection<NptBusinessCode> getBusinessCodes() {
        return businessCodes;
    }

    public void setBusinessCodes(Collection<NptBusinessCode> businessCodes) {
        this.businessCodes = businessCodes;
    }
}
