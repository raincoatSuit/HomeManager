package com.github.gleans.utils;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

/**
 * 	自定义id生成
 */
@NoArgsConstructor
public class CustomGenerationId implements Configurable,IdentifierGenerator {

    /**
     * id前缀
     */
    private String idPrefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // TODO Auto-generated method stub
        return getId();
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        // TODO Auto-generated method stub
        this.idPrefix = params.getProperty("idPrefix"); //	实体类中@Parameter注解，根据键值获取value
    }

    /**
     * 	该方法需要是线程安全的
     */
    public String getId() {
        synchronized (CustomGenerationId.class) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            return idPrefix + "-" + uuid;
        }
    }
}
