package com.kangyonggan.app.fortune.model.constants;

/**
 * @author kangyonggan
 * @since 2017/4/9 0009
 */
public interface AppConstants {

    /**
     * 默认编码
     */
    String CHARSET = "UTF-8";

    /**
     * 分页大小
     */
    int PAGE_SIZE = 10;

    /**
     * 是否删除
     */
    byte IS_DELETED_NO = 0;
    byte IS_DELETED_YES = 1;

    /**
     * 文件根路径的key
     */
    String FILE_PATH_ROOT = "file.root.path";

    /**
     * 文件上传路径
     */
    String FILE_UPLOAD = "upload/";

    /**
     * Shiro常量
     */
    String HASH_ALGORITHM = "SHA-1";
    int HASH_INTERATIONS = 2;
    int SALT_SIZE = 8;

    /**
     * 把验证码存放在session中的key
     */
    String KEY_CAPTCHA = "key-captcha";

    /**
     * command流水号在redis中的key
     */
    String COMMAND_SERIAL_NO = "command:serialNo";

    /**
     * 签约协议号在redis中的key
     */
    String SIGN_PROTOCOL_NO = "sign:protocolNo";

    /**
     * 默认证件类型， 身份证
     */
    String DEFAULT_ID_TP = "0";

    /**
     * 默认币种， 人民币
     */
    String DEFAULT_CUUR_CO = "00";

    /**
     * 默认账户类型， 借记卡
     */
    String DEFAULT_ACCT_TP = "00";

    /**
     * 商户角色
     */
    String DEFAULT_ROLE_CODE = "ROLE_MERCHANT";

    /**
     * 对账文件分隔符
     */
    String FILE_SPLIT = "|";
}

