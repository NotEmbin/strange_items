package embin.strangeitems.client.debug;

import embin.strangeitems.StrangeRegistries;
import embin.strangeitems.tracker.Tracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;

import java.util.List;

public class TrackerListWidget extends ElementListWidget<TrackerListWidget.TrackerEntry> {

    public TrackerListWidget(MinecraftClient minecraftClient, TrackerListScreen screen) {
        super(minecraftClient, screen.width, screen.height - 67, 16, 24);
        for (Tracker tracker : StrangeRegistries.TRACKER) {
            this.addEntry(new TrackerEntry(tracker));
        }
    }

    @Override
    public int getRowWidth() {
        return 200;
    }

    public class TrackerEntry extends ElementListWidget.Entry<TrackerListWidget.TrackerEntry> {
        public final Tracker tracker;
        public final TextRenderer textRenderer;

        TrackerEntry(Tracker tracker) {
            this.tracker = tracker;
            this.textRenderer = TrackerListWidget.this.client.textRenderer;
            this.setWidth(150);
            this.setHeight(50);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of();
        }

        @Override
        public List<? extends Element> children() {
            return List.of();
        }

        public void render(DrawContext context, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered) {
            Text name = Text.translatable(tracker.getTranslationKey());
            context.fillGradient(x, y, x + entryWidth, y + entryHeight, 1615855616, -1602211792);
            context.drawCenteredTextWithShadow(this.textRenderer, name, x + (entryWidth / 2), y + (entryHeight / 4) + 1, Colors.WHITE);
            if (hovered) {
                Text id = Text.literal(tracker.toString()).formatted(Formatting.DARK_GRAY);
                List<Text> tooltip = List.of(name, id);
                context.drawTooltip(this.textRenderer, tooltip, mouseX, mouseY);
            }
        }

        public Tracker getTracker() {
            return tracker;
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
            this.render(context, this.getContentY(), this.getX(), this.getContentWidth(), this.getContentHeight(), mouseX, mouseY, hovered);
        }
    }
}
