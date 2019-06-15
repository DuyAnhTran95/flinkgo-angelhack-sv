package vn.crazyx.flinkgo.service.searchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.dao.Mentor;
import vn.crazyx.flinkgo.dao.MentorRepository;
import vn.crazyx.flinkgo.dao.User;
import vn.crazyx.flinkgo.dao.UserRepository;
import vn.crazyx.flinkgo.dto.MentorData;
import vn.crazyx.flinkgo.service.user.AuthenticationUserService;

@Service
public class MentorBasicSearchService implements MentorSearchService {
    
    private static final double GRID = 0.03;
    
    @Autowired
    AuthenticationUserService authService;
    
    @Autowired
    MentorRepository mentorRepo;
    
    @Autowired
    UserRepository userRepo;

    @Override
    public List<MentorData> getMentorSearchResult() {
        AuthUser authUser = authService.getUserFromContext();
        List<MentorData> mentorData = new ArrayList<>();
        
        List<Mentor> users = userRepo.findById(authUser.getUserId())
                .map(mentor -> findInGrid(mentor, GRID)).get();
        
        if (users != null && !users.isEmpty()) {
            mentorData = users.stream().map(mentor -> new MentorData(mentor)).collect(Collectors.toList());
        }
        
        return mentorData;
    }
    
    private List<Mentor> findInGrid(User user, double grid) {
        Double upperLat = user.getLatitude() + grid;
        Double lowerLat = user.getLatitude() - grid;
        Double upperLong = user.getLongitude() + grid;
        Double lowerLong = user.getLongitude() - grid;
        
        return mentorRepo.findAllValidMentor(lowerLat, upperLat, lowerLong, upperLong, user.getLevel());
    }

}
