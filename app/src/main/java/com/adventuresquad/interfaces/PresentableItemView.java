package com.adventuresquad.interfaces;

/**
 * Generic interface for displaying a model item on a view.
 * Created by Harrison on 13/06/2017.
 */
public interface PresentableItemView<Type> {
    void displayItem(Type item);
}
