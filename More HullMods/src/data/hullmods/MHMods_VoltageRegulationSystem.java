package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_VoltageRegulationSystem extends BaseHullMod {

    public final float FluxCost = 15f;
    public final float RangeBonus = 20f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getBeamWeaponFluxCostMult().modifyPercent(id , -FluxCost);
		stats.getBeamWeaponRangeBonus().modifyPercent(id, -RangeBonus);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return Math.round(FluxCost) + "%";
        }
        if (index == 1) {
            return Math.round(RangeBonus) + "%";
        }
        return null;
    }
}