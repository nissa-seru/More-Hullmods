package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.util.HashMap;
import java.util.Map;

public class MHMods_hullfoam extends BaseHullMod {

    final Map<HullSize, Float> repairSpeed = new HashMap<>();
    final float maxFoam = 1f;

    {
        repairSpeed.put(HullSize.FIGHTER, 4f);
        repairSpeed.put(HullSize.FRIGATE, 2f);
        repairSpeed.put(HullSize.DESTROYER, 1.5f);
        repairSpeed.put(HullSize.CRUISER, 1f);
        repairSpeed.put(HullSize.CAPITAL_SHIP, 0.5f);
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return floatToString(repairSpeed.get(HullSize.FRIGATE)) + "%";
        if (index == 1) return floatToString(repairSpeed.get(HullSize.DESTROYER)) + "%";
        if (index == 2) return floatToString(repairSpeed.get(HullSize.CRUISER)) + "%";
        if (index == 3) return floatToString(repairSpeed.get(HullSize.CAPITAL_SHIP)) + "%";
        if (index == 4) return floatToString(maxFoam * 100) + "%";
        return null;
    }

    public String floatToString(float number) {
        if (number == Math.round(number)) {
            return Math.round(number) + "";
        } else {
            return number + "";
        }
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        if (!ship.isAlive()) return;

        Map<String, Object> customCombatData = Global.getCombatEngine().getCustomData();
        String id = ship.getId();
        float foamLeft = maxFoam;

        if (customCombatData.get("MHMods_hullfoam" + id) instanceof Float)
            foamLeft = (float) customCombatData.get("MHMods_hullfoam" + id);

        float currentHP = ship.getHitpoints();
        float missingHP = 1f - ship.getHullLevel();
        float repairThatFrame = repairSpeed.get(ship.getHullSize()) * 0.01f * amount;
        if (missingHP < repairThatFrame) repairThatFrame = missingHP;

        float hullToRepair = ship.getMaxHitpoints() * repairThatFrame;
        float percentRepaired = ship.getMaxHitpoints() * repairThatFrame / ship.getHullSpec().getHitpoints();
        if (percentRepaired > foamLeft) {
            percentRepaired = foamLeft;
            hullToRepair = ship.getHullSpec().getHitpoints() * percentRepaired;
        }
        ship.setHitpoints(currentHP + hullToRepair);

        foamLeft -= percentRepaired;

        if (ship == Global.getCombatEngine().getPlayerShip()) {
            if (foamLeft != 0) {
                Global.getCombatEngine().maintainStatusForPlayerShip("MHMods_hullfoam", "graphics/icons/hullsys/mhmods_hullfoam.png", "Hullfoam Left", (float) Math.round(foamLeft * 1000) / 10 + "%", false);
            } else {
                Global.getCombatEngine().maintainStatusForPlayerShip("MHMods_hullfoam", "graphics/icons/hullsys/mhmods_hullfoam.png", "Hullfoam Left", "OUT OF FOAM", true);
            }
        }

        customCombatData.put("MHMods_hullfoam" + id, foamLeft);
    }
}
