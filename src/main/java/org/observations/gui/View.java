package org.observations.gui;

import javafx.scene.Node;

/**
 * Simple view
 *
 * @param <I> Parameter required to be imputed to update the view
 */
public interface View<I> {

    /**
     * Update the view with the new input
     *
     * @param input
     */
    void update(I input);

    /**
     * Returns the view root node
     *
     * @return Node root
     */
    Node getView();

    /**
     * Show/hide the view
     *
     * @param value
     */
    void setVisible(Boolean value);
}
