package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_ExplorationRefit extends BaseHullMod {

    public float FuelUse = 15f;
    public float SensorStrng = 25f;
    public float CR = 20f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getFuelUseMod().modifyPercent(id , -FuelUse);
		stats.getSensorStrength().modifyPercent(id , SensorStrng);
		stats.getMaxCombatReadiness().modifyFlat(id , -CR/100);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return Math.round(FuelUse) + "%";
		if (index == 1) return Math.round(SensorStrng) + "%";
		if (index == 2) return Math.round(CR) + "%";
        return null;
    }
}