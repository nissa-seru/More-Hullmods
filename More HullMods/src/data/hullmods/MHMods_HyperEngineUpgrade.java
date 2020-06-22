package data.hullmods;

import java.awt.*;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;


public class MHMods_HyperEngineUpgrade extends BaseHullMod {

	public final float MANEUVER_BONUS = 50f;
	public final Color Engines_color = new Color(27, 238, 178,255);
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		float ZeroFluxBoost = 10f + stats.getZeroFluxSpeedBoost().base - stats.getMaxSpeed().base / 4f;
		stats.getZeroFluxSpeedBoost().modifyFlat(id, ZeroFluxBoost);
	}

	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return Math.round(MANEUVER_BONUS) + "%";
		if (index == 1) {
			if (ship == null) {
				return "zero flux boost - ship base speed/4 + 10 /n (exact number on install)";
			} else {
				float zerofluxboost = 10f + ship.getMutableStats().getZeroFluxSpeedBoost().base - ship.getMutableStats().getMaxSpeed().base / 4f;
				return Math.round(zerofluxboost) + "";
			}
		}
        return null;
    }

	@Override
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship.getVariant().hasHullMod("safetyoverrides"))
			return "Incompatible with Safety Overrides";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null &&
			(!ship.getVariant().getHullMods().contains("safetyoverrides"));
	}

	public void advanceInCombat(ShipAPI ship, float amount) {
		if (!ship.isAlive()) return;
		if (ship.getFluxTracker().isEngineBoostActive()) {
			ship.getEngineController().fadeToOtherColor(this, Engines_color, null, 1f, 0.6f);
			ship.getEngineController().extendFlame(this, 0.4f, 0.4f, 0.4f);
			ship.getMutableStats().getAcceleration().modifyPercent("HyperEngineUpgrade", MANEUVER_BONUS * 2f);
			ship.getMutableStats().getDeceleration().modifyPercent("HyperEngineUpgrade", MANEUVER_BONUS);
			ship.getMutableStats().getTurnAcceleration().modifyPercent("HyperEngineUpgrade", -MANEUVER_BONUS * 1.5f);
			ship.getMutableStats().getMaxTurnRate().modifyPercent("HyperEngineUpgrade", -MANEUVER_BONUS);
		} else{
			ship.getMutableStats().getAcceleration().modifyPercent("HyperEngineUpgrade", 0f);
			ship.getMutableStats().getDeceleration().modifyPercent("HyperEngineUpgrade",0f);
			ship.getMutableStats().getTurnAcceleration().modifyPercent("HyperEngineUpgrade", 0f);
			ship.getMutableStats().getMaxTurnRate().modifyPercent("HyperEngineUpgrade", 0f);
		}
	}
}

			
			