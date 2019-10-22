package com.process.zuul.app.api;

import com.process.common.domain.BaseCodeName;
import com.process.zuul.app.client.BusinessFeignClient;
import com.process.zuul.app.service.TenantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lynn
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TenantServiceApi {

    private final TenantService tenantService;
    private final BusinessFeignClient businessFeignClient;

    @GetMapping("/tenants")
    public ResponseEntity tenants() {
        return ResponseEntity.ok(tenantService.findAll());
    }

    @GetMapping("/codeNames")
    public List<BaseCodeName> codeNames() {
        return businessFeignClient.codeNames();
    }

}
