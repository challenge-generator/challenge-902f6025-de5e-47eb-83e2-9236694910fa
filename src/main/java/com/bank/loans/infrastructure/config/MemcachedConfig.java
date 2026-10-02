package com.bank.loans.infrastructure.config;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@Profile("!test")
public class MemcachedConfig {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedConfig.class);

    @Value("${cache.memcached.host:localhost}")
    private String memcachedHost;

    @Value("${cache.memcached.port:11211}")
    private int memcachedPort;

    @Value("${cache.memcached.expiration:3600}")
    private int defaultExpiration;

    @Value("${cache.memcached.op-timeout:3000}")
    private int operationTimeout;

    @Value("${cache.memcached.connection-timeout:3000}")
    private int connectionTimeout;

    @Bean(destroyMethod = "shutdown")
    public MemcachedClient memcachedClient() {
        try {
            logger.info("Conectando a Memcached en {}:{}", memcachedHost, memcachedPort);
            MemcachedClient client = new MemcachedClient(
                new InetSocketAddress(memcachedHost, memcachedPort)
            );
            logger.info("Cliente Memcached conectado exitosamente");
            return client;
        } catch (IOException e) {
            logger.error("Error al conectar con Memcached: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo inicializar el cliente Memcached", e);
        }
    }

    @Bean
    public MemcachedProperties memcachedProperties() {
        MemcachedProperties props = new MemcachedProperties();
        props.setHost(memcachedHost);
        props.setPort(memcachedPort);
        props.setExpiration(defaultExpiration);
        props.setOpTimeout(operationTimeout);
        props.setConnectionTimeout(connectionTimeout);
        return props;
    }

    public int getDefaultExpiration() {
        return defaultExpiration;
    }

    public static class MemcachedProperties {
        private String host;
        private int port;
        private int expiration;
        private int opTimeout;
        private int connectionTimeout;

        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
        public int getExpiration() { return expiration; }
        public void setExpiration(int expiration) { this.expiration = expiration; }
        public int getOpTimeout() { return opTimeout; }
        public void setOpTimeout(int opTimeout) { this.opTimeout = opTimeout; }
        public int getConnectionTimeout() { return connectionTimeout; }
        public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
    }
}