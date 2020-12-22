package com.npt.bridge.util;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * author: owen
 * date:   2017/4/20 下午4:27
 * note:
 */
public class NptSessionTaskHelper {


    /**
     *  author: owen
     *  date:   2017/4/20 下午4:28
     *  note:
     *          生成任务ID
     */
    public static String generateTaskId(){
        return UUID.randomUUID().toString();
    }

    /**
     *  author: owen
     *  date:   2017/4/20 下午4:32
     *  note:
     *          将任务添加到SESSION中
     */
    public static void putTask(HttpSession session,String taskId,NptAjaxTask task){

        if(null != session && null != task && !StringUtils.isBlank(taskId)){
            session.setAttribute(taskId,task);
        }
    }

    /**
     *  author: owen
     *  date:   2017/4/20 下午4:34
     *  note:
     *          从session中获取任务详情
     */
    public static NptAjaxTask getTask(HttpSession session,String taskId){
        if(null != session && !StringUtils.isBlank(taskId)){
            return (NptAjaxTask) session.getAttribute(taskId);
        }
        return null;
    }

    /**
     *  author: owen
     *  date:   2017/4/20 下午4:42
     *  note:
     *          更新任务信息
     */
    public static void updateTask(HttpSession session,String taskId,NptAjaxTask task){
        if(null != session && null != task && !StringUtils.isBlank(taskId)){
            session.removeAttribute(taskId);
            session.setAttribute(taskId,task);
        }
    }

    public static void appendTaskInfo(HttpSession session,String taskId,String appendText){
        NptAjaxTask task = getTask(session,taskId);
        if(null != task){
            task.setTextInfo(task.getTextInfo() + "\n" + appendText);
            NptSessionTaskHelper.updateTask(session,taskId,task);
        }
    }


    public static void removeTask(HttpSession session,String taskId){
        if(null != session && !StringUtils.isBlank(taskId)){
            session.removeAttribute(taskId);
        }
    }
}
