package com.mobike.mola.service.repair;

import com.mobike.mola.config.RepairMysqlDAOConfig;
import java.util.Iterator;
import java.util.function.BiConsumer;
import javax.annotation.Resource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

/**
 * @author Lynn
 * @since 2019-05-14
 */
@Service
public class BikeRepairBatchServiceImpl implements BikeRepairBatchService {

    @Resource(name = RepairMysqlDAOConfig.SQL_SESSION_FACTORY_NAME)
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public <T, M> void execute(Iterable<T> data, Class<M> mapperClass, int batchCount, BiConsumer<T,M> biConsumer) {
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            M mapper = batchSqlSession.getMapper(mapperClass);
            Iterator<T> dataIterator = data.iterator();
            int index = 0;
            while (dataIterator.hasNext()) {
                T next = dataIterator.next();
                biConsumer.accept(next, mapper);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                    index = 0;
                }
                index++;
            }
            batchSqlSession.commit();
        } catch (Exception e) {
            if (batchSqlSession != null) {
                batchSqlSession.rollback();
            }
            throw e;
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
    }

}
