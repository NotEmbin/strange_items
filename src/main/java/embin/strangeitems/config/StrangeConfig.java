package embin.strangeitems.config;

import com.google.gson.stream.JsonReader;
import embin.strangeitems.client.StrangeItemsClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

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
            ", \"invert_tooltipscroll_check_value\":" + invert_tooltipscroll_check_value +
            ", \"key.show_blocks_mined\":\""+ StrangeItemsClient.show_blocks_mined.getBoundKeyTranslationKey() +
            "\", \"key.show_times_dropped\":\""+ StrangeItemsClient.show_times_dropped.getBoundKeyTranslationKey() +
            "\", \"key.show_mobs_killed\":\""+ StrangeItemsClient.show_mobs_killed.getBoundKeyTranslationKey() +
            "\", \"key.show_time_in_dimensions\":\""+ StrangeItemsClient.show_time_in_dimensions.getBoundKeyTranslationKey() + "\"}";
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
                if (key.equals("key.show_blocks_mined")) {
                    StrangeItemsClient.show_blocks_mined.setBoundKey(InputUtil.fromTranslationKey(parser.nextString()));
                }
                if (key.equals("key.show_times_dropped")) {
                    StrangeItemsClient.show_times_dropped.setBoundKey(InputUtil.fromTranslationKey(parser.nextString()));
                }
                if (key.equals("key.show_mobs_killed")) {
                    StrangeItemsClient.show_mobs_killed.setBoundKey(InputUtil.fromTranslationKey(parser.nextString()));
                }
                if (key.equals("key.show_time_in_dimensions")) {
                    StrangeItemsClient.show_time_in_dimensions.setBoundKey(InputUtil.fromTranslationKey(parser.nextString()));
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
