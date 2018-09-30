package cn.bounter.oauth2.controller;

import cn.bounter.common.model.ResponseData;
import cn.bounter.common.util.CipherUtil;
import cn.bounter.common.util.IdGenerator;
import cn.bounter.oauth2.model.po.Client;
import cn.bounter.oauth2.model.po.User;
import cn.bounter.oauth2.service.ClientService;
import cn.bounter.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * 注册控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;


    /**
     * 注册客户端应用
     * @param client
     * @return
     */
    @PostMapping("/client")
    public ResponseData<?> client(@RequestBody Client client) {
        //生成client_id和client_secret
        long id = IdGenerator.getId();
        String seed = UUID.randomUUID().toString();
        String clientId = CipherUtil.md5Hex(String.valueOf(id) + seed);
        String clientSecret = CipherUtil.sha256Hex(String.valueOf(id) + seed);

        //Bcrypt加密client_secret
        clientService.add(client.setId(id).setClientId(clientId).setClientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(clientSecret)).setCreateTime(new Date()));

        //返回加密前的client_secret
        return new ResponseData<>().data(client.setClientSecret(clientSecret));
    }


    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/user")
    public ResponseData<?> user(@RequestBody User user) {
        //如果未指定用户类型，用户类型默认是“注册会员”
        if (user.getIsStaff() == null) {
            user.setIsStaff(false);
        }

        //用户名防重
        if(userService.findOne(new User().setUsername(user.getUsername())) != null) {
            return new ResponseData<>().fail("用户名已存在！");
        }

        //密码BCrypt加密
        userService.add(user.setId(IdGenerator.getId()).setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword())).setCreateTime(new Date()));

        return new ResponseData<>().data(user.setPassword(null));
    }

}
