package com.kangyonggan.app.fortune.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.fortune.biz.service.DictionaryService;
import com.kangyonggan.app.fortune.common.util.StringUtil;
import com.kangyonggan.app.fortune.mapper.DictionaryMapper;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/5/17
 */
@Service
public class DictionaryServiceImpl extends BaseService<Dictionary> implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    @LogTime
    public List<Dictionary> searchDictionsries(int pageNum, String type, String value) {
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(type)) {
            criteria.andEqualTo("type", type);
        }

        if (StringUtils.isNotEmpty(value)) {
            criteria.andLike("value", StringUtil.toLikeString(value));
        }

        example.setOrderByClause("sort asc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return myMapper.selectByExample(example);
    }

    @Override
    @LogTime
    public Dictionary findDictionaryById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    public void updateDictionary(Dictionary dictionary) {
        myMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    @LogTime
    public void saveDictionary(Dictionary dictionary) {
        myMapper.insertSelective(dictionary);
    }

    @Override
    @LogTime
    public boolean existsDictionaryCode(String code) {
        Dictionary dictionary = new Dictionary();
        dictionary.setCode(code);

        return dictionaryMapper.selectCount(dictionary) == 1;
    }

    @Override
    @LogTime
    public List<Dictionary> findDictionariesByType(String type) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("type", type).andEqualTo("isDeleted", AppConstants.IS_DELETED_NO);

        example.setOrderByClause("sort asc");
        return myMapper.selectByExample(example);
    }
}
