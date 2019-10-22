package com.process.auth.app.sys.api;

import com.process.auth.app.sys.entity.AbstractAppRoleEntity;
import com.process.auth.app.sys.mapper.PsAppRoleMapper;
import com.process.auth.app.sys.service.PsAppRoleService;
import com.process.common.database.domain.PagingResult;
import com.process.common.domain.BaseCodeName;
import com.process.common.util.WebUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Danfeng
 * @since 2018/12/16
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PsAppRoleApi {
    private final PsAppRoleMapper appRoleMapper;
    private final PsAppRoleService appRoleService;

    @GetMapping("/roles")
    public PagingResult<AbstractAppRoleEntity> pages(PsAppRoleQuery query) {
        val total = appRoleMapper.total(query);
        if (total > 0) {
            return PagingResult.of(appRoleMapper.pages(query), total);
        }
        return PagingResult.empty();
    }

    @PutMapping("/role")
    public ResponseEntity role(@RequestBody AbstractAppRoleEntity appRoleEntity) {
        return WebUtil.ok(appRoleService.optEntity(appRoleEntity));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity role(@PathVariable("id") long id) {
        return WebUtil.ok(appRoleMapper.getEntity(id));
    }

    @DeleteMapping("/roles")
    public ResponseEntity roles(@RequestBody Long[] ids) {
        return WebUtil.ok(appRoleService.batchDelete(Arrays.asList(ids)));
    }

    @GetMapping("/roles/suggest")
    public ResponseEntity<List<BaseCodeName>> suggest(@RequestParam(value = "key", required = false) String key) {
        return WebUtil.ok(appRoleMapper.suggest(key));
    }

}
