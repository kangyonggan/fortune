package com.kangyonggan.app.fortune.biz.service;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
public interface DictionaryService {

    /**
     * 校验字典是否存在
     *
     * @param type
     * @param code
     * @return
     */
    boolean exists(String type, String code);

}
