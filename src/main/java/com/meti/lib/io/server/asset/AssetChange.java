package com.meti.lib.io.server.asset;

import com.meti.lib.io.server.Change;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class AssetChange<T> extends Change {
    //TODO: convert to array
    private final List<Addition<T>> additions = new ArrayList<>();
    private final List<Deletion<T>> deletions = new ArrayList<>();

    public List<Addition<T>> getAdditions() {
        return additions;
    }

    public List<Deletion<T>> getDeletions() {
        return deletions;
    }

    public void index(T[] oldArray, T[] newArray) {
        int counter = 0;
        for (int o = 0; o < oldArray.length; o++) {
            if (oldArray[o].equals(newArray[counter])) {
                counter++;
            } else {
                int index = -1;
                for (int k = counter; k < newArray.length; k++) {
                    if (oldArray[o].equals(newArray[k])) index = k;
                }
                if (index == -1) {
                    deletions.add(new Deletion<>(oldArray[o], o));
                } else {
                    for (int c = counter; c < index; c++) {
                        additions.add(new Addition<>(newArray[c], c));
                    }

                    counter = index + 1;
                }
            }
        }
    }
}
