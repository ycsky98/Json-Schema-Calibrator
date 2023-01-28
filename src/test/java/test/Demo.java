package test;

import org.schema.JSON;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;

public class Demo {

    public static void main(String[] args) {
        String json = "{\"name\": 12, \"age\" : 30}";
        Schema schema = JSON.object()
                .attr("name", JSON.string())
                .attr("age", JSON.number().max(30))
                .attr("school", JSON.string())
                        .require("name", "age");
        System.out.println(Verify.verify(json, schema));
    }
}
