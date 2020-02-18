package ru.ncedu.course.service.properties;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.Properties;

public class PropertiesService {

    private static final String PROPERTIES = "application.properties";

    private Properties properties = new Properties();

    public PropertiesService() {
        try(InputStream input = PropertiesService.class.getResourceAsStream(PROPERTIES)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Could not load " + PROPERTIES);
            e.printStackTrace();
        }
    }

    public String getPropertyAsString(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public Long getPropertyAsLong(String propertyName) {
        return Optional.ofNullable(getPropertyAsString(propertyName))
                .map(Long::parseLong)
                .orElse(null);
    }

    public void injectProperties(Object obj) throws IllegalAccessException {
        if(obj == null) {
            return;
        }

        Field[] fields = obj.getClass().getFields();
        for (Field field: fields) {
            Property property = field.getAnnotation(Property.class);
            if(property != null) {
                if(Modifier.isPrivate(field.getModifiers())) {
                    field.setAccessible(true);
                }
                if(field.getType() == String.class) {
                    field.set(obj, getPropertyAsString(property.value()));
                } else if (field.getType() == long.class) {
                    field.set(obj, getPropertyAsLong(property.value()));
                } else {
                        System.out.println(field.getName() + " in "
                                + obj.getClass().getName() + " has unsupported property type "
                                + field.getType());
                }
            }
        }

    }

}
