<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>

<form id="form" method="post" action="${ctx}/dashboard/user/profile" class="form-horizontal"
      enctype="multipart/form-data">
    <input type="hidden" name="id" value="${user.id}"/>
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
                <h4 class="header blue bolder smaller">基本资料</h4>

                <div class="row">
                    <div class="col-xs-12 col-sm-4" id="avatar-div">
                        <label class="ace-file-input ace-file-multiple">
                            <span class="ace-file-container hide-placeholder selected">
                                <span class="ace-file-name large">
                                    <img class="middle"
                                         src="<#if userProfile.largeAvatar?has_content>${ctx}/${userProfile.largeAvatar}<#else>${ctx}/static/ace/dist/avatars/profile-pic.jpg</#if>">
                                    <i class=" ace-icon fa fa-picture-o file-image"></i>
                                </span>
                            </span>
                            <a id="remove" class="remove" href="javascript:">
                                <i class=" ace-icon fa fa-times"></i>
                            </a>
                        </label>
                    </div>

                    <div class="vspace-12-sm"></div>

                    <div class="col-xs-12 col-sm-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right">用户名<span class="red">*</span></label>

                            <div class="col-sm-8">
                                <input class="col-xs-12 col-sm-10 readonly" readonly type="text"
                                       value="${user.username}" name="username">
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right" for="fullname">姓名<span
                                    class="red">*</span></label>

                            <div class="col-sm-8">
                                <input class="col-xs-12 col-sm-10" type="text" placeholder="姓名:2至4个汉字"
                                       name="fullname" value="${user.fullname}">
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right" for="fullname">身份证</label>

                            <div class="col-sm-8">
                                <input class="col-xs-12 col-sm-10" type="text" placeholder="请输入身份证号码"
                                       name="idCard" value="${userProfile.idCard}">
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right" for="fullname">性别<span
                                    class="red">*</span></label>

                            <div class="col-sm-8">
                                <label class="inline">
                                    <input name="sex" value="0" type="radio" class="ace"
                                           <#if userProfile.sex==0>checked</#if>>
                                    <span class="lbl middle"> 男</span>
                                </label>

                                &nbsp; &nbsp; &nbsp;
                                <label class="inline">
                                    <input name="sex" value="1" type="radio" class="ace"
                                           <#if userProfile.sex==1>checked</#if>>
                                    <span class="lbl middle"> 女</span>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <hr>
                <div class="space"></div>
                <h4 class="header blue bolder smaller">联系信息</h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">手机号</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "user.mobile" 'class="form-control" placeholder="请输入11位的手机号"'/>
                        <input type="hidden" id="old-mobile" value="${user.mobile!''}"/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">座机号</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "userProfile.phone" 'class="form-control" placeholder="请输入座机号"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">邮箱</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "user.email" 'class="form-control" placeholder="请输入邮箱，可以用来找回密码"'/>
                        <input type="hidden" id="old-email" value="${user.email!''}"/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">QQ号</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "userProfile.qq" 'class="form-control" placeholder="请输入QQ账号"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">微信号</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "userProfile.weixin" 'class="form-control" placeholder="请输入微信账号"'/>
                    </div>
                </div>

                <div class="space"></div>

                <h4 class="header blue bolder smaller">社交信息</h4>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">暂住址</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "userProfile.address" 'class="form-control" placeholder="请输入您暂时居住的地址"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">个人网站</label>
                    <div class="col-xs-12 col-sm-5">
                        <@spring.formInput "userProfile.webSite" 'class="form-control" placeholder="例:http://kangyonggan.com"'/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">备注</label>
                    <div class="col-xs-12 col-sm-5">
                        <textarea rows="5" class="form-control" name="remarks"
                                  placeholder="个人备注信息">${userProfile.remarks}</textarea>
                    </div>
                </div>
            </div>

            <div id="edit-password" class="tab-pane">
                <div class="space-10"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">新密码<span class="red">*</span></label>

                    <div class="col-sm-9">
                        <input type="password" id="password" name="password" class="form-control"
                               placeholder="密码:6至20位的字母数字组合" autocomplete="off"/>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">确认密码<span class="red">*</span></label>

                    <div class="col-sm-9">
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

<script src="${ctx}/static/app/js/dashboard/user/profile.js"></script>
