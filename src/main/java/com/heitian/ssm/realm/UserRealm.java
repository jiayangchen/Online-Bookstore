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
import javax.annotation.Resource;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.fromRealm(getName()).iterator().next();
        if(userName.equals("Eason")){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }else if(userName.equals("Jack")){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("manager");
            return info;
        }else if(userName.equals("5140379040")){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("user");
            return info;
        }else{
            return null;
        }
        /*User u = userService.getUserByName(userName);
        if(u == null){
            throw new UnknownAccountException("用户不存在！");
        }else{
            List<UserRole> urList = userRoleService.getAllUserRoles();
            for (UserRole ur : urList){
                if(ur.getUId().equals(u.getUid())){
                    if(ur.getRId() == 1){
                        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                        info.addRole("user");
                        return info;
                    }else if(ur.getRId() == 2){
                        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                        info.addRole("admin");
                        return info;
                    }else if(ur.getRId() == 3){
                        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                        info.addRole("manager");
                        return info;
                    }
                }
            }
            return null;
        }*/
    }

    /**
     * 登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        User user = null;
        // 1. 把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 2. 从UsernamePasswordToken中获取email
        String username = upToken.getUsername();
        // 3. 若用户不存在，抛出UnknownAccountException异常
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
