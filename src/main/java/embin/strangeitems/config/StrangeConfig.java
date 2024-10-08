package embin.strangeitems.config;

import com.google.gson.stream.JsonReader;
import embin.strangeitems.client.StrangeItemsClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class StrangeConfig {
    private StrangeConfig() {}

    public static boolean in_depth_tracking = true;
    public static boolean check_for_tooltipscroll = true;
    public static boolean invert_tooltipscroll_check_value = false;

    public static void save_json () {
        String json = "{\"in_depth_tracking\":" + in_depth_tracking +
            ", \"check_for_tooltipscroll_mod\":" + check_for_tooltipscroll +
            ", \"invert_tooltipscroll_check_value\":" + invert_tooltipscroll_check_value + "}";
        try {
            FileWriter writer = new FileWriter("config/strange_items.json");
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            StrangeItemsClient.LOGGER.error("Encountered error whilst trying to save config JSON.", e);
        }
    }

    public static void read_json() {
        try {
            FileReader reader = new FileReader("config/strange_items.json");
            JsonReader parser = new JsonReader(reader);
            parser.beginObject();
            while (parser.hasNext()) {
                final String key = parser.nextName();
                if (key.equals("in_depth_tracking")) {
                    in_depth_tracking = parser.nextBoolean();
                }
                if (key.equals("check_for_tooltipscroll_mod")) {
                    check_for_tooltipscroll = parser.nextBoolean();
                }
                if (key.equals("invert_tooltipscroll_check_value")) {
                    invert_tooltipscroll_check_value = parser.nextBoolean();
                }
            }
            parser.close();
        } catch (FileNotFoundException e) {
            try {
                (new File("config")).mkdirs();
                save_json();
            } catch (Exception ex) {}
        } catch (Exception e) {}
    }
}
