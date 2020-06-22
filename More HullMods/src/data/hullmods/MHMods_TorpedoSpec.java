package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_TorpedoSpec extends BaseHullMod {

	public final float SpeedBonus = 35f;
	public final float MTurn = 40f;


	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMissileMaxSpeedBonus().modifyPercent(id, SpeedBonus);
		stats.getMissileAccelerationBonus().modifyPercent(id, SpeedBonus);
		stats.getMissileMaxTurnRateBonus().modifyPercent(id, -MTurn);
		stats.getMissileTurnAccelerationBonus().modifyPercent(id, -MTurn);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return Math.round(SpeedBonus) + "%";
        }
        if (index == 1) {
            return Math.round(MTurn) + "%";
        }
        return null;
    }
}

			
			