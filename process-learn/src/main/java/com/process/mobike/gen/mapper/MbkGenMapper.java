package com.process.mobike.gen.mapper;

import com.process.mobike.gen.domain.MbkGenDef;
import com.process.mobike.gen.domain.MbkGenSeq;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Lynn
 * @since 2019/04/19
 */
public interface MbkGenMapper {

    /**
     * @return 指定租客应用的发号器定义列表
     */
    @Select({
        "select GEN_CODE code, GEN_NAME name, SEQ_FMT seqFmt, SEQ_KEY seqKey",
        ", CNT_KEY cntKey, CNT_LEN cntLen, CNT_PAD cntPad",
        ", CNT_MIN cntMin, CNT_MAX cntMax",
        "from PS_GEN_DEF",
    })
    List<MbkGenDef> genDefs();

    /**
     * 锁定指定发号器实例 (用于更新下一发号值)
     *
     * @param genSeq 发号上下文
     * @return 下一发号值
     */
    @Select({
        "select s.CNT_VAL cntVal",
        " from PS_GEN_SEQ s",
        " where s.GEN_CODE = #{genDef.code}",
        " for update",
    })
    Long getCurrentVal(MbkGenSeq genSeq);

    /**
     * 更新下一个发号值
     *
     * @param genSeq 发号上下文
     * @return 更新记录数(如果为零则需新增发号器)
     */
    @Update({
        "update PS_GEN_SEQ set CNT_KEY = #{cntKey}, CNT_VAL = #{cntValNext}",
        " where GEN_CODE = #{genDef.code} and SEQ_KEY = #{seqKey}",
    })
    boolean updateCnt(MbkGenSeq genSeq);

    /**
     * @param genSeq 发号上下文
     * @return 更新记录数
     */
    @Insert({
        "insert into PS_GEN_SEQ (",
        "  GEN_CODE, SEQ_KEY, CNT_KEY, CNT_VAL",
        ") values (",
        "  #{genDef.code}, #{seqKey}, #{cntKey}, #{cntValNext}",
        ")",
    })
    int addNewSeq(MbkGenSeq genSeq);


    /**
     * 锁定指定发号器定义 (用于新增发号器)
     *
     * @param genCode 发号器代码
     * @return 1
     */
    @Select({
            "select 1 from PS_GEN_DEF d",
            " where d.GEN_CODE = #{gen}",
            "for update"
    })
    Integer lockDef(@Param("gen") String genCode);

}
