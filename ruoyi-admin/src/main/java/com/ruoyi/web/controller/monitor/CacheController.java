package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author 棠野·Lysander
 */
@Tag(name = "缓存监控", description = "Redis 缓存监控与管理")
@RestController
@RequestMapping("/monitor/cache")
@RequiredArgsConstructor
public class CacheController {
    private final static List<SysCache> caches = new ArrayList<SysCache>();
    private final RedisTemplate<String, String> redisTemplate;

    {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "密码错误次数"));
    }

    @Operation(summary = "获取缓存监控信息", description = "返回 Redis info、dbSize 及命令调用统计")
    @SuppressWarnings("deprecation")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return AjaxResult.success(result);
    }

    @Operation(summary = "获取缓存名称列表", description = "返回系统可监控的缓存名称集合")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getNames")
    public AjaxResult cache() {
        return AjaxResult.success(caches);
    }

    @Operation(summary = "获取指定缓存名称下的键列表")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getKeys/{cacheName}")
    public AjaxResult getCacheKeys(
            @Parameter(description = "缓存名称（前缀）", required = true) @PathVariable String cacheName) {
        Set<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        return AjaxResult.success(new TreeSet<>(cacheKeys));
    }

    @Operation(summary = "获取指定缓存键的内容")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public AjaxResult getCacheValue(
            @Parameter(description = "缓存名称", required = true) @PathVariable String cacheName,
            @Parameter(description = "缓存键（完整）", required = true) @PathVariable String cacheKey) {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return AjaxResult.success(sysCache);
    }

    @Operation(summary = "按名称清理缓存", description = "删除指定缓存名称（前缀）下的所有键")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheName/{cacheName}")
    public AjaxResult clearCacheName(
            @Parameter(description = "缓存名称（前缀）", required = true) @PathVariable String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        redisTemplate.delete(cacheKeys);
        return AjaxResult.success();
    }

    @Operation(summary = "按键清理缓存")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public AjaxResult clearCacheKey(
            @Parameter(description = "缓存键（完整）", required = true) @PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return AjaxResult.success();
    }

    @Operation(summary = "清理全部缓存", description = "删除 Redis 中所有键，慎用")
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheAll")
    public AjaxResult clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        redisTemplate.delete(cacheKeys);
        return AjaxResult.success();
    }
}
