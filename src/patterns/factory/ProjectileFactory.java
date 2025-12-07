package patterns.factory;

import entities.*;
import utils.Constants;
import utils.Logger;

/**
 * FACTORY PATTERN
 * Crée différents types de projectiles selon l'ID fourni
 */
public class ProjectileFactory {

    public ProjectileFactory() {
        Logger.info("ProjectileFactory initialized");
    }

    /**
     * Crée un projectile selon le type demandé
     * @param type ID du type de projectile
     * @param xCoord Position X
     * @param yCoord Position Y
     * @return Le projectile créé ou null si type invalide
     */
    public Projectile makeProjectile(int type, int xCoord, int yCoord) {
        Projectile projectile = null;
        
        switch (type) {
            case Constants.NORMAL_PROJECTILE_ID:
                projectile = new NormalShot(xCoord, yCoord);
                Logger.info("FACTORY: NormalShot created at (" + xCoord + ", " + yCoord + ")");
                break;
                
            case Constants.POWER_PROJECTILE_ID:
                projectile = new PowerShot(xCoord, yCoord);
                Logger.info("FACTORY: PowerShot created at (" + xCoord + ", " + yCoord + ")");
                break;
                
            case Constants.ALIEN_BOMB_ID:
                projectile = new AlienBomb(xCoord, yCoord);
                Logger.info("FACTORY: AlienBomb created at (" + xCoord + ", " + yCoord + ")");
                break;
                
            default:
                Logger.warning("FACTORY: Unknown projectile type: " + type);
                break;
        }
        
        return projectile;
    }
    
    /**
     * Crée un projectile avec des paramètres personnalisés
     */
    public Projectile makeCustomProjectile(String typeName, int xCoord, int yCoord) {
        Projectile projectile = null;
        
        switch (typeName.toLowerCase()) {
            case "normal":
                projectile = makeProjectile(Constants.NORMAL_PROJECTILE_ID, xCoord, yCoord);
                break;
            case "power":
                projectile = makeProjectile(Constants.POWER_PROJECTILE_ID, xCoord, yCoord);
                break;
            case "bomb":
                projectile = makeProjectile(Constants.ALIEN_BOMB_ID, xCoord, yCoord);
                break;
            default:
                Logger.warning("FACTORY: Unknown projectile name: " + typeName);
                break;
        }
        
        return projectile;
    }
}