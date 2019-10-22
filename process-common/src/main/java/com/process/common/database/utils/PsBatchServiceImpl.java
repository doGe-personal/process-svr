package com.process.common.database.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * @author Danfeng
 * @since 2018/12/28
 */
@Slf4j
@RequiredArgsConstructor
public class PsBatchServiceImpl implements PsBatchService {

    private final SqlSessionFactory sessionFactory;

    @Override
    public <M, R> int runBatch(Iterator<R> dataSource, int batchSize, Class<M> mapperClass, BiConsumer<M, R> runPiece) {
        int committedCount = 0;
        val session = sessionFactory.openSession(ExecutorType.BATCH);
        try {
            final M mapper = session.getMapper(mapperClass);
            log.info("[PS-ORM] BATCH running...");
            for (int n = 1; dataSource.hasNext(); n++) {
                runPiece.accept(mapper, dataSource.next());
                if (n == batchSize || !dataSource.hasNext()) {
                    // 批量提交
                    session.commit();
                    committedCount += n;
                    log.info("[PS-ORM] BATCH COMMITTED: {} rows", committedCount);

                    // 清理缓存，防止溢出
                    session.clearCache();

                    // 重新计数
                    n = 0;
                }
            }
            log.info("[PS-ORM] BATCH COMPLETED.");
        } catch (Throwable e) {
            log.error("[PS-ORM] BATCH FAILED.", e);
            // 没有提交的数据可以回滚
            session.rollback();
        } finally {
            session.close();
        }
        return committedCount;
    }

}
