package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;

import java.util.HashMap;
import java.util.Map;

public class MHMods_TestHullmod extends BaseHullMod {

	private final float Dmg_Multi = 0.8f;

	private final Map<String, Float> myltiforshield = new HashMap<>();
	{
		myltiforshield.put("vic_dest", 50f);
		myltiforshield.put("vic_dest2", 100f);
		myltiforshield.put("vic_dest3", 150f);
		myltiforshield.put("vic_dest4", 200f);
	}

	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getShieldArcBonus().modifyPercent("testmod",100 /* * (Math.abs(Math.abs(ship.getShield().getFacing()-180f) - Math.abs(ship.getFacing()-180f))/180)*/);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return Math.round((1f - Dmg_Multi) * 100f) + "%";
        }
        return null;
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
    	ship.getMutableStats().getShieldArcBonus().modifyPercent("testmod",100 /* * (Math.abs(Math.abs(ship.getShield().getFacing()-180f) - Math.abs(ship.getFacing()-180f))/180)*/);
			Global.getCombatEngine().maintainStatusForPlayerShip("info1", "graphics/icons/hullsys/ammo_feeder.png", "Debug 1", String.valueOf( Math.abs(Math.abs(ship.getShield().getFacing()-180f) - Math.abs(ship.getFacing()-180f))) , false);
	}
}