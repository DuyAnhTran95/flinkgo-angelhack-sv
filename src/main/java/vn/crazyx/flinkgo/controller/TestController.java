package vn.crazyx.flinkgo.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind .annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.crazyx.flinkgo.config.ResponseSuccess;
import vn.crazyx.flinkgo.dto.ChatMessageRequest;
import vn.crazyx.flinkgo.dto.MentorData;
import vn.crazyx.flinkgo.dto.UserData;
import vn.crazyx.flinkgo.service.message.MessageProcessingService;
import vn.crazyx.flinkgo.service.message.RequestType;
import vn.crazyx.flinkgo.service.searchService.MentorSearchService;
import vn.crazyx.flinkgo.service.searchService.UserSearchService;

@RestController
@RequestMapping("/")
public class TestController {
    Logger log = Logger.getLogger("TestController");
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired 
    MessageProcessingService msgService;
    
    @Autowired MentorSearchService mentorSearchService;
    
    @Autowired UserSearchService userSearchService;
    
    @PostMapping("/chat")
    @ResponseBody
    public ResponseEntity<Object> success(@RequestBody ChatMessageRequest msg) throws IOException {
        
        RequestType type = msgService.processMessageRequestType(msg.getMessage().toLowerCase());
        
        ResponseSuccess res = new ResponseSuccess(HttpStatus.OK.value(), "success");

        switch(type) {
            case MENTOR:
                List<MentorData> mentors = mentorSearchService.getMentorSearchResult();
                res.addResults("mentors", mentors);
                res.addResults("message", "sau đây là thông tin của một số giáo viên tiếng Anh phù hợp với bạn");
                break;
            
            case FRIEND:
                List<UserData> users = userSearchService.getUserSearchResult();
                res.addResults("users", users);
                res.addResults("message", "sau đây là thông tin của một số bạn trong khu vực gần đây");
                break;
                
            default:
                res.addResults("message", "xin lỗi. mình không hiểu ý bạn");
                break;
                
        }
        
        return new ResponseEntity<Object>(res, HttpStatus.valueOf(res.getStatus()));
    }
    
}
