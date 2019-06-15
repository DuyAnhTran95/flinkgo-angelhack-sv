package vn.crazyx.flinkgo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MentorRepository extends JpaRepository<Mentor, Integer>, 
        CrudRepository<Mentor, Integer> {

    @Query(nativeQuery = true, value = "select * from mentor where latitude between ?1 and ?2 and "
            + "longitude between ?3 and ?4 and level = ?5 limit 5")
    List<Mentor> findAllValidMentor(Double lowerLat, Double upperLat, Double lowerLong, Double upperLong, Integer level);

}
