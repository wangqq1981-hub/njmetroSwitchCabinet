package com.zhixin.api.client;

import com.zhixin.api.req.TokenReq;
import com.zhixin.common.core.domain.Result;
import com.zhixin.common.core.domain.SysUser;

public interface UserClient {
   Result<SysUser> authToken(TokenReq req);
}
