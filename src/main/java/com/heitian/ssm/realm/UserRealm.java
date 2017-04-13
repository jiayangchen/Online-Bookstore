package com.heitian.ssm.realm;

import com.heitian.ssm.model.User;
import com.heitian.ssm.model.UserRole;
import com.heitian.ssm.service.UserRoleService;
import com.heitian.ssm.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.fromRealm(getName()).iterator().next();
        User user = userService.getUserByName(userName);
        if(user.getRid() == 2){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            //可调用setRoles(List<String>)添加角色集合
            //可调用setStringPermissions(List<String>)添加权限集合
            return info;
        }else if(user.getRid() == 3){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("manager");
            return info;
        }else if(user.getRid() == 1){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("user");
            return info;
        }else{
            return null;
        }
    }

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        User user = null;
        // 把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 从UsernamePasswordToken中获取username
        String username = upToken.getUsername();
        // 若用户不存在，抛出UnknownAccountException异常
        user = userService.getUserByName(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        // 4.
        // 根据用户的情况，来构建AuthenticationInfo对象并返回，通常使用的实现类为SimpleAuthenticationInfo
        // 以下信息从数据库中获取
        // （1）principal：认证的实体信息，可以是email，也可以是数据表对应的用户的实体类对象
        Object principal = username;
        // （2）credentials：密码
        Object credentials = user.getuPassword();
        // （3）realmName：当前realm对象的name，调用父类的getName()方法即可
        String realmName = getName();
        // （4）盐值：取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt,
                realmName);
        return info;
    }
}
