<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>

<form id="form" method="post" action="${ctx}/dashboard/merchant/info" class="form-horizontal"
      enctype="multipart/form-data">
    <div class="tabbable">
        <ul class="nav nav-tabs padding-16">
            <li class="active">
                <a data-toggle="tab" href="#edit-basic" aria-expanded="true">
                    <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
                    基础信息
                </a>
            </li>

            <li class="">
                <a data-toggle="tab" href="#edit-password" aria-expanded="false">
                    <i class="blue ace-icon fa fa-key bigger-125"></i>
                    修改密码
                </a>
            </li>
        </ul>

        <div class="tab-content profile-edit-tab-content">
            <div id="edit-basic" class="tab-pane active">
                <h4 class="header blue bolder smaller">基础信息</h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">商户号<span class="red">*</span></label>

                    <div class="col-xs-12 col-sm-5">
                        <input class="form-control readonly" readonly type="text" value="${merchant.merchCo}"
                               name="merchCo">
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="merchNm">商户名称<span class="red">*</span></label>

                    <div class="col-xs-12 col-sm-5">
                    <@s.formInput "merchant.merchNm" 'class="form-control" placeholder="默认为UTF-8编码"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="charset">字符集</label>

                    <div class="col-xs-12 col-sm-5">
                    <@s.formInput "merchant.charset" 'class="form-control" placeholder="默认为UTF-8编码"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="fullname">调试模式<span
                            class="red">*</span></label>

                    <div class="col-xs-12 col-sm-5">
                        <label class="inline">
                            <input name="isDebug" value="0" type="radio" class="ace"
                                   <#if merchant.isDebug==0>checked</#if>>
                            <span class="lbl middle"> 否</span>
                        </label>

                        &nbsp; &nbsp; &nbsp;
                        <label class="inline">
                            <input name="isDebug" value="1" type="radio" class="ace"
                                   <#if merchant.isDebug==1>checked</#if>>
                            <span class="lbl middle"> 是</span>
                        </label>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="ftpHost">FTP主机名</label>

                    <div class="col-xs-12 col-sm-5">
                    <@s.formInput "merchant.ftpHost" 'class="form-control" placeholder="请输入FTP主机名"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="ftpUser">FTP用户名</label>

                    <div class="col-xs-12 col-sm-5">
                    <@s.formInput "merchant.ftpUser" 'class="form-control" placeholder="请输入FTP用户名" autocomplete="off"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="ftpPwd">FTP密码</label>

                    <div class="col-xs-12 col-sm-5">
                    <@s.formInput path="merchant.ftpPwd" attributes='class="form-control" placeholder="请输入FTP密码" autocomplete="off"' fieldType="password"/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="ftpDir">FTP目录</label>

                    <div class="col-xs-12 col-sm-5">
                    <@s.formInput "merchant.ftpDir" 'class="form-control" placeholder="请输入FTP目录"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="publicKey">上传商户公钥</label>

                    <div class="col-xs-12 col-sm-5">
                        <input type="file" id="publicKey" name="publicKey" class="ace ace-file-input"/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">发财付公钥和商户公钥</label>

                    <div class="col-xs-12 col-sm-5">
                    <#if merchant.merchCo=='201705050000001'>
                        <p>公测商户私钥：<a href="${ctx}/downloads/201705050000001_merch_pkcs8_rsa_private_key_2048.pem"
                                     target="_blank">201705050000001_merch_pkcs8_rsa_private_key_2048.pem</a></p>
                    </#if>
                    <#if merchant.publicKeyPath==''>
                        <p>当前商户公钥：未上传</p>
                    <#else>
                        <p>当前商户公钥：<a href="${ctx}${merchant.publicKeyPath}"
                                     target="_blank">${merchant.publicKeyPath}</a></p>
                    </#if>
                        <p>当前发财付公钥：<a href="${ctx}/downloads/${merchant.merchCo}_fpay_rsa_public_key_2048.pem"
                                      target="_blank">${merchant.merchCo}_fpay_rsa_public_key_2048.pem</a></p>
                    </div>
                </div>
            </div>

            <div id="edit-password" class="tab-pane">
                <div class="space-10"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">新密码<span class="red">*</span></label>

                    <div class="col-xs-12 col-sm-5">
                        <input type="password" id="password" name="password" class="form-control"
                               placeholder="密码:6至20位的字母数字组合" autocomplete="off"/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">确认密码<span class="red">*</span></label>

                    <div class="col-xs-12 col-sm-5">
                        <input type="password" name="rePassword" class="form-control" placeholder="密码:6至20位的字母数字组合"
                               autocomplete="off"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="clearfix form-actions">
        <div class="col-xs-offset-3">
            <button id="submit" class="btn btn-inverse" data-loading-text="正在提交...">
                <i class="ace-icon fa fa-check"></i>
            <@s.message "app.button.save"/>
            </button>
        </div>
    </div>
</form>

<script src="${ctx}/static/app/js/dashboard/merchant/info/index.js"></script>
