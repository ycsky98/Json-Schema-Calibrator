package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.JSON;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;


public class Demo {

    public static void main(String[] args) throws JsonProcessingException {
        String json = "[{\"name\": \"张三\", \"age\" : 21}, {\"name\": \"李四\", \"age\" : 18}]";
        Schema schema = JSON.array().item(
                JSON.object()
                        .attr("name", JSON.string())
                        .attr("age", JSON.number().enumVal(18,20))
        );

        System.out.println(Verify.verify(json, schema));
    }
}
