package com.process.auth.app.sys.api;

import com.process.auth.app.sys.entity.AbstractAppMenuEntity;
import com.process.auth.app.sys.mapper.PsAppUserMapper;
import com.process.auth.app.sys.service.PsAppMenuService;
import com.process.auth.core.security.domain.AbstractAppUser;
import com.process.common.util.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PsAppMenuApi {

    private final PsAppMenuService menuService;
    private final PsAppUserMapper psAppUserMapper;

    @GetMapping("/menus")
    public ResponseEntity menus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        log.debug("principal-me:{}", authentication.getPrincipal());
        AbstractAppUser psAppUser = psAppUserMapper.getEntityByName(username);
        return WebUtil.okBuilder().body(menuService.menusOf(psAppUser.getId()));
    }

    @GetMapping("/menus/all")
    public ResponseEntity<List<AbstractAppMenuEntity>> findMenus(@RequestParam(value = "roleId", required = false) Long roleId) {
        if (roleId != null) {
            return WebUtil.okBuilder().body(menuService.menusByRoleId(roleId));
        }
        return WebUtil.okBuilder().body(menuService.findList());
    }

}

