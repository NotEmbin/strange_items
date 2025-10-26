package embin.strangeitems.client.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.strangeitems.StrangeRegistries;
import embin.strangeitems.tracker.Tracker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public record HiddenTrackers(List<Condition> conditions) {
    public static final Codec<HiddenTrackers> CODEC = RecordCodecBuilder.create(h -> h.group(
            Condition.CODEC.listOf().fieldOf("conditions").forGetter(HiddenTrackers::conditions)
        ).apply(h, HiddenTrackers::new)
    );

    public boolean shouldShowForItem(ItemStack item, Tracker tracker) {
        return this.shouldShowForItem(Registries.ITEM.getEntry(item.getItem()), StrangeRegistries.TRACKER.getEntry(tracker));
    }

    public boolean shouldShowForItem(RegistryEntry<Item> item, RegistryEntry<Tracker> tracker) {
        for (Condition condition : this.conditions) {
            if (condition.affectedItems.contains(item)) {
                if (condition.trackers.contains(tracker)) {
                    return false;
                }
            }
        }
        return true;
    }

    public record Condition(List<RegistryEntry<Item>> affectedItems, List<RegistryEntry<Tracker>> trackers) {
        public static final Codec<Condition> CODEC = RecordCodecBuilder.create(c -> c.group(
                Registries.ITEM.getEntryCodec().listOf().fieldOf("items").forGetter(Condition::affectedItems),
                StrangeRegistries.TRACKER.getEntryCodec().listOf().fieldOf("trackers").forGetter(Condition::trackers)
            ).apply(c, Condition::new)
        );
    }
}
