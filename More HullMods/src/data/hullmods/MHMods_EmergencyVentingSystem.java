package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.util.HashMap;
import java.util.Map;

public class MHMods_EmergencyVentingSystem extends BaseHullMod {

	private final float TriggerAt = 0.9f;
	private final float RedPerCell = 0.9f;
	private final float HardFluxVent = 0.5f;
	private final float SoftFluxVent = 0.3f;

	private final Map<HullSize, Integer> mag = new HashMap<>();
	{
		mag.put(HullSize.FRIGATE, 2);
		mag.put(HullSize.DESTROYER, 3);
		mag.put(HullSize.CRUISER, 4);
		mag.put(HullSize.CAPITAL_SHIP, 5);
	}

	public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
		MutableShipStatsAPI stats = ship.getMutableStats();
		stats.getDynamic().getStat("MHMods_FusionCell_used").setBaseValue(0);
		stats.getDynamic().getStat("MHMods_FusionCell_MaxFluxMult").setBaseValue(1);
	}

	 public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return Math.round(TriggerAt * 100f) + "%";
		if (index == 1) return Math.round((1 - HardFluxVent) * 100f) + "%";
		if (index == 2) return Math.round((1 - SoftFluxVent) * 100f) + "%";
        if (index == 3) return mag.get(HullSize.FRIGATE) + "";
		if (index == 4) return mag.get(HullSize.DESTROYER) + "";
		if (index == 5) return mag.get(HullSize.CRUISER) + "";
		if (index == 6) return mag.get(HullSize.CAPITAL_SHIP) + "";
		if (index == 7) return Math.round((1f - RedPerCell) * 100f) + "%";
        return null;
    }

    @Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		if (!ship.isAlive()) return;

		int FusionCell_used = 0;
		if (Global.getCombatEngine().getCustomData().get("FusionCell_used" + ship.getId()) instanceof Integer)
			FusionCell_used = (int) Global.getCombatEngine().getCustomData().get("FusionCell_used" + ship.getId());

		MutableShipStatsAPI stats = ship.getMutableStats();
		float Flux = ship.getFluxTracker().getFluxLevel();
		float SoftFlux = ship.getFluxTracker().getCurrFlux();
		float HardFlux = ship.getFluxTracker().getHardFlux();

		if (FusionCell_used < mag.get(ship.getHullSize())) {
			if (Flux >= TriggerAt) {
				ship.getFluxTracker().setHardFlux(HardFlux * HardFluxVent);
				ship.getFluxTracker().setCurrFlux((SoftFlux - HardFlux) * SoftFluxVent + ship.getFluxTracker().getHardFlux());

				FusionCell_used += 1;//ship.getMutableStats().getDynamic().getStat("FusionCell_used").modifyFlat("FusionCells", CellsUsed + 1);
				Global.getCombatEngine().getCustomData().put("FusionCell_used" + ship.getId(), FusionCell_used);

				stats.getFluxCapacity().modifyMult("MHMods_FusionCells", (float) Math.pow(RedPerCell, FusionCell_used));
			}
			if (ship == Global.getCombatEngine().getPlayerShip())
				Global.getCombatEngine().maintainStatusForPlayerShip("info", "graphics/icons/hullsys/emp_emitter.png", "Cells left", Math.round(mag.get(ship.getHullSize()) - FusionCell_used) + " Cells", false);
		}
		else if (ship == Global.getCombatEngine().getPlayerShip())
			Global.getCombatEngine().maintainStatusForPlayerShip("info", "graphics/icons/hullsys/emp_emitter.png", "Cells left", "Out of cells", false);
	}
}








