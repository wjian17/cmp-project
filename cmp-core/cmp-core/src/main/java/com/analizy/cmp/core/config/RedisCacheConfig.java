package com.analizy.cmp.core.config;

import com.analizy.cmp.core.handler.IgnoreExceptionCacheErrorHandler;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRedisRepositories
/**
 * @author wangjian
 * @date 2020/5/28 0028 16:06
 */
 public class RedisCacheConfig extends CachingConfigurerSupport {


    /**
     * 添加自定义缓存异常处理
     * 当缓存读写异常时,忽略异常
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreExceptionCacheErrorHandler();
    }

//    @Override
//    public CacheResolver cacheResolver() {
//        // 通过Guava实现的自定义堆内存缓存管理器
////        CacheManager guavaCacheManager = new GuavaCacheManager();
//        CacheManager redisCacheManager = cacheManager();
//        List<CacheManager> list = new ArrayList<>();
//        // 优先读取堆内存缓存
////        list.add(guavaCacheManager);
//        // 堆内存缓存读取不到该key时再读取redis缓存
//        list.add(redisCacheManager);
//        return new CustomCacheResolver(list);
//    }

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    //    @Bean(name = "myMethodParamKeyGenerator")
//    public KeyGenerator myMethodParamKeyGenerator() {
//        return (target, method, params) -> {
//            //获得注解
//            MyCacheable myCacheable = AnnotationUtils.findAnnotation(method, MyCacheable.class);
//            if (myCacheable != null) {
//                String myKey = myCacheable.myKey();
//                if (myKey != null && StringUtils.hasText(myKey)) {
//                    //获取方法的参数集合
//                    Parameter[] parameters = method.getParameters();
//                    StandardEvaluationContext context = new StandardEvaluationContext();
//
//                    //遍历参数，以参数名和参数对应的值为组合，放入StandardEvaluationContext中
//                    // 注意：若没有java8的编译参数-parameters，参数名都回事arg0,arg1...  若有参数就是具体的参数名了
//                    for (int i = 0; i < parameters.length; i++) {
//                        context.setVariable(parameters[i].getName(), params[i]);
//                    }
//                    ExpressionParser parser = new SpelExpressionParser();
//                    //根据newKey来解析获得对应值
//                    Expression expression = parser.parseExpression(myKey);
//                    return expression.getValue(context, String.class);
//
//                }
//            }
//            return params[0].toString();
//        };
//    }

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    @Override
    public CacheManager cacheManager() {
        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存的默认过期时间，也是使用Duration设置
        config = config.entryTtl(Duration.ofMinutes(5))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();
        // 设置一个初始化的缓存空间set集合
        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("mybatis", config);

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                // 一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .initialCacheNames(configMap.keySet())
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }


//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//        // 设置缓存的默认过期时间，也是使用Duration设置
//        config = config.entryTtl(Duration.ofMinutes(1))
//                // 设置 key为string序列化
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // 设置value为json序列化
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
//                // 不缓存空值
//                .disableCachingNullValues();
//
//        // 设置一个初始化的缓存空间set集合
//        // 对每个缓存空间应用不同的配置
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put("company_goods_info", config);
//
//        // 使用自定义的缓存配置初始化一个cacheManager
//        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
//                // 一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
//                .initialCacheNames(configMap.keySet())
//                .withInitialCacheConfigurations(configMap)
//                .build();
//        return cacheManager;
//    }

    /**
     * json序列化
     *
     * @return
     */
    @Bean
    public RedisSerializer<Object> jackson2JsonRedisSerializer() {
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        //解决缓存转换异常的问题
        ObjectMapper objectMapper = new ObjectMapper();
        //下面两行解决Java8新日期API序列化问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        //设置所有访问权限以及所有的实际类型都可序列化和反序列化
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

}
