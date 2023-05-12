package com.ambisiss.api.config;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-12 20:36:47
 */
@Configuration
public class IdGeneratorConfig {

    @PostConstruct
    public void init() {
        // 创建 IdGeneratorOptions 对象
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        // WorkerIdBitLength 默认值6，支持的 WorkerId 最大值为2^6-1，若 WorkerId 超过64，可设置更大的 WorkerIdBitLength
         options.WorkerIdBitLength = 16;
        // ...... 其它参数设置参考 IdGeneratorOptions 定义，一般来说，只要再设置 WorkerIdBitLength （决定 WorkerId 的最大值）。
        // 保存参数（必须的操作，否则以上设置都不能生效）：
        YitIdHelper.setIdGenerator(options);
    }

}
