package com.process.mobike.gen.service;

import com.process.mobike.gen.domain.MbkGenDef;
import com.process.mobike.gen.domain.MbkGenSeq;
import com.process.mobike.gen.mapper.MbkGenMapper;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lynn
 * @since 2019/04/19
 */
@Slf4j
@Service
public class MbkGenManagerImpl implements MbkGenManager {

    private final Map<String, MbkGenDef> genDefMap;

    private final MbkGenMapper genMapper;

    public MbkGenManagerImpl(MbkGenMapper genMapper) {
        this.genMapper = genMapper;
        this.genDefMap = genMapper.genDefs().stream()
                .collect(Collectors.toMap(MbkGenDef::getCode, Function.identity()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String nextSeq(String genCode, Object genCtx) {
        return updateCnt(toGenSeq(genCode, 1, genCtx)).buildSeq();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<String> nextSeqs(String genCode, int genCnt, Object genCtx) {
        return updateCnt(toGenSeq(genCode, genCnt, genCtx))
                .buildSeqs().collect(Collectors.toList());
    }

    private MbkGenSeq toGenSeq(String genCode, int genCnt, Object genCtx) {
        val genDef = genDefMap.get(genCode);
        if (genDef == null) {
            throw new IllegalArgumentException("Invalid generator code: " + genCode);
        }
        return new MbkGenSeq(genDef, genCnt, genCtx);
    }

    private MbkGenSeq updateCnt(MbkGenSeq genSeq) {
        Long oldCntVal = genMapper.getCurrentVal(genSeq);
        genSeq.updateAndCheckCntVal(oldCntVal);
        if (oldCntVal == null) {
            val genCode = genSeq.getGenDef().getCode();
            genMapper.lockDef(genCode);
            genMapper.addNewSeq(genSeq);
        } else {
            genMapper.updateCnt(genSeq);
        }
        return genSeq;
    }

}
