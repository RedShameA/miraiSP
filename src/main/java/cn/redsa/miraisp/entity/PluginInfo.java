package cn.redsa.miraisp.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.lang.reflect.Method;

@Getter
@Setter
public class PluginInfo {
    //instance id desc method disabled
    private Object instance;
    private String id;
    private String desc;
    private Method exec;
    private Boolean enable=true;

    private File path;

    // 重写下面两个方法 Set里就不能存在id相同的两个插件了
    @Override
    public int hashCode(){
        return this.id.hashCode();
    }
    @Override
    public boolean equals(Object otherObject){
        if(this==otherObject) return true;
        if(otherObject==null) return false;
        if(getClass()!=otherObject.getClass())  return false;
        return this.id==((PluginInfo)otherObject).getId();
    }
}
