package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_WeaponInhibitor extends BaseHullMod {

	private final float rof = 0.85f;
	private final float flux_usage = 0.85f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getEnergyRoFMult().modifyMult(id, rof);
		stats.getBallisticRoFMult().modifyMult(id, rof);
		stats.getBeamWeaponDamageMult().modifyMult(id, rof);
		stats.getEnergyWeaponFluxCostMod().modifyMult(id, flux_usage);
		stats.getBallisticWeaponFluxCostMod().modifyMult(id, flux_usage);
	}
	
	 public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return Math.round((1f - rof) * 100f) + "%";
        if (index == 1) return Math.round((1f - rof) * 100f) + "%";
		if (index == 2) return Math.round((1f - flux_usage) * 100f) + "%";
        return null;
    }
}