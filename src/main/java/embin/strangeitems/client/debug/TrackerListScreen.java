package embin.strangeitems.client.debug;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public class TrackerListScreen extends Screen {
    private TrackerListWidget trackerList;
    private Screen parent;

    public TrackerListScreen(Screen parent) {
        super(Text.literal("Debug: Registered Trackers"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.trackerList = new TrackerListWidget(this.client, this);
        this.addDrawableChild(this.trackerList);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        //super.renderBackground(context, mouseX, mouseY, deltaTicks);
        this.renderPanoramaBackground(context, deltaTicks);
    }
}
