package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_ParticleAccelerator extends BaseHullMod {

	public final float SpeedBonus = 25f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getProjectileSpeedMult().modifyPercent(id , SpeedBonus);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return Math.round(SpeedBonus) + "%";
        return null;
    }
}