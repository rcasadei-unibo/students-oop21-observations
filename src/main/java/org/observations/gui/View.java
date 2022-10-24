package org.observations.gui;

import javafx.scene.Node;

/**
 * Interface class for views
 *
 * @param <I> Parameter required to update the view
 */
public interface View<I> {

    /**
     * Update the view with the new input.
     *
     * @param input value tho be inputted.
     */
    void update(I input);

    /**
     * Returns the view root node.
     *
     * @return node of root.
     */
    Node getView();

    /**
     * Show/hide the view.
     *
     * @param value
     */
    void setVisible(Boolean value);
}
