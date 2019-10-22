package com.process.mobike.gen.service;

import java.util.List;

/**
 * 发号器管理服务
 *
 * @author Lynn
 * @since 2019/04/19
 */
public interface MbkGenManager {

    /**
     * 发一个号
     *
     * @param genCode 发号器代码
     * @param genCtx 发号器上下文
     * @return 下一个号
     */
    String nextSeq(String genCode, Object genCtx);

    /**
     * 发一批号
     *
     * @param genCode 发号器代码
     * @param genCnt 一批数量
     * @param genCtx 发号器上下文
     * @return 下一批号
     */
    List<String> nextSeqs(String genCode, int genCnt, Object genCtx);

}
