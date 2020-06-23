package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;

public class MHMods_ExplorationRefit extends BaseLogisticsHullMod {

    public float FuelUse = 0.8f;
    public float SensorStrng = 25f;
    public float CR = 20f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getFuelUseMod().modifyMult(id , FuelUse);
		stats.getSensorStrength().modifyPercent(id , SensorStrng);
		stats.getMaxCombatReadiness().modifyFlat(id , -CR/100);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return Math.round((1f - FuelUse) * 100) + "%";
		if (index == 1) return Math.round(SensorStrng) + "%";
		if (index == 2) return Math.round(CR) + "%";
        return null;
    }
}