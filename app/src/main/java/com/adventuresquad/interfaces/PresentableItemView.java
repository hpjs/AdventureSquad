package com.adventuresquad.interfaces;

/**
 * Generic interface for displaying a model item on a view.
 * Created by Harrison on 13/06/2017.
 */
public interface PresentableItemView<Type> extends Presentable {
    void displayItem(Type item);
}
