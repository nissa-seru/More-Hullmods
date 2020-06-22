package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.FighterLaunchBayAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class MHMods_DreadnoughtSpec extends BaseHullMod {

	public final float recover_percent = 50f;
	public final float ZFSB_mult = 0.5f;
	public final float Vent_bonus = 30f;
	public final float Sensor_bonus = 150f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		if (stats.getVariant() != null) {
			int fluxCapacitors = stats.getVariant().getNumFluxCapacitors();
			int fluxVents = stats.getVariant().getNumFluxVents();
			stats.getFluxCapacity().modifyFlat(id, (float)fluxCapacitors * Vent_bonus * 10);
			stats.getFluxDissipation().modifyFlat(id, (float)fluxVents * Vent_bonus);
		}

		stats.getSuppliesToRecover().modifyPercent(id, recover_percent);
		stats.getFuelUseMod().modifyPercent(id, recover_percent);
		stats.getZeroFluxSpeedBoost().modifyMult(id, ZFSB_mult);
		stats.getSensorStrength().modifyFlat(id, Sensor_bonus);
		stats.getSensorProfile().modifyFlat(id, Sensor_bonus);

	}


	@Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		for (FighterLaunchBayAPI bay : ship.getLaunchBaysCopy()) {
			if (bay.getWing() == null) continue;

			FighterWingSpecAPI spec = bay.getWing().getSpec();

			int addForWing = spec.getNumFighters();
			int maxTotal = addForWing * 2;

			if (ship.getFullTimeDeployed() < 1f){
				bay.setFastReplacements(addForWing);
				bay.setExtraDuration(99999);
			}

			//int actualAdd = maxTotal - bay.getWing().getWingMembers().size();
			bay.setExtraDeployments(addForWing);
			bay.setExtraDeploymentLimit(maxTotal);
		}
		
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		return null;
	}

	public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
	}

}