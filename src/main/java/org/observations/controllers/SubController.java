package org.observations.controllers;

import javafx.scene.Node;

/**
 * SubControlled which create and control a View for a third party
 *
 * @param <M> Model data type required bi controller
 * @param <I> Type of input required by View
 * @param <O> Type of output required by Model
 */
public interface SubController<M, I, O> {

    /**
     * Updates the view with new data
     *
     * @param input
     */
    void updateView(I input);

    /**
     * Returns the controlled view root's node
     *
     * @return Node
     */
    Node getView();

    /**
     * Set the view controlled on/off
     *
     * @param value
     */
    void setViewVisible(Boolean value);

    /**
     * Show/hide buttons to edit data
     *
     * @param value
     */
    void switchOnOffEditButtons(Boolean value);

    /**
     * Ask data from the model
     *
     * @param text Entry to be satisfied
     */
    void getData(M text);

    /**
     * Handles new data to the model
     *
     * @param output
     */
    void updateModel(O output);
}
