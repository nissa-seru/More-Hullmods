package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_FluxBooster extends BaseHullMod {

	public final float MANEUVER_BONUS = 30f;
	public final float vent_bonus = 10f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getVentRateMult().modifyPercent(id, vent_bonus);
	}

	public void advanceInCombat(ShipAPI ship, float amount) {
		if (!ship.isAlive()) return;
		MutableShipStatsAPI stats = ship.getMutableStats();
		if (ship.getFluxTracker().isVenting()) {
			stats.getMaxSpeed().modifyPercent("FluxBuster", 1f + MANEUVER_BONUS);
			stats.getAcceleration().modifyPercent("FluxBuster", 1f + MANEUVER_BONUS * 2f);
			stats.getDeceleration().modifyPercent("FluxBuster", 1f + MANEUVER_BONUS);
			stats.getTurnAcceleration().modifyPercent("FluxBuster", 1f + MANEUVER_BONUS * 2f);
			stats.getMaxTurnRate().modifyPercent("FluxBuster", 1f + MANEUVER_BONUS);
		} else {
			stats.getMaxSpeed().modifyPercent("FluxBuster", 1f);
			stats.getAcceleration().modifyPercent("FluxBuster", 1f);
			stats.getDeceleration().modifyPercent("FluxBuster", 1f);
			stats.getTurnAcceleration().modifyPercent("FluxBuster", 1f);
			stats.getMaxTurnRate().modifyPercent("FluxBuster", 1f);
		}
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return Math.round(MANEUVER_BONUS) + "%";
		if (index == 1) return Math.round(MANEUVER_BONUS) + "%";
		if (index == 2) return Math.round(vent_bonus) + "%";
		return null;
	}
}