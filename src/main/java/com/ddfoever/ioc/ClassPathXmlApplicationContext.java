package com.ddfoever.ioc;

import com.sun.xml.internal.bind.v2.runtime.output.NamespaceContextImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClassPathXmlApplicationContext implements  ApplicatonContext {

    private Map<String,Object> iocMap;

    public ClassPathXmlApplicationContext(String resourceName){
        //初始化iocMap
        iocMap = new HashMap<String, Object>();
        //读取spring xml 文件
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read("./src/main/resources/"+resourceName);
            Element element = document.getRootElement();
            Iterator<Element> iterator = element.elementIterator();
            while (iterator.hasNext()){
                Element element1 = iterator.next();
                String id = element1.attributeValue("id");
                String className = element1.attributeValue("class");
                //通过反射机制
                Class clazz = Class.forName(className);
                //获取无参构造
               Constructor constructor = clazz.getConstructor();
               //获取对象
               Object object = constructor.newInstance();
               //给目标对象赋值
                Iterator<Element> beanIter = element1.elementIterator();
                while (beanIter.hasNext()){
                   Element property = beanIter.next();
                   String name = property.attributeValue("name");
                   String valueStr = property.attributeValue("value");
                   String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length());
                    Field field = clazz.getDeclaredField(name);
                   Method method = clazz.getDeclaredMethod(methodName,field.getType());
                   //参数类型转化
                    Object value = null;
                    if(field.getType().getName().equals("long")){
                        value = Long.parseLong(valueStr);
                    }
                    if(field.getType().getName().equals("java.lang.String")){
                        value = valueStr;
                    }
                   method.invoke(object,value);
                    System.out.println(method);
                }
               iocMap.put(id,object);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){

        }catch (InstantiationException E){

        }catch (InvocationTargetException e){

        }catch (NoSuchFieldException e){}

    }

    public Object getBean(String id) {
        return iocMap.get(id);
    }
}
