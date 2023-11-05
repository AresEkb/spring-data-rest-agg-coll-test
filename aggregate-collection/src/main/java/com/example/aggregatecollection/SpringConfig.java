//package com.example.aggregatecollection;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.fasterxml.jackson.databind.BeanDescription;
//import com.fasterxml.jackson.databind.DeserializationConfig;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.Module;
//import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
//import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.type.CollectionType;
//
//@Configuration
//public class SpringConfig {
//
//    @Bean
//    public Module listDeserializer() {
//        SimpleModule module = new SimpleModule();
//        module.setDeserializerModifier(new BeanDeserializerModifier() {
//
//            @Override
//            public JsonDeserializer<?> modifyCollectionDeserializer(DeserializationConfig config, CollectionType type,
//                    BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
//                return new ListCopyDeserializer((CollectionDeserializer) deserializer);
//            }
//
//        });
//        return module;
//    }
//
//}
