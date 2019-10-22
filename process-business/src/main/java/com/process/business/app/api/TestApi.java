package com.process.business.app.api;

import com.process.common.domain.BaseCodeName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lynn
 * @since 2019-09-03
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestApi {

    @GetMapping("/t1")
    public ResponseEntity test1() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/codeNames")
    public List<BaseCodeName> codeNames() {
        log.info("哈哈哈....");
        List<BaseCodeName> codeNames = new ArrayList<>();
//        codeNames.add(new BaseCodeName() {{
//            setId(1L);
//            setCode("0001");
//            setName("测试");
//        }});
        return codeNames;
    }
}
