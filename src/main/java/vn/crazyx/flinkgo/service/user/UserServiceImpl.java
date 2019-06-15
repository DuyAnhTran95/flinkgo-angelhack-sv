package vn.crazyx.flinkgo.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.dao.User;
import vn.crazyx.flinkgo.dao.UserRepository;

@Service
public class UserServiceImpl implements UserService, AuthenticationUserService {
    
    @Autowired
    UserRepository userRepo;
    
    @Override
    public User findByUserName(String userName) {
        return userRepo.findOneByUserName(userName);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User getUserByRefreshToken(String token) {
        return userRepo.findOneByRefreshToken(token);
    }

    @Override
    public AuthUser getUserFromContext() {
        return (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

}
