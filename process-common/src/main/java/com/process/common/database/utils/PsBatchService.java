package com.process.common.database.utils;

import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * @author neo.pan
 * @since 2018/05/18
 */
public interface PsBatchService {

    /**
     * 运行批量数据任务
     *
     * @param dataSource  数据源
     * @param batchSize   批行数
     * @param mapperClass Mapper类
     * @param runPiece    行处理
     * @param <M>         Mapper类类
     * @param <R>         行类型
     * @return 已提交行数
     */
    <M, R> int runBatch(Iterator<R> dataSource, int batchSize, Class<M> mapperClass, BiConsumer<M, R> runPiece);

}
