package vn.crazyx.flinkgo.service.searchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.dao.User;
import vn.crazyx.flinkgo.dao.UserRepository;
import vn.crazyx.flinkgo.dto.UserData;
import vn.crazyx.flinkgo.service.user.AuthenticationUserService;

@Service
public class UserBasicSearchService implements UserSearchService {
    
    private static final double GRID = 0.03;
    
    @Autowired
    AuthenticationUserService authService;
    
    @Autowired
    UserRepository userRepo;

    @Override
    public List<UserData> getUserSearchResult() {
        AuthUser authUser = authService.getUserFromContext();
        List<UserData> userData = new ArrayList<>();
        
        List<User> users = userRepo.findById(authUser.getUserId()).map(user -> findInGrid(user, GRID)).get();
        
        if (users != null && !users.isEmpty()) {
            userData = users.stream().map(user -> new UserData(user)).collect(Collectors.toList());
        }
        
        return userData;
    }

    private List<User> findInGrid(User user, double grid) {
        Double upperLat = user.getLatitude() + grid;
        Double lowerLat = user.getLatitude() - grid;
        Double upperLong = user.getLongitude() + grid;
        Double lowerLong = user.getLongitude() - grid;
        
        return userRepo.findAllValidUser(lowerLat, upperLat, lowerLong, upperLong);
    }
}
