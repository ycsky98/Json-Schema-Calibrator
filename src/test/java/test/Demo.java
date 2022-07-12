package test;

import org.schema.JSON;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;

public class Demo {

    public static void main(String[] args) {
        String json = "{\"sky\": [0.4, 0.2, 0.3]}";
        Schema schema = JSON.object().attr("name", JSON.array().item(JSON.number().enumVal(0.2, 0.3, 0.4)));

        System.out.println(Verify.verify(json, schema));
    }
}
