package com.process.learn.gen.service;

import com.process.learn.MbkBaseTest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Lynn
 * @since 2019-04-18
 */
@Slf4j
public class MbkGenManagerTest extends MbkBaseTest {

    @Autowired
    private MbkGenManager mbkGenManager;

    @Test
    public void nextSeq() {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(12, 100, 1, TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>());
//        for (int i = 0; i < 100; i++) {
//            executor.execute(() -> {
//                String dev_b01 = mbkGenManager.nextSeq("AGENT_CODE", null);
//                log.info("Code ==>,{}", dev_b01);
//            });
//        }
//        executor.shutdown();
//        while (true) {
//        }

        List<String> agent_code = mbkGenManager.nextSeqs("WMS_SEQ_REPAIR", 10, null);
//        String dev_b01 = mbkGenManager.nextSeq("AGENT_CODE", null);
        log.info("Code ==>,{}", agent_code);
    }

}