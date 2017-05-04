package com.kangyonggan.app.fortune.web.controller;


import com.kangyonggan.app.fortune.common.util.StringUtil;

/**
 * @author kangyonggan
 * @since 2016/12/10
 */
public class BaseController {

    private String PATH_ROOT;
    private static final String LIST = "/list";
    private static final String INDEX = "/index";
    private static final String FORM = "/form";
    private static final String DETAIL = "/detail";

    public BaseController() {
        String className = getClass().getSimpleName();
        String arr[] = StringUtil.camelToArray(className);

        PATH_ROOT = "";
        for (int i = 0; i < arr.length - 1; i++) {
            if (i != 0) {
                PATH_ROOT += "/";
            }
            PATH_ROOT += arr[i];
        }
    }

    protected String getPathRoot() {
        return PATH_ROOT;
    }

    protected String getPathIndex() {
        return PATH_ROOT + INDEX;
    }

    protected String getPathList() {
        return PATH_ROOT + LIST;
    }

    protected String getPathForm() {
        return PATH_ROOT + FORM;
    }

    protected String getPathDetail() {
        return PATH_ROOT + DETAIL;
    }

}
