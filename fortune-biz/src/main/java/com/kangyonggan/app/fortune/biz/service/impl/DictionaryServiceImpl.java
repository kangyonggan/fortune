package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
@Service
public class DictionaryServiceImpl extends BaseService<Dictionary> implements DictionaryService {

    @Override
    @LogTime
    @CacheGetOrSave("dictionary:type:{0}:code:{1}")
    public boolean exists(String type, String code) {
        Dictionary dictionary = new Dictionary();
        dictionary.setIsDeleted(AppConstants.IS_DELETED_NO);
        dictionary.setType(type);
        dictionary.setCode(code);

        return super.exists(dictionary);
    }
}
