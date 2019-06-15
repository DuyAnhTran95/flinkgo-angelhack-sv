package vn.crazyx.flinkgo.service.user;

import vn.crazyx.flinkgo.config.AuthUser;

public interface AuthenticationUserService {
    AuthUser getUserFromContext();
}
