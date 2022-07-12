package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.JSON;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;


public class Demo {

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"name\": [true, true, true]}";
        Schema schema = JSON.object().attr("name", JSON.array().item(JSON.number()));

        System.out.println(Verify.verify(json, schema));
    }
}
