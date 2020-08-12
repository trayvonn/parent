package com.platform.auth.service.impl;

import com.platform.admin.api.entity.OauthClient;
import com.platform.auth.feign.OauthClientFeignClient;
import com.platform.common.core.base.R;
import com.platform.common.core.feign.FeignOptional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author 吴邪
 * @date 2020/8/12 14:14
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OauthClientFeignClient oauthClientFeignClient;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        R<OauthClient> byClientId = oauthClientFeignClient.getByClientId(clientId);
        OauthClient oauthClient = FeignOptional.of(byClientId).orElseThrow(() -> new ClientRegistrationException("clientId不存在"));
        return new BaseClientDetails(oauthClient.getClientId(),null,oauthClient.getScope(),oauthClient.getAuthorizedGrantTypes(),null);
    }
}
