package org.schema;

import org.schema.json.*;

/**
 * @author yangcong
 *
 * 数据表述器
 */
public class JSON {

    /**
     * string
     *
     * @return
     */
    public static StringSchema string(){
        return new StringSchema();
    }

    /**
     * numberSchema
     *
     * @return
     */
    public static NumberSchema number(){
        return new NumberSchema();
    }

    /**
     * arraySchema
     *
     * @return
     */
    public static ArraySchema array(){
        return new ArraySchema();
    }

    /**
     * Boolean
     *
     * @return
     */
    public static BoolSchema bool(){
        return new BoolSchema();
    }

    /**
     * object
     *
     * @return
     */
    public static ObjectSchema object(){
        return new ObjectSchema();
    }
}
