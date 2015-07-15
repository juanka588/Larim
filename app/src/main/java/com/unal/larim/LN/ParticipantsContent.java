package com.unal.larim.LN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ParticipantsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Participant> ITEMS = new ArrayList<Participant>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Participant> ITEM_MAP = new HashMap<String, Participant>();

    static {
        // Add 3 sample items.
        addItem(new Participant("1", "Juan Camilo", "Rodriguez Duran", "jcrodriguezd@unal.edu.co", "Universidad Nacional de Colombia"));
        addItem(new Participant("2", "Andres", "Granados Cruz", "jcrodriguezd@unal.edu.co", "Universidad Nacional de Colombia"));
        addItem(new Participant("3", "Mario Armando", "Higuera", "jcrodriguezd@unal.edu.co", "Universidad Nacional de Colombia"));
    }

    private static void addItem(Participant item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */

}
