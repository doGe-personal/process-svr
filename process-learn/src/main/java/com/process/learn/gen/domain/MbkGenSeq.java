package com.process.learn.gen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * 一次发号的上下文
 *
 * @author Lynn
 * @since 2019/04/19
 */
@Slf4j
@EqualsAndHashCode(of = {"genDef", "seqKey", "seqCnt"})
public class MbkGenSeq {

    /**
     * 定义
     */
    @Getter
    private final MbkGenDef genDef;

    /**
     * 发放数量
     */
    private final int seqCnt;

    /**
     * 发号键
     */
    private String seqKey;

    /**
     * 计数键
     */
    private String cntKey;

    /**
     * @param genDef 定义
     * @param seqCnt 发放数量
     * @param genCtx 上下文数据（可为null）
     */
    public MbkGenSeq(MbkGenDef genDef, int seqCnt, Object genCtx) {
        this.genDef = genDef;
        this.seqCnt = Math.max(seqCnt, 1);
        buildKeys(genDef, genCtx);
        log.trace("[EDP-GEN] REQ is {}, {}, {}", genDef.getCode(), seqCnt, genCtx);
    }

    private void buildKeys(MbkGenDef genDef, Object genCtx) {
        val ctx = new TimeGenContext(genCtx);
        this.seqKey = genDef.genSeqKey(ctx);
        this.cntKey = genDef.genCntKey(ctx);
    }

    @Override
    public String toString() {
        return genDef.getCode() + "/" + seqKey + "/" + cntKey;
    }

    /**
     * 计数值
     */
    private long cntVal;

    /**
     * @throws RuntimeException 当`cntVal+cntCnt > cntMax`时抛出
     */
    public void updateAndCheckCntVal(Long oldCntVal) {
        cntVal = oldCntVal != null ? oldCntVal : genDef.getCntMin();
        // check overflow
        final long cntMax = genDef.getCntMax();
        final long cntValNext = getCntValNext();
        if (cntVal > cntMax || cntValNext > cntMax + 1) {
            throw new RuntimeException(String.format(
                    "generator instance (%s) OVERFLOW: %d > %d",
                    this, this.getCntValNext(), this.getGenDef().getCntMax()));
        }
        if (cntValNext > cntMax) {
            log.warn("NEXT counter value of generator ({}) will OVERFLOW: {} > {}", this, cntValNext, cntMax);
        }
        log.trace("generator new value: {}", cntVal);
    }

    /**
     * @return 计数值的下一跳
     */
    public long getCntValNext() {
        return cntVal + seqCnt;
    }

    /**
     * 生成一个流水号
     *
     * @return 流水号
     */
    public String buildSeq() {
        return buildSeq(cntVal);
    }

    /**
     * 生成一批流水号
     *
     * @return 流水号
     */
    public Stream<String> buildSeqs() {
        return LongStream.range(cntVal, getCntValNext())
                .mapToObj(this::buildSeq);
    }

    /**
     * 生成一个流水号
     *
     * @param cntVal 计数值
     * @return 流水号
     */
    private String buildSeq(long cntVal) {
        return String.format(genDef.getSeqFmt(),
                genDef.noSeqKey() ? "" : seqKey,
                genDef.noCntKey() ? "" : cntKey,
                genDef.seq(cntVal));
    }


    @Setter
    @Getter
    @RequiredArgsConstructor
    class TimeGenContext {

        /**
         * @param ta 时间
         * @param tp 格式
         * @return 格式化后的时间
         */
        public String t(TemporalAccessor ta, String tp) {
            return DateTimeFormatter.ofPattern(tp).format(ta);
        }

        /**
         * 当前时间
         */
        private final LocalDateTime now = LocalDateTime.now();

        /**
         * @param tp 格式
         * @return 格式化后的当前时间
         */
        public String t(String tp) {
            return t(now, tp);
        }

        /**
         * 业务上下文数据
         */
        private final Object ctx;

    }

}
