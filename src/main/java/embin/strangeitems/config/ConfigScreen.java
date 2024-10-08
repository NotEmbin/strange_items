package embin.strangeitems.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {
    private ConfigScreen() {}

    public static ConfigBuilder configBuilder(final Screen parent) {
        final ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.translatable("screens.strangeitems.config"));


        final ConfigEntryBuilder entry_builder = builder.entryBuilder();
        final ConfigCategory strangeitems = builder.getOrCreateCategory(Text.translatable("screens.strangeitems.config"));

        strangeitems.addEntry(entry_builder.startBooleanToggle(Text.translatable("setting.strangeitems.in_depth_tracking"), StrangeConfig.in_depth_tracking)
            .setDefaultValue(true)
            .setSaveConsumer(newvalue -> StrangeConfig.in_depth_tracking = newvalue)
            .setTooltip(
                Text.translatable("setting.strangeitems.in_depth_tracking.desc"),
                Text.translatable("setting.strangeitems.in_depth_tracking.desc2")
            )
            .build()
        );

        strangeitems.addEntry(entry_builder.startBooleanToggle(Text.translatable("setting.strangeitems.check_for_tooltipscroll_mod"), StrangeConfig.check_for_tooltipscroll)
            .setDefaultValue(true)
            .setSaveConsumer(newvalue -> StrangeConfig.check_for_tooltipscroll = newvalue)
            .setTooltip(
                Text.translatable("setting.strangeitems.check_for_tooltipscroll_mod.desc"),
                Text.translatable("setting.strangeitems.check_for_tooltipscroll_mod.desc2")
            )
            .build()
        );

        strangeitems.addEntry(entry_builder.startBooleanToggle(Text.translatable("setting.strangeitems.invert_tooltipscroll_check_value"), StrangeConfig.invert_tooltipscroll_check_value)
            .setDefaultValue(false)
            .setSaveConsumer(newvalue -> StrangeConfig.invert_tooltipscroll_check_value = newvalue)
            .setTooltip(
                Text.translatable("setting.strangeitems.invert_tooltipscroll_check_value.desc"),
                Text.translatable("setting.strangeitems.invert_tooltipscroll_check_value.desc2"),
                Text.translatable("setting.strangeitems.invert_tooltipscroll_check_value.desc3")
            )
            .build()
        );

        builder.transparentBackground();
        builder.setSavingRunnable(StrangeConfig::save_json);
        return builder;
    }
}
