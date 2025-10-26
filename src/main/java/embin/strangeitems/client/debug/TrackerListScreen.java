package embin.strangeitems.client.debug;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TrackerListScreen extends Screen {
    private TrackerListWidget trackerList;
    private final Screen parent;

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
        this.renderPanoramaBackground(context, deltaTicks);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
