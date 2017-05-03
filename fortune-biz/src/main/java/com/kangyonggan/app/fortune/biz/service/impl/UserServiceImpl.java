package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.UserService;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.User;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Override
    @LogTime
    @CacheGetOrSave("user:username:{0}")
    public User findUserByUsername(String username) {
        User user = new User();
        user.setIsDeleted(AppConstants.IS_DELETED_NO);
        user.setUsername(username);

        return super.selectOne(user);
    }
}
