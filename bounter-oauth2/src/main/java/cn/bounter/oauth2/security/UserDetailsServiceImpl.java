package cn.bounter.oauth2.security;

import cn.bounter.oauth2.dao.UserDao;
import cn.bounter.oauth2.dao.UserRoleDao;
import cn.bounter.oauth2.model.po.User;
import cn.bounter.oauth2.model.po.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 自定义的用户认证服务
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        User user = userDao.selectOne(new User().setUsername(username));
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        //查询用户权限
        Collection<GrantedAuthority> authorityList = userRoleDao.selectAll(new UserRole().setUsername(username))
                                                                .stream()
                                                                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                                                                .collect(Collectors.toList());

        return new UserDetailsImpl(user, authorityList);
    }
}
