//package com.example.aggregatecollection;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.deser.NullValueProvider;
//import com.fasterxml.jackson.databind.deser.ValueInstantiator;
//import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
//import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
//
//// I have no idea why, but copying of the list sets a parent property (@JsonBackReference) in child items
//// Based on https://stackoverflow.com/questions/50753283/jsonmerge-check-existence-with-list
//public class ListCopyDeserializer extends CollectionDeserializer {
//
//    private static final long serialVersionUID = 1L;
//
//    public ListCopyDeserializer(CollectionDeserializer defaultDeserializer) {
//        super(defaultDeserializer);
//    }
//
//    public ListCopyDeserializer(
//            JavaType collectionType,
//            JsonDeserializer<Object> valueDeser,
//            TypeDeserializer valueTypeDeser,
//            ValueInstantiator valueInstantiator) {
//        super(collectionType, valueDeser, valueTypeDeser, valueInstantiator);
//    }
//
//    protected ListCopyDeserializer(
//            JavaType collectionType,
//            JsonDeserializer<Object> valueDeser,
//            TypeDeserializer valueTypeDeser,
//            ValueInstantiator valueInstantiator,
//            JsonDeserializer<Object> delegateDeser,
//            NullValueProvider nuller,
//            Boolean unwrapSingle) {
//        super(collectionType, valueDeser, valueTypeDeser, valueInstantiator, delegateDeser, nuller, unwrapSingle);
//    }
//
//    @Override
//    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
//        return new ArrayList<>(super.deserialize(jsonParser, context));
//    }
//
//    @Override
//    public Collection<Object> deserialize(JsonParser p, DeserializationContext ctxt, Collection<Object> result)
//            throws IOException {
//        return new ArrayList<>(super.deserialize(p, ctxt, result));
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    protected CollectionDeserializer withResolved(
//            JsonDeserializer<?> dd,
//            JsonDeserializer<?> vd,
//            TypeDeserializer vtd,
//            NullValueProvider nuller,
//            Boolean unwrapSingle) {
//        return new ListCopyDeserializer(_containerType, (JsonDeserializer<Object>) vd, vtd, _valueInstantiator,
//                (JsonDeserializer<Object>) dd, nuller, unwrapSingle);
//    }
//
//}
