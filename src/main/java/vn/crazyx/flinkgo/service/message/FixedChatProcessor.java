package vn.crazyx.flinkgo.service.message;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;

@Service
public class FixedChatProcessor implements MessageProcessingService {
    Logger log = Logger.getLogger("MessageProcessor");
    
    String[] annotators = {"wseg"}; 

    @Override
    public RequestType processMessageRequestType(String message) throws IOException {
        
//        VnCoreNLP pipeline = new VnCoreNLP(annotators);
//        Annotation annotation = new Annotation(message);
//        
//        pipeline.annotate(annotation);
//        
//        List<String> words = annotation.getWords().stream().map(Word::getForm).collect(Collectors.toList());
        
        
        int findKeyword = message.indexOf("tìm");
        int friendKeyword = message.indexOf("bạn");
        int mentorKeyword = message.indexOf("thầy");
        
        log.info(message.toString());
        
        if (mentorKeyword > findKeyword) {
            return RequestType.MENTOR;
        } else if (friendKeyword > findKeyword) {
            return RequestType.FRIEND;
        } else {
            return RequestType.UNKOWN;
        }
    }
}
