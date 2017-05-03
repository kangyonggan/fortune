package com.kangyonggan.app.fortune.biz.shiro;

import com.kangyonggan.app.fortune.biz.service.RedisService;
import com.kangyonggan.app.fortune.biz.util.PropertiesUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author kangyonggan
 * @since 2016/12/31
 */
public class MyEnterpriseCacheSessionDAO extends EnterpriseCacheSessionDAO {

    @Autowired
    private RedisService redisService;

    /**
     * redis键的前缀
     */
    private String prefix = PropertiesUtil.getProperties("redis.prefix") + ":";

    /**
     * 创建session，保存到redis数据库
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        redisService.set(prefix + sessionId.toString(), session);

        return sessionId;
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisService.get(prefix + sessionId.toString());
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        redisService.set(prefix + session.getId().toString(), session);
    }

    /**
     * 删除session
     *
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        redisService.delete(prefix + session.getId().toString());
    }

}
