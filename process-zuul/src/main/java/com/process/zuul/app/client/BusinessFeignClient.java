package com.process.zuul.app.client;

import com.process.common.domain.BaseCodeName;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Danfeng
 * @since 2018/12/12
 */
@FeignClient("process-business")
public interface BusinessFeignClient {

    @GetMapping("/business/api/test/codeNames")
    List<BaseCodeName> codeNames();

}
