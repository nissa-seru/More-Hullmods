package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;


public class MHMods_StiffMounts extends BaseHullMod {

    private final float RecRed = 0.5f;
    private final float WeaponTurnSpeed = 0.5f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	    stats.getRecoilPerShotMult().modifyMult(id, RecRed);
	    stats.getRecoilDecayMult().modifyMult(id, RecRed);
	    stats.getMaxRecoilMult().modifyMult(id, RecRed);
	    stats.getWeaponTurnRateBonus().modifyMult(id, WeaponTurnSpeed);
	}

	 public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) {
            return "Every weapon";
        }
        if (index == 1) {
            return "best possible auto-fire aim accuracy";
        }
		if (index == 2) {
            return "" + 90 + "%";
        }
        return null;
    }
}

			
			