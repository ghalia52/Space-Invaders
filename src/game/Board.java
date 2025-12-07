package game;

import javax.swing.*;
import patterns.state.*;
import utils.Logger;

/**
 * Gestionnaire principal du jeu
 * Implémente le State Pattern pour gérer les différents états du jeu
 * FIXED: Properly clears and updates UI when state changes
 */
public class Board {
    private JFrame frame;
    private State currentState;
    private JPanel currentPanel;
    
    public Board() {
        Logger.info("Board initialized");
        
        // Démarrer avec l'état Menu
        setCurrentState(new StateMenu(this));
    }
    
    /**
     * Change l'état du jeu avec logging approprié
     * Respecte le cycle de vie des états (onExit -> transition -> onEnter)
     * FIXED: Properly clears all components before updating
     */
    public void setCurrentState(State newState) {
        // Appeler onExit sur l'état actuel
        if (currentState != null) {
            currentState.onExit();
        }
        
        // Logger la transition d'état
        String fromState = (currentState != null) ? currentState.getStateName() : "INIT";
        String toState = newState.getStateName();
        Logger.state("Game: " + fromState + " -> " + toState);
        
        // Changer l'état
        currentState = newState;
        
        // Appeler onEnter sur le nouvel état
        currentState.onEnter();
        
        // Mettre à jour l'interface - CRITICAL FIX
        updateUI();
    }
    
    /**
     * Met à jour l'interface avec le panel de l'état actuel
     * FIXED: Completely clears frame before adding new panel
     */
    private void updateUI() {
        if (frame != null) {
            // CRITICAL FIX: Remove ALL components first
            frame.getContentPane().removeAll();
            
            // Get new panel from current state
            currentPanel = currentState.getMainPanel();
            
            // Add new panel
            frame.getContentPane().add(currentPanel);
            
            // Force complete repaint
            frame.revalidate();
            frame.repaint();
            
            // Request focus for the new panel
            SwingUtilities.invokeLater(() -> {
                currentPanel.requestFocusInWindow();
            });
            
            Logger.info("UI updated for state: " + currentState.getStateName());
        }
    }
    
    /**
     * Obtient le panel principal de l'état actuel
     */
    public JPanel getMainPanel() {
        return currentState.getMainPanel();
    }
    
    /**
     * Obtient l'état actuel
     */
    public State getCurrentState() {
        return currentState;
    }
    
    /**
     * Définit la frame (pour les mises à jour UI)
     * MUST be called from Main after creating the frame
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
        Logger.info("Frame set in Board");
    }
}