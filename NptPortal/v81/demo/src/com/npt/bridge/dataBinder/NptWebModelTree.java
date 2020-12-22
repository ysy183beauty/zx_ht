package com.npt.bridge.dataBinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 19:50
 * 描述:
 */
public class NptWebModelTree<M,G,P> implements Serializable{

    private M root;
    private Collection<NptWebModelSketelon> skeletons;

    public M getRoot() {
        return root;
    }

    public void setRoot(M root) {
        this.root = root;
    }

    public Collection<NptWebModelSketelon> getSkeletons() {
        return skeletons;
    }

    public void setSkeletons(Collection<NptWebModelSketelon> skeletons) {
        this.skeletons = skeletons;
    }

    public NptWebModelTree(){
        this.root = null;
        this.skeletons = new ArrayList();
    }

    public NptWebModelSketelon instanceSkeleton(){
        NptWebModelSketelon skeleton = new NptWebModelSketelon();
        skeleton.setLeafs(new ArrayList());
        return skeleton;
    }

    public class NptWebModelSketelon{
        private G sketeton;
        private Collection<P> leafs;

        public G getSketeton() {
            return sketeton;
        }

        public void setSketeton(G sketeton) {
            this.sketeton = sketeton;
        }

        public Collection<P> getLeafs() {
            return leafs;
        }

        public void setLeafs(Collection<P> leafs) {
            this.leafs = leafs;
        }
    }
}
