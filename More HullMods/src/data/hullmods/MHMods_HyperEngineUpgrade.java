package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.awt.*;
import java.util.Map;

public class MHMods_HyperEngineUpgrade extends BaseHullMod {

    //public final float MANEUVER_BONUS = 25f;
    public final Color Engines_color = new Color(27, 238, 178, 255);
    final float fluxThreshold = 0.05f;

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
		/*
		MutableShipStatsAPI stats = ship.getMutableStats();
		float ZeroFluxBoost = 20f + stats.getZeroFluxSpeedBoost().getModifiedValue() - stats.getMaxSpeed().getModifiedValue() / 3f;
		stats.getZeroFluxSpeedBoost().modifyFlat(id, ZeroFluxBoost);
		 */
    }

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
        if (index == 0) return Math.round(fluxThreshold * 100) + "%";
        if (index == 1) {
            if (ship == null) {
                return "zero flux boost - ship base speed/3 + 20 /n (exact number on install)";
            } else {
                float zerofluxboost = 20f + ship.getMutableStats().getZeroFluxSpeedBoost().getModifiedValue() - ship.getMutableStats().getMaxSpeed().getModifiedValue() / 3f;
                return Math.round(zerofluxboost) + "";
            }
        }
        return null;
    }

    public void advanceInCombat(ShipAPI ship, float amount) {
        if (!ship.isAlive()) return;

        Map<String, Object> customCombatData = Global.getCombatEngine().getCustomData();
        String id = ship.getId();
        float speedBoost;

        if (customCombatData.get("MHMods_HyperEngineUpgrade" + id) instanceof Float) {
            speedBoost = (float) customCombatData.get("MHMods_HyperEngineUpgrade" + id);
        } else {
            speedBoost = 20f + ship.getMutableStats().getZeroFluxSpeedBoost().base - ship.getMutableStats().getMaxSpeed().getModifiedValue() / 3f;
            customCombatData.put("MHMods_HyperEngineUpgrade" + id, speedBoost);
        }

        if (ship.getFluxTracker().isEngineBoostActive() && ship.getFluxLevel() <= 0.05f) {
            ship.getMutableStats().getZeroFluxSpeedBoost().modifyFlat(id, speedBoost);
            ship.getEngineController().fadeToOtherColor(this, Engines_color, null, 1f, 0.6f);
            ship.getEngineController().extendFlame(this, 0.4f, 0.4f, 0.4f);
        } else {
            ship.getMutableStats().getZeroFluxSpeedBoost().modifyFlat(id, 0);
        }
    }
}

			
			