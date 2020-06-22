package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;

import java.awt.*;

public class MHMods_UnstableShields extends BaseHullMod {

	public final float DmgRed = 0.5f;
	public final float StableTime = 2f;
	public final float DmgInc = 2f;
	public final float TimeToBrake = 8f;
	public final float TimeToUnbrake = 4f;

	public Color ZeroEntropyColor = new Color(153, 18, 213,255);
	public Color FullEntropyColor = new Color(255, 30, 30, 255);

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getShieldAbsorptionMult().modifyMult(id, DmgRed);
		stats.getDynamic().getStat("MHMods_UnstableShields_S").setBaseValue(0f);
		stats.getDynamic().getStat("MHMods_UnstableShields_Instability").setBaseValue(0f);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return Math.round(DmgRed * 100) + "%";
		if (index == 1) return  Math.round(StableTime) + "";
		if (index == 2) return Math.round((DmgInc + 1) * 100 * DmgRed) + "%";
		if (index == 3) return Math.round(TimeToBrake) + "";
		if (index == 4) return Math.round(TimeToUnbrake) + "";
		return null;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		if ((ship.getShield() == null))
			return "Ship Has No Shield";
		return null;
	}
		
	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getShield() != null;
	}

	public void advanceInCombat(ShipAPI ship, float amount) {
		if (!ship.isAlive()) return;

		ship.getShield().setRingColor(new Color(255, 173, 173, 255));

		float Instability = ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_Instability").getModifiedValue();
		float timer = Global.getCombatEngine().getTotalElapsedTime(false);

		if (ship.getShield().isOff()) {
			ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_S").modifyFlat("MHMods_UnstableShields", timer);
			ship.getMutableStats().getShieldAbsorptionMult().modifyMult("MHMods_UnstableShields_break", 1f);
			if (Instability > 0){
				ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_Instability").modifyFlat("Instability", Instability - ((1/ TimeToUnbrake) * amount));
			} else {
				ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_Instability").modifyFlat("Instability", 0);
			}
		}

		if (ship.getShield().isOn()) {
			float timeShieldOn = timer - ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_S").getModifiedValue() + 1;

			if (timeShieldOn > StableTime){
				if (Instability < 1)
					ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_Instability").modifyFlat("Instability", Instability + ((1/TimeToBrake) * amount));
				else
					ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_Instability").modifyFlat("Instability", 1);
			}
		}

		int ShieldRed = Math.round(ZeroEntropyColor.getRed() * (1 - Instability) + FullEntropyColor.getRed() * Instability);
		int ShieldGreen = Math.round(ZeroEntropyColor.getGreen() * (1 - Instability) + FullEntropyColor.getGreen() * Instability);
		int ShieldBlue = Math.round(ZeroEntropyColor.getBlue() * (1 - Instability) + FullEntropyColor.getBlue() * Instability);

		ship.getShield().setInnerColor(new Color(ShieldRed, ShieldGreen, ShieldBlue, 200));

		float BreakValue = 1 + (DmgInc * ship.getMutableStats().getDynamic().getStat("MHMods_UnstableShields_Instability").getModifiedValue());

		ship.getMutableStats().getShieldAbsorptionMult().modifyMult("MHMods_UnstableShields_break", BreakValue);

		if (ship == Global.getCombatEngine().getPlayerShip()) {
			Global.getCombatEngine().maintainStatusForPlayerShip("info", "graphics/icons/hullsys/fortress_shield.png", "Shield efficiency", String.valueOf((float) Math.round(ship.getMutableStats().getShieldAbsorptionMult().getModifiedValue() * 10) / 10), false);
		}

	}
}