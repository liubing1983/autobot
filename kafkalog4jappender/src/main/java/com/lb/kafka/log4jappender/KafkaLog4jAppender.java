package com.lb.kafka.log4jappender;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by samsung on 2017/4/12.
 */

/**
 * A log4j appender that produces log messages to Kafka
 */
public class KafkaLog4jAppender extends AppenderSkeleton {

    private static final String BOOTSTRAP_SERVERS_CONFIG = ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
    private static final String COMPRESSION_TYPE_CONFIG = ProducerConfig.COMPRESSION_TYPE_CONFIG;
    private static final String ACKS_CONFIG = ProducerConfig.ACKS_CONFIG;
    private static final String RETRIES_CONFIG = ProducerConfig.RETRIES_CONFIG;
    private static final String KEY_SERIALIZER_CLASS_CONFIG = ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
    private static final String VALUE_SERIALIZER_CLASS_CONFIG = ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
    private static final String SECURITY_PROTOCOL = CommonClientConfigs.SECURITY_PROTOCOL_CONFIG;
    private static final String SSL_TRUSTSTORE_LOCATION = SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG;
    private static final String SSL_TRUSTSTORE_PASSWORD = SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG;
    private static final String SSL_KEYSTORE_TYPE = SslConfigs.SSL_KEYSTORE_TYPE_CONFIG;
    private static final String SSL_KEYSTORE_LOCATION = SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG;
    private static final String SSL_KEYSTORE_PASSWORD = SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG;
    private static final String SASL_KERBEROS_SERVICE_NAME = SaslConfigs.SASL_KERBEROS_SERVICE_NAME;

    private String brokerList = null;
    private String topic = null;
    /**
     * 新增字段， 用于实现kafka topic动态改变
     * 传递过来的数据为格式化样式， 根据当时时间格式化后与topic合并为topic name
     */
    private String timeFormat = null;
    private String compressionType = null;
    private String securityProtocol = null;
    private String sslTruststoreLocation = null;
    private String sslTruststorePassword = null;
    private String sslKeystoreType = null;
    private String sslKeystoreLocation = null;
    private String sslKeystorePassword = null;
    private String saslKerberosServiceName = null;
    private String clientJaasConfPath = null;
    private String kerb5ConfPath = null;

    private int retries = 0;
    private int requiredNumAcks = Integer.MAX_VALUE;
    private boolean syncSend = false;
    private Producer<String, String> producer = null;

    //public Producer<byte[], byte[]> getProducer() {
    // return producer;
    // }

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public int getRequiredNumAcks() {
        return requiredNumAcks;
    }

    public void setRequiredNumAcks(int requiredNumAcks) {
        this.requiredNumAcks = requiredNumAcks;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public String getTopic() {
        //return topic;
        // 判断timeFormat是否为空
        if(StringUtils.isBlank(timeFormat)){
            // 为空直接返回topic
            return topic;
        }else{
            // 不为空将当前时间按照timeFormat格式化后与topic拼接到一起
            return topic+"-"+new SimpleDateFormat(getTimeFormat()).format(System.currentTimeMillis());
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public boolean getSyncSend() {
        return syncSend;
    }

    public void setSyncSend(boolean syncSend) {
        this.syncSend = syncSend;
    }

    public String getSslTruststorePassword() {
        return sslTruststorePassword;
    }

    public String getSslTruststoreLocation() {
        return sslTruststoreLocation;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public void setSslTruststoreLocation(String sslTruststoreLocation) {
        this.sslTruststoreLocation = sslTruststoreLocation;
    }

    public void setSslTruststorePassword(String sslTruststorePassword) {
        this.sslTruststorePassword = sslTruststorePassword;
    }

    public void setSslKeystorePassword(String sslKeystorePassword) {
        this.sslKeystorePassword = sslKeystorePassword;
    }

    public void setSslKeystoreType(String sslKeystoreType) {
        this.sslKeystoreType = sslKeystoreType;
    }

    public void setSslKeystoreLocation(String sslKeystoreLocation) {
        this.sslKeystoreLocation = sslKeystoreLocation;
    }

    public void setSaslKerberosServiceName(String saslKerberosServiceName) {
        this.saslKerberosServiceName = saslKerberosServiceName;
    }

    public void setClientJaasConfPath(String clientJaasConfPath) {
        this.clientJaasConfPath = clientJaasConfPath;
    }

    public void setKerb5ConfPath(String kerb5ConfPath) {
        this.kerb5ConfPath = kerb5ConfPath;
    }

    public String getSslKeystoreLocation() {
        return sslKeystoreLocation;
    }

    public String getSslKeystoreType() {
        return sslKeystoreType;
    }

    public String getSslKeystorePassword() {
        return sslKeystorePassword;
    }

    public String getSaslKerberosServiceName() {
        return saslKerberosServiceName;
    }

    public String getClientJaasConfPath() {
        return clientJaasConfPath;
    }

    public String getKerb5ConfPath() {
        return kerb5ConfPath;
    }

    @Override
    public void activateOptions() {
        // check for config parameter validity
        Properties props = new Properties();
        if (brokerList != null)
            props.put(BOOTSTRAP_SERVERS_CONFIG, brokerList);
        if (props.isEmpty())
            throw new ConfigException("The bootstrap servers property should be specified");
        if (topic == null)
            throw new ConfigException("Topic must be specified by the Kafka log4j appender");

        // 添加kafka配置
        props.put("acks", "all");
        props.put("retries", Integer.valueOf(0));
        props.put("batch.size", Integer.valueOf(16384));
        props.put("linger.ms", Integer.valueOf(1));
        props.put("buffer.memory", Integer.valueOf(33554432));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        if (compressionType != null)
            props.put(COMPRESSION_TYPE_CONFIG, compressionType);
        if (requiredNumAcks != Integer.MAX_VALUE)
            props.put(ACKS_CONFIG, Integer.toString(requiredNumAcks));
        if (retries > 0)
            props.put(RETRIES_CONFIG, retries);
        if (securityProtocol != null) {
            props.put(SECURITY_PROTOCOL, securityProtocol);
        }
        if (securityProtocol != null && securityProtocol.contains("SSL") && sslTruststoreLocation != null &&
                sslTruststorePassword != null) {
            props.put(SSL_TRUSTSTORE_LOCATION, sslTruststoreLocation);
            props.put(SSL_TRUSTSTORE_PASSWORD, sslTruststorePassword);

            if (sslKeystoreType != null && sslKeystoreLocation != null &&
                    sslKeystorePassword != null) {
                props.put(SSL_KEYSTORE_TYPE, sslKeystoreType);
                props.put(SSL_KEYSTORE_LOCATION, sslKeystoreLocation);
                props.put(SSL_KEYSTORE_PASSWORD, sslKeystorePassword);
            }
        }
        if (securityProtocol != null && securityProtocol.contains("SASL") && saslKerberosServiceName != null && clientJaasConfPath != null) {
            props.put(SASL_KERBEROS_SERVICE_NAME, saslKerberosServiceName);
            System.setProperty("java.security.auth.login.config", clientJaasConfPath);
            if (kerb5ConfPath != null) {
                System.setProperty("java.security.krb5.conf", kerb5ConfPath);
            }
        }

        //props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        //props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        this.producer = getKafkaProducer(props);
        LogLog.debug("Kafka producer connected to " + brokerList);
        LogLog.debug("Logging for topic: " + topic);
    }

    protected Producer<String, String> getKafkaProducer(Properties props) {
        //return new KafkaProducer<byte[], byte[]>(props);
        return new KafkaProducer<String, String>(props);
    }

    @Override
    protected void append(LoggingEvent event) {
        String message = subAppend(event);
        LogLog.debug("[" + new Date(event.getTimeStamp()) + "]" + message);

        Future<RecordMetadata> response = producer.send(new ProducerRecord<String, String>(getTopic(), new String(message.getBytes())));

        if (syncSend) {
            try {
                response.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private String subAppend(LoggingEvent event) {
        return (this.layout == null) ? event.getRenderedMessage() : this.layout.format(event);
    }

    @Override
    public void close() {
        if (!this.closed) {
            this.closed = true;
            producer.close();
        }
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
