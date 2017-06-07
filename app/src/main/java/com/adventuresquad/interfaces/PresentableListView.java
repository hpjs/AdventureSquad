package com.adventuresquad.interfaces;

import java.util.List;

/**
 * Provides an interface for a presenter to present a list of a certain type of item to the view
 * Created by Harrison on 7/06/2017.
 */
public interface PresentableListView<Type> extends Presentable {

    public void addListItem(Type item);

    public void updateList(List<Type> itemList);

}
