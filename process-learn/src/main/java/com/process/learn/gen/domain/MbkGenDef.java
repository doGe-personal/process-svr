package com.process.learn.gen.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 发号配置
 *
 * @author Lynn
 * @since 2019/04/19
 */
@Data
public class MbkGenDef {

    private static final String NO_KEY = "-";

    /**
     * 发号器代码
     */
    private String code;
    /**
     * 发号器名称
     */
    private String name;

    /**
     * 流水号模版(printf-style)、含三个%s、分别代表发号键、计数键和计数值
     */
    private String seqFmt;
    /**
     * 发号键模版(SpEL)、变化时新建实例
     */
    private String seqKey;

    public boolean noSeqKey() {
        return NO_KEY.equals(seqKey) || StringUtils.isEmpty(seqKey);
    }

    /**
     * 计数键模版(SpEL)、变化时重新计数
     */
    private String cntKey;

    public boolean noCntKey() {
        return NO_KEY.equals(cntKey) || StringUtils.isEmpty(cntKey);
    }

    private final static ExpressionParser parser = new SpelExpressionParser();

    /**
     * @param ctx 上下文数据
     * @return 发号键
     */
    public String genSeqKey(Object ctx) {
        return noSeqKey() ? seqKey : parser.parseExpression(seqKey)
                .getValue(ctx, String.class);
    }

    /**
     * @param ctx 上下文数据
     * @return 计数键
     */
    public String genCntKey(Object ctx) {
        return noCntKey() ? cntKey : parser.parseExpression(cntKey)
                .getValue(ctx, String.class);
    }

    /**
     * 发号值长度
     */
    private int cntLen;
    /**
     * 发号值补零
     */
    private char cntPad = '0';
    /**
     * 最小发号值
     */
    private long cntMin;
    /**
     * 最大发号值
     */
    private long cntMax;

    /**
     * @param seqCnt 计数值
     * @return 格式后的计数值
     */
    public String seq(long seqCnt) {
        return StringUtils.leftPad(String.valueOf(seqCnt), cntLen, cntPad);
    }
}
