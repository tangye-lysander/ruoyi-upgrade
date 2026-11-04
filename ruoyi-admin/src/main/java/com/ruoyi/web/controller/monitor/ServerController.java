package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.domain.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author 棠野·Lysander
 */
@Tag(name = "服务器监控", description = "CPU、内存、JVM、磁盘等服务器信息监控")
@RestController
@RequestMapping("/monitor/server")
public class ServerController {
    @Operation(summary = "获取服务器信息")
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
