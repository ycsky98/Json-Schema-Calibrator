package test;

import org.schema.JSON;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;

public class Demo {

    public static void main(String[] args) {
        String json = "{\"name\": [{\"age\": 2}, {\"age\": 3}, {\"age\": 2}], \"age\": 18}";
        Schema schema = JSON.object()
                .attr("name", JSON.array()
                        .item(
                                JSON.object().attr("age", JSON.number().enumVal(2, 3))
                        )
                )
                .attr("age", JSON.number().max(20))
                .require("name", "age");

        System.out.println(Verify.verify(json, schema));


    }
}
