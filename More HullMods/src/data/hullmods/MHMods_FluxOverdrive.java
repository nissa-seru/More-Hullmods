package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;

public class MHMods_FluxOverdrive extends BaseHullMod {

	public final float ATKSpeedBonus = 30f;

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return Math.round(ATKSpeedBonus) + "%";
		return null;
    }

	public void advanceInCombat(ShipAPI ship, float amount){
		if (!ship.isAlive()) return;
		ship.getMutableStats().getBallisticRoFMult().modifyPercent("Overcharged", ATKSpeedBonus * ship.getFluxLevel());
		ship.getMutableStats().getEnergyRoFMult().modifyPercent("Overcharged", ATKSpeedBonus * ship.getFluxLevel());
		if (ship == Global.getCombatEngine().getPlayerShip())
			Global.getCombatEngine().maintainStatusForPlayerShip("info", "graphics/icons/hullsys/ammo_feeder.png", "Overcharge boost", Math.round(ATKSpeedBonus * ship.getFluxLevel()) + "%", false);
	}
}