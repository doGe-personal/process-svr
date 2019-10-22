package com.mobike.mola.service.repair;

import java.util.function.BiConsumer;

/**
 * @author Lynn
 * @since 2019-05-14
 */
public interface BikeRepairBatchService {

    /**
     * 数据库批量操作
     *
     * @param data 修改数据序列
     * @param mapperClass dao
     * @param batchCount 批量commit数量
     * @param biConsumer DMl操作
     */
    <T, M> void execute(Iterable<T> data, Class<M> mapperClass, int batchCount, BiConsumer<T, M> biConsumer);

    /**
     * 数据库批量操作(默认500条)
     */
    default <T, M> void execute(Iterable<T> data, Class<M> mapperClass, BiConsumer<T, M> biConsumer) {
        execute(data, mapperClass, 500, biConsumer);
    }

}
