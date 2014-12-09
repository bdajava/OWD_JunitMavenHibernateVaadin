package org.jpwh.shared;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.HibernateProxyHelper;
import org.hibernate.proxy.LazyInitializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Field;

/**
 * Read/write an XML element that references an entity by type and id.
 * <p>
 * Knows how to get the identifier and type of a Hibernate proxy and turn it
 * into a reference. Does not know how to turn a reference back into a proxy,
 * so always creates "hollow" entity instances with only the identifier value.
 * </p>
 */
public class ReferenceXmlAdapter extends XmlAdapter<ReferenceXmlAdapter.Reference, Object> {

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Reference {

        @XmlAttribute
        public Class type;

        @XmlAttribute
        public Long id;

        public Reference() {
        }

        public Reference(Class type, Long id) {
            this.type = type;
            this.id = id;
        }
    }

    @Override
    public Object unmarshal(Reference entityReference) throws Exception {
        Object entityInstance = entityReference.type.newInstance();
        setId(entityReference.type, entityInstance, entityReference.id);
        // What is returned here is not the real thing, it's "hollow".
        // It should be a Hibernate proxy but isn't. Maybe you want to wrap
        // it in your own proxy class or set a flag on it, so you know that
        // it has to be em.merge()'d before it's usable.
        return entityInstance;
    }

    @Override
    public Reference marshal(Object entityInstance) throws Exception {
        Class type = getType(entityInstance);
        return new Reference(type, getId(type, entityInstance));
    }

    // This is all Hibernate proprietary, and we assume that JPA entities are
    // mapped with field access ('id' field check). You can easily rewrite this
    // to use getter/setter methods for the identifier.

    protected Class getType(Object o) throws Exception {
        return HibernateProxyHelper.getClassWithoutInitializingProxy(o);
    }

    protected Long getId(Class type, Object entityInstance) throws Exception {
        if (entityInstance instanceof HibernateProxy) {
            LazyInitializer lazyInitializer =
               ((HibernateProxy) entityInstance).getHibernateLazyInitializer();
            return (Long) lazyInitializer.getIdentifier();
        }
        return (Long) getIdField(type).get(entityInstance);
    }

    protected void setId(Class type, Object entityInstance, Long id) throws Exception {
        getIdField(type).set(entityInstance, id);
    }

    protected Field getIdField(Class type) throws Exception {
        Field idField = type.getDeclaredField("id");
        if (idField == null || idField.getType() != Long.class)
            throw new IllegalArgumentException("Missing 'id' field of type Long on: " + type);
        return idField;
    }

}
