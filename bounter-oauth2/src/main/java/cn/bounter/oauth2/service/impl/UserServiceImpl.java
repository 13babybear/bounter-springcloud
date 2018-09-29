package cn.bounter.oauth2.service.impl;


import cn.bounter.common.model.BaseServiceImpl;
import cn.bounter.common.util.IdGenerator;
import cn.bounter.oauth2.dao.UserRoleDao;
import cn.bounter.oauth2.model.po.User;
import cn.bounter.oauth2.model.po.UserRole;
import cn.bounter.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserRoleDao userRoleDao;


    @Override
    @Transactional
    public void add(User user) {
        //新增用户
        super.add(user);

        //新增用户角色
        List<UserRole> userRoles = user.getRoles().stream()
                                                  .map(role -> new UserRole().setId(IdGenerator.getId())
                                                                            .setUsername(user.getUsername())
                                                                            .setRole(role))
                                                  .collect(Collectors.toList());
        userRoleDao.batchInsert(userRoles);
    }
}
