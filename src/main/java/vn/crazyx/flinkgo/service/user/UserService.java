package vn.crazyx.flinkgo.service.user;

import vn.crazyx.flinkgo.dao.User;

public interface UserService {
    User findByUserName(String userName);
    
    void save(User user);
    
    User getUserByRefreshToken(String token);
}
