package top.imyzt.study.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.domain.User;
import top.imyzt.study.miaosha.mapper.UserMapper;

/**
 * @author imyzt
 * @date 2019/3/8 17:26
 * @description UserService
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getById(Integer id) {
        return userMapper.findById(id);
    }
}
