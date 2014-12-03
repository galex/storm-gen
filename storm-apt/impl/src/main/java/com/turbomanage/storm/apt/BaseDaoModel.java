package com.turbomanage.storm.apt;

import javax.lang.model.type.TypeMirror;

/**
 * Class representing the Base Dao Class
 * <p/>
 *
 * @Author Alexander Gherschon
 */
public class BaseDaoModel extends ClassModel {

    /**
     * Base Dao Class is received from the Annotation as qualified Name class
     *
     * @param qualifiedName
     */
    public BaseDaoModel(String qualifiedName) {

        setClassName(qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1));
        setPackageName(qualifiedName.substring(0, qualifiedName.lastIndexOf(".")));
    }
}
