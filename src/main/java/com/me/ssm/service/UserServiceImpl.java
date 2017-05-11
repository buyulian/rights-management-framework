package com.me.ssm.service;

import com.me.ssm.dao.UserDao;
import com.me.ssm.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    public User getUserById(String id) {
        return userDao.selectUserById(id);
    }

    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }

    public void add(User user) {
        userDao.add(user);
    }

}
