package vn.crazyx.flinkgo.service.message;

import java.io.IOException;

public interface MessageProcessingService {
    public RequestType processMessageRequestType(String message) throws IOException;
}
