package data.hullmods;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipSystemSpecAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.FluxTrackerAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class MHMods_MissilesCPU extends BaseHullMod {
	
	
		@Override
	public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
			MutableShipStatsAPI stats = ship.getMutableStats();
			
		if (ship.getVariant().hasHullMod("hmp_alphasubsystem")){
			stats.getMissileMaxSpeedBonus().modifyPercent("MissilesCPU" , 30f);
			stats.getMissileAccelerationBonus().modifyPercent("MissilesCPU" , 30f);
			stats.getMissileMaxTurnRateBonus().modifyPercent("MissilesCPU" , 50f);
			stats.getMissileTurnAccelerationBonus().modifyPercent("MissilesCPU" , 50f);
		}
		else{
			stats.getMissileMaxSpeedBonus().modifyPercent("MissilesCPU" , 30f);
			stats.getMissileAccelerationBonus().modifyPercent("MissilesCPU" , 30f);
			stats.getMissileMaxTurnRateBonus().modifyPercent("MissilesCPU" , 50f);
			stats.getMissileTurnAccelerationBonus().modifyPercent("MissilesCPU" , 50f);
			stats.getMissileWeaponDamageMult().modifyPercent("MissilesCPU" , -15f);
		}
		
		if (ship.getVariant().hasHullMod("hmp_betasubsystem"))
			stats.getMissileHealthBonus().modifyPercent(id, 40f);
	}
	
		    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return "" + 30 + "%";
        }
		if (index == 1) {
            return "" + 50 + "%";
        }
        if (index == 2) {
            return "" + 15 + "%";
        }
        return null;
    }
}

			
			