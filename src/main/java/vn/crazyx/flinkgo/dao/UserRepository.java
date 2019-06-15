package vn.crazyx.flinkgo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>,
        JpaRepository<User, String> {
    User findOneByUserName(String userName);
    
    User findOneByRefreshToken(String token);
    
    @Query(nativeQuery = true, value = "select * from user where latitude between ?1 and ?2 and "
            + "longitude between ?3 and ?4 limit 5")
    List<User> findAllValidUser(double lowerLat, double upperLat, double lowerLong, double upperLong);

}
