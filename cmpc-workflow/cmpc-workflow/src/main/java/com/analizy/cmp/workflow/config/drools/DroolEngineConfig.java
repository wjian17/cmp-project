package com.analizy.cmp.workflow.config.drools;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.drools.core.marshalling.impl.ProtobufMessages;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.builder.conf.EvaluatorOption;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


@Slf4j
@Configuration
public class DroolEngineConfig implements InitializingBean {

    public static KieServices kieServices = KieServices.Factory.get();

    public static KieContainer kieContainer;

    private static final String RULES_PATH = "rules/";

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;


    public static KieFileSystem loadKieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        final Resource[] resources = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
        for (Resource file : resources) {
            Resource[] resources1 = resourcePatternResolver.getResources("classpath:rules/*.*");
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }
        return kieFileSystem;
    }


    public static KieFileSystem loadKieFileSystem(List<String> rules) throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (String rule : rules) {
            kieFileSystem.write("src/main/resources/rule/" + System.currentTimeMillis() + ".drl", rule);
        }
        return kieFileSystem;
    }

    public void loadKieContainer() throws IOException {


        //修改默认Java Dialect编译器
        Properties props = new Properties();
        //set default java compiler

        props.setProperty("drools.dialect.java.compiler", "JANINO");
        // 声明新的KnowledgeBuilder对象
        KieBaseConfiguration kieBaseConfiguration = kieServices.newKieBaseConfiguration(props);


        final KieRepository kieRepository = kieServices.getRepository();
        KieBuilder kieBuilder = kieServices.newKieBuilder(loadKieFileSystem());
        kieBuilder.buildAll();



        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            log.error("load drools file error:results={}", results.getMessages());
            throw new IllegalStateException("## error ##");
        }
        kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    public static void execute(final Object object, final String endAgenda) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(object);
        AgendaFilter agendaFilter = new RuleNameEndsWithAgendaFilter(endAgenda);
        kieSession.fireAllRules(agendaFilter);
        kieSession.setGlobal("drools.dialect.java.compiler", "JANINO");
        kieSession.dispose();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        loadKieContainer();
    }
}