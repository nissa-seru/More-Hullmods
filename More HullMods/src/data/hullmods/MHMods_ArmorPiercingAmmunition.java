package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class MHMods_ArmorPiercingAmmunition extends BaseHullMod {

    public final float DMGBonus = 20f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	    stats.getHitStrengthBonus().modifyPercent(id, DMGBonus);
	}
	    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return Math.round(DMGBonus) + "%";
        }
        return null;
    }
}